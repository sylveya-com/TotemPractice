package dev.lokspel.totempractice.config;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.section.DifficultiesSection;
import dev.lokspel.totempractice.config.section.HideSection;

public class MainConfig {

    private final TotemPractice plugin;
    private final DifficultiesSection difficulties;
    private final HideSection hide;
    private final BackedConfig backed;
    private MessagesConfig messages;

    public MainConfig(TotemPractice plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        this.difficulties = new DifficultiesSection(plugin);
        this.hide = new HideSection(plugin);
        this.backed = new BackedConfig(plugin);
        this.messages = new MessagesConfig(plugin);
    }

    public void load() {
        plugin.reloadConfig();
        backed.reload();
        messages = new MessagesConfig(plugin);
    }

    public DifficultiesSection difficulties() {
        return difficulties;
    }

    public HideSection hide() {
        return hide;
    }

    public BackedConfig backed() {
        return backed;
    }

    public MessagesConfig messages() {
        return messages;
    }
}
