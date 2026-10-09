package dev.lokspel.totempractice.config;

import dev.lokspel.totempractice.TotemPractice;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class GuiConfig {

    private static final String PATH = "difficulty-selection.";

    private final FileConfiguration config;

    public GuiConfig(TotemPractice plugin) {
        this.config = load(plugin);
    }

    private FileConfiguration load(TotemPractice plugin) {
        File file = new File(plugin.getDataFolder(), "guis.yml");
        if (!file.exists()) {
            plugin.saveResource("guis.yml", false);
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public String title() {
        return config.getString(PATH + "title", "<dark_purple>TotemPractice - choose difficulty</dark_purple>");
    }

    public int size() {
        return config.getInt(PATH + "size", 9);
    }

    public String fillMaterial() {
        return config.getString(PATH + "fill-material", "gray_stained_glass_pane");
    }

    public String offhandWarning() {
        return config.getString(PATH + "offhand-warning", "<red>Hold your totem in the offhand!");
    }

    public Map<String, SlotConfig> difficultySlots() {
        Map<String, SlotConfig> slots = new LinkedHashMap<>();
        ConfigurationSection section = config.getConfigurationSection(PATH + "slots");
        if (section == null) {
            return slots;
        }
        for (String name : section.getKeys(false)) {
            String path = PATH + "slots." + name;
            slots.put(name, new SlotConfig(
                    config.getInt(path + ".slot"),
                    config.getString(path + ".material", "lime_concrete"),
                    config.getString(path + ".name", "<green><bold>" + name + "</bold></green>"),
                    config.getStringList(path + ".lore"),
                    config.getBoolean(path + ".glint", true)
            ));
        }
        return slots;
    }

    public record SlotConfig(int slot, String material, String name, List<String> lore, boolean glint) {}
}