package dev.lokspel.totempractice.config.section;

import dev.lokspel.totempractice.TotemPractice;
import org.bukkit.configuration.file.FileConfiguration;

public class HideSection {

    private static final String PATH = "hide.";

    private final TotemPractice plugin;

    public HideSection(TotemPractice plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public boolean matchPlayersInTab() {
        return config().getBoolean(PATH + "match-players-in-tab", false);
    }

    public boolean matchPlayersFromEachOther() {
        return config().getBoolean(PATH + "match-players-from-each-other", true);
    }
}