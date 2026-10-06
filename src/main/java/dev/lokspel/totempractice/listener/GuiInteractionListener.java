package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.gui.Gui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public final class GuiInteractionListener implements Listener {

    @EventHandler
    public void handle(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        Gui gui = Gui.openGui(player);
        if (gui == null) {
            return;
        }
        event.setCancelled(true);
        gui.handleClickEvent(event);
    }

    @EventHandler
    public void handle(InventoryDragEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }
        Gui gui = Gui.openGui(player);
        if (gui == null) {
            return;
        }
        event.setCancelled(true);
        gui.handleDragEvent(event);
    }
}