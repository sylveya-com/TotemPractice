package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.RoundManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public final class PlayerDropItemListener implements Listener {

    private final RoundManager roundManager;

    public PlayerDropItemListener(RoundManager roundManager) {
        this.roundManager = roundManager;
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void handle(PlayerDropItemEvent event) {
        if (!roundManager.isParticipant(event.getPlayer())) {
            return;
        }
        if (event.getItemDrop().getItemStack().getType() == Material.TOTEM_OF_UNDYING) {
            event.setCancelled(true);
        }
    }
}