package dev.lokspel.totempractice.game;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.api.event.RoundStartEvent;
import dev.lokspel.totempractice.config.MainConfig;
import dev.lokspel.totempractice.util.InventoryUtil;
import dev.lokspel.totempractice.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Rounds {

    public static final double HIT_DAMAGE = 1000.0D;

    private final TotemPractice plugin;
    private final Map<UUID, Round> rounds = new HashMap<>();
    private final Map<UUID, Long> lastCountdown = new HashMap<>();

    public Rounds(TotemPractice plugin) {
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
                    PlayerUtil.showActionBar(player, plugin.getMainConfig().messages().get("round-starting", "seconds", String.valueOf(seconds)));
                }
            }

            if (round.shouldHit(now)) {
                round.recordHit(now);
                player.damage(HIT_DAMAGE);
            }
        }
    }

    public void start(Player player, Difficulty difficulty) {
        MainConfig config = plugin.getMainConfig();
        UUID playerId = player.getUniqueId();

        if (isParticipant(player)) {
            player.sendMessage(config.messages().prefixed("already-running"));
            return;
        }

        if (player.isDead()) {
            player.sendMessage(config.messages().prefixed("cannot-start-dead"));
            return;
        }

        PlayerUtil.switchToSurvival(player);
        boolean offhandOnly = config.difficulties().offhandOnly(difficulty);
        InventoryUtil.fillTotems(player.getInventory(), Round.STORAGE_SLOTS, offhandOnly);

        Location location = config.backed().location("match");
        if (location != null) {
            player.teleport(location);
        }

        rounds.put(playerId, new Round(
                playerId,
                difficulty,
                config.difficulties().effectiveHitInterval(difficulty),
                config.difficulties().scoreMultiplier(difficulty),
                offhandOnly
        ));
        lastCountdown.put(playerId, -1L);

        Bukkit.getPluginManager().callEvent(new RoundStartEvent(player));
    }

    public Round remove(Player player) {
        lastCountdown.remove(player.getUniqueId());
        return rounds.remove(player.getUniqueId());
    }
}