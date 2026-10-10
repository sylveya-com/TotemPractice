package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.RoundManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public final class PlayerPickupListener implements Listener {

    private final RoundManager roundManager;

    public PlayerPickupListener(RoundManager roundManager) {
        this.roundManager = roundManager;
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!roundManager.isParticipant(player)) {
            return;
        }
        if (event.getItem().getItemStack().getType() == Material.TOTEM_OF_UNDYING) {
            event.setCancelled(true);
        }
    }
}