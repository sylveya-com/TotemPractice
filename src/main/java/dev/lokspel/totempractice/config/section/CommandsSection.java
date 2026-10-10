package dev.lokspel.totempractice.config.section;

import dev.lokspel.totempractice.TotemPractice;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandsSection {

    private static final String PATH = "commands.";

    public record Entry(String command, List<String> bypassPermissions) {
    }

    private final TotemPractice plugin;

    public CommandsSection(TotemPractice plugin) {
        this.plugin = plugin;
    }

    private FileConfiguration config() {
        return plugin.getConfig();
    }

    public List<Entry> onRoundEnd() {
        List<Entry> entries = new ArrayList<>();
        for (Object value : config().getList(PATH + "on-round-end")) {
            if (value instanceof String command) {
                entries.add(new Entry(command, List.of()));
            } else if (value instanceof Map<?, ?> map) {
                Object command = map.get("command");
                if (command instanceof String text && !text.isBlank()) {
                    entries.add(new Entry(text, strings(map.get("bypass-permissions"))));
                }
            }
        }
        return entries;
    }

    private static List<String> strings(Object value) {
        if (!(value instanceof List<?> list)) {
            return List.of();
        }
        List<String> result = new ArrayList<>();
        for (Object element : list) {
            if (element instanceof String text && !text.isBlank()) {
                result.add(text.trim());
            }
        }
        return result;
    }
}
