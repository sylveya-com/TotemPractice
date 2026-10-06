package dev.lokspel.totempractice.gui;

import dev.lokspel.totempractice.util.ColorAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public abstract class Gui {

    private static final Map<GuiType, Gui> GUIS = new EnumMap<>(GuiType.class);
    private static final Map<Player, Gui> OPEN_GUI = new HashMap<>();

    private final String title;
    private final int size;
    private Inventory inventory;

    protected Gui(GuiType type, String title, int size) {
        this.title = ColorAPI.legacy(title);
        this.size = size;
        GUIS.put(type, this);
    }

    public static Gui search(GuiType type) {
        return GUIS.get(type);
    }

    public static Gui openGui(Player player) {
        return OPEN_GUI.get(player);
    }

    public static void trackOpen(Player player, Gui gui) {
        OPEN_GUI.put(player, gui);
    }

    public static void untrack(Player player, Gui gui) {
        OPEN_GUI.remove(player, gui);
    }

    public final Inventory getInventory() {
        return inventory;
    }

    public void open(Player player) {
        inventory = Bukkit.createInventory(null, size, title);
        populate(inventory);
        trackOpen(player, this);
        player.openInventory(inventory);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public void handleClickEvent(InventoryClickEvent event) {
    }

    public void handleDragEvent(InventoryDragEvent event) {
    }

    public void handleCloseEvent(InventoryCloseEvent event) {
    }

    protected abstract void populate(Inventory inventory);
}