package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.Rounds;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public final class PlayerPickupListener implements Listener {

    private final Rounds rounds;

    public PlayerPickupListener(Rounds rounds) {
        this.rounds = rounds;
    }

    @EventHandler(ignoreCancelled = true)
    public void handle(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }
        if (!rounds.isParticipant(player)) {
            return;
        }
        if (event.getItem().getItemStack().getType() == Material.TOTEM_OF_UNDYING) {
            event.setCancelled(true);
        }
    }
}