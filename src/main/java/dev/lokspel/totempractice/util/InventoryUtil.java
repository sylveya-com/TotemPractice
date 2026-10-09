package dev.lokspel.totempractice.util;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public final class InventoryUtil {

    private InventoryUtil() {
    }

    public static void fillTotems(PlayerInventory inventory, int storageSlots, boolean offhandOnly) {
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        for (int slot = 0; slot < storageSlots; slot++) {
            inventory.setItem(slot, totem.clone());
        }
        inventory.setItemInOffHand(offhandOnly ? null : totem.clone());
    }

    public static void fillAll(Inventory inventory, String material, Material fallback) {
        ItemStack fill = new ItemStack(Objects.requireNonNullElse(Material.matchMaterial(material), fallback));
        for (int slot = 0; slot < inventory.getSize(); slot++) {
            inventory.setItem(slot, fill);
        }
    }
}