package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.GuiConfig;
import dev.lokspel.totempractice.config.MainConfig;
import dev.lokspel.totempractice.gui.guis.DifficultyGui;
import dev.lokspel.totempractice.util.SoftDependUtil;
import org.bukkit.command.CommandSender;

public final class ReloadCommand implements SubCommand {

    private final TotemPractice plugin;
    private final MainConfig config;

    public ReloadCommand(TotemPractice plugin) {
        this.plugin = plugin;
        this.config = plugin.getMainConfig();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!sender.hasPermission("totempractice.reload")) {
            sender.sendMessage(config.messages().get("no-permission"));
            return true;
        }

        config.load();
        new DifficultyGui(plugin.getRounds(), config.difficulties(), new GuiConfig(plugin));
        if (SoftDependUtil.PACKET_EVENTS_ENABLED && plugin.getPlayerHider() != null) {
            plugin.getPlayerHider().refreshVisibility();
        }

        sender.sendMessage(config.messages().prefixed("config-reloaded"));
        return true;
    }
}