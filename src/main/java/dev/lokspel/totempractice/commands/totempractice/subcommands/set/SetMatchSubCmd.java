package dev.lokspel.totempractice.commands.totempractice.subcommands.set;

import dev.lokspel.totempractice.commands.SubCommand;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.util.MessageUtil;
import dev.lokspel.totempractice.util.PluginPermission;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class SetMatchSubCmd implements SubCommand {

    @Override
    public @NotNull String label() {
        return "match";
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        var messages = TotemPracticeConfig.messages().getCommands();
        if (!sender.hasPermission(PluginPermission.SET_MATCH_CMD.get())) {
            MessageUtil.send(sender, messages.getNoPermission());
            return true;
        }
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, messages.getPlayerOnly());
            return true;
        }

        TotemPracticeConfig.backed().getMatch().setFromLocation(player.getLocation());
        TotemPracticeConfig.saveBacked();
        MessageUtil.send(sender, messages.getPointSet(), "type", "match");
        return true;
    }
}
