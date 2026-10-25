package dev.lokspel.totempractice.commands.totempractice.subcommands;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.commands.SubCommand;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.gui.guis.DifficultyGui;
import dev.lokspel.totempractice.util.MessageUtil;
import dev.lokspel.totempractice.util.PluginPermission;
import dev.lokspel.totempractice.util.SoftDependUtil;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ReloadSubCmd implements SubCommand {

    @Override
    public @NotNull String label() {
        return "reload";
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        var messages = TotemPracticeConfig.messages().getCommands();
        if (!sender.hasPermission(PluginPermission.RELOAD_CMD.get())) {
            MessageUtil.send(sender, messages.getNoPermission());
            return true;
        }

        TotemPractice plugin = TotemPractice.getInstance();
        try {
            TotemPracticeConfig.reload();
            new DifficultyGui(plugin.getRoundManager());
            if (SoftDependUtil.PACKET_EVENTS_ENABLED && plugin.getPlayerHider() != null) {
                plugin.getPlayerHider().refreshVisibility();
            }
            MessageUtil.send(sender, TotemPracticeConfig.messages().getCommands().getConfigReloaded());
        } catch (Exception e) {
            MessageUtil.send(sender, "<red>Failed to reload configuration: " + TotemPracticeConfig.describeFailure(e));
        }
        return true;
    }
}
