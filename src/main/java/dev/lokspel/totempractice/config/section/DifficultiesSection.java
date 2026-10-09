package dev.lokspel.totempractice.config.section;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.game.Difficulty;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Locale;

public class DifficultiesSection {

    private static final String PATH = "difficulties.";

    private final TotemPractice plugin;

    public DifficultiesSection(TotemPractice plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public double hitInterval(Difficulty difficulty) {
        return config().getDouble(PATH + key(difficulty) + ".hit-interval", 1.0D);
    }

    public double offhandHitInterval(Difficulty difficulty) {
        return config().getDouble(PATH + key(difficulty) + ".offhand-hit-interval", hitInterval(difficulty));
    }

    public double effectiveHitInterval(Difficulty difficulty) {
        return offhandOnly(difficulty) ? offhandHitInterval(difficulty) : hitInterval(difficulty);
    }

    public double scoreMultiplier(Difficulty difficulty) {
        return config().getDouble(PATH + key(difficulty) + ".score-multiplier", 1.0D);
    }

    public boolean offhandOnly(Difficulty difficulty) {
        return config().getBoolean(PATH + key(difficulty) + ".offhand-only", false);
    }

    private static String key(Difficulty difficulty) {
        return difficulty.name().toLowerCase(Locale.ROOT);
    }
}