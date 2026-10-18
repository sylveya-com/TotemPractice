package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.game.Round;
import dev.lokspel.totempractice.game.RoundManager;
import dev.lokspel.totempractice.util.CommandUtil;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.UUID;

/**
 * Removes round participants who leave the match world.
 */
public final class PlayerTeleportListener implements Listener {

    private final RoundManager roundManager;

    public PlayerTeleportListener(TotemPractice plugin) {
        this.roundManager = plugin.getRoundManager();
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        Round round = roundManager.find(playerId);
        if (round == null) {
            return;
        }

        Location to = event.getTo();
        if (to == null || to.getWorld() == null) {
            return;
        }

        Location match = TotemPracticeConfig.backed().getMatch().toLocation();
        if (match == null || to.getWorld().equals(match.getWorld())) {
            return;
        }

        roundManager.remove(player);
        CommandUtil.run(TotemPracticeConfig.settings().getCommands().getOnRoundEnd(), player);
    }
}