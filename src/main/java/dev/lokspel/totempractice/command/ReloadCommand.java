package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.gui.guis.DifficultyGui;
import dev.lokspel.totempractice.util.MessageUtil;
import dev.lokspel.totempractice.util.SoftDependUtil;
import org.bukkit.command.CommandSender;

public final class ReloadCommand implements SubCommand {

    private final TotemPractice plugin;

    public ReloadCommand(TotemPractice plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        var messages = TotemPracticeConfig.messages().getCommands();
        if (!sender.hasPermission("totempractice.reload")) {
            MessageUtil.send(sender, messages.getNoPermission());
            return true;
        }

        try {
            TotemPracticeConfig.reload();
            new DifficultyGui(plugin.getRoundManager());
            if (SoftDependUtil.PACKET_EVENTS_ENABLED && plugin.getPlayerHider() != null) {
                plugin.getPlayerHider().refreshVisibility();
            }
            MessageUtil.send(sender, TotemPracticeConfig.messages().getCommands().getConfigReloaded());
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to reload configuration: " + TotemPracticeConfig.describeFailure(e));
            sender.sendMessage("§cFailed to reload configuration: " + e.getMessage());
        }
        return true;
    }
}