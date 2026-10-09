package dev.lokspel.totempractice.config.section;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.game.Difficulty;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.LinkedHashMap;
import java.util.Map;

public class DifficultiesSection {

    private static final String PATH = "difficulties.";

    private final TotemPractice plugin;

    public DifficultiesSection(TotemPractice plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public Difficulty get(String name) {
        ConfigurationSection section = config().getConfigurationSection(PATH + name);
        return section == null ? null : fromSection(name, section);
    }

    public Map<String, Difficulty> all() {
        Map<String, Difficulty> difficulties = new LinkedHashMap<>();
        ConfigurationSection section = config().getConfigurationSection("difficulties");
        if (section == null) {
            return difficulties;
        }
        for (String name : section.getKeys(false)) {
            if (section.isConfigurationSection(name)) {
                difficulties.put(name, fromSection(name, section.getConfigurationSection(name)));
            }
        }
        return difficulties;
    }

    private static Difficulty fromSection(String name, ConfigurationSection section) {
        double hitInterval = section.getDouble("hit-interval", 1.0D);
        return new Difficulty(
                name,
                hitInterval,
                section.getDouble("offhand-hit-interval", hitInterval),
                section.getDouble("score-multiplier", 1.0D),
                section.getBoolean("offhand-only", false)
        );
    }
}