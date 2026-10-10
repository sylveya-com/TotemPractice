package dev.lokspel.totempractice.config.section;

import dev.lokspel.totempractice.TotemPractice;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class CommandsSection {

    private static final String PATH = "commands.";

    private final TotemPractice plugin;

    public CommandsSection(TotemPractice plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public List<String> onRoundEnd() {
        return config().getStringList(PATH + "on-round-end");
    }
}
