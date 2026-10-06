package dev.lokspel.totempractice.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public final class ItemBuilder {

    private final ItemStack item;

    public ItemBuilder(Material material, Material fallback) {
        this.item = new ItemStack(Objects.requireNonNullElse(material, fallback));
    }

    public ItemBuilder name(String name) {
        return meta(meta -> meta.setDisplayName(ColorAPI.legacy(name)));
    }

    public ItemBuilder lore(List<String> lore) {
        return meta(meta -> meta.setLore(lore.stream().map(ColorAPI::legacy).toList()));
    }

    public ItemBuilder glint(boolean glint) {
        return meta(meta -> meta.setEnchantmentGlintOverride(glint));
    }

    private ItemBuilder meta(Consumer<ItemMeta> handler) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            handler.accept(meta);
            item.setItemMeta(meta);
        }
        return this;
    }

    public ItemStack build() {
        return item;
    }
}