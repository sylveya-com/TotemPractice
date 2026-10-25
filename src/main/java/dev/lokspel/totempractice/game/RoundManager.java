package dev.lokspel.totempractice.game;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.api.event.RoundStartEvent;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.util.Disableable;
import dev.lokspel.totempractice.util.InventoryUtil;
import dev.lokspel.totempractice.util.MessageUtil;
import dev.lokspel.totempractice.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RoundManager implements Disableable {

    public static final double HIT_DAMAGE = 1000.0D;

    private final TotemPractice plugin;
    private final Map<UUID, Round> rounds = new HashMap<>();
    private final Map<UUID, Long> lastCountdown = new HashMap<>();

    public RoundManager(TotemPractice plugin) {
        this.plugin = plugin;
    }

    public Round find(UUID playerId) {
        return rounds.get(playerId);
    }

    public boolean isParticipant(Player player) {
        return rounds.containsKey(player.getUniqueId());
    }

    public void tick() {
        if (rounds.isEmpty()) {
            return;
        }

        long now = System.currentTimeMillis();

        for (Round round : rounds.values().toArray(Round[]::new)) {

            Player player = Bukkit.getPlayer(round.playerId());
            if (player == null || !player.isOnline() || player.isDead()) {
                continue;
            }

            if (!round.hasStarted()) {
                long seconds = Math.max(0, (long) Math.ceil((round.nextHitAt() - now) / 1000.0D));
                if (seconds != lastCountdown.getOrDefault(round.playerId(), -1L)) {
                    lastCountdown.put(round.playerId(), seconds);
                    PlayerUtil.showActionBar(player, MessageUtil.format(
                            TotemPracticeConfig.messages().getGame().getRoundStarting(),
                            "seconds",
                            seconds
                    ));
                }
            }

            if (round.shouldHit(now)) {
                round.recordHit(now);
                player.damage(HIT_DAMAGE);
            }
        }
    }

    public void start(Player player, Difficulty difficulty) {
        var messages = TotemPracticeConfig.messages().getGame();
        UUID playerId = player.getUniqueId();

        if (isParticipant(player)) {
            MessageUtil.send(player, messages.getAlreadyRunning());
            return;
        }

        if (player.isDead()) {
            MessageUtil.send(player, messages.getCannotStartDead());
            return;
        }

        PlayerUtil.switchToSurvival(player);
        if (difficulty.randomTotem()) {
            InventoryUtil.giveRandomTotem(player.getInventory(), Round.STORAGE_SLOTS);
        } else {
            InventoryUtil.fillTotems(player.getInventory(), Round.STORAGE_SLOTS, difficulty.offhandOnly());
        }

        Location location = TotemPracticeConfig.backed().getMatch().toLocation();
        if (location != null) {
            player.teleport(location);
        }

        rounds.put(playerId, new Round(playerId, difficulty));
        lastCountdown.put(playerId, -1L);

        Bukkit.getPluginManager().callEvent(new RoundStartEvent(player));
    }

    public void hideRandomTotem(Player player, Round round) {
        if (!round.randomTotem()) {
            return;
        }
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (!player.isOnline() || player.isDead()) {
                return;
            }
            if (rounds.get(player.getUniqueId()) != round) {
                return;
            }
            InventoryUtil.giveRandomTotem(player.getInventory(), Round.STORAGE_SLOTS);
        });
    }

    public Round remove(Player player) {
        lastCountdown.remove(player.getUniqueId());
        return rounds.remove(player.getUniqueId());
    }

    @Override
    public void disable() {
        Location lobby = TotemPracticeConfig.backed().getLobby().toLocation();
        for (Round round : rounds.values()) {
            Player player = Bukkit.getPlayer(round.playerId());
            if (player != null && player.isOnline() && lobby != null) {
                player.teleport(lobby);
            }
        }
        rounds.clear();
        lastCountdown.clear();
    }
}