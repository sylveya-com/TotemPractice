package dev.lokspel.totempractice.gui.listener;

import dev.lokspel.totempractice.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public final class GuiCloseListener implements Listener {

    @EventHandler
    public void handle(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player player)) {
            return;
        }
        Gui gui = Gui.openGui(player);
        if (gui == null) {
            return;
        }
        if (event.getInventory() != gui.getInventory()) {
            return;
        }
        Gui.untrack(player, gui);
        gui.handleCloseEvent(event);
    }
}