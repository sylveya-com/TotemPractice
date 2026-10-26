package dev.lokspel.totempractice.commands.totempractice.subcommands;

import com.google.common.collect.ImmutableList;
import dev.lokspel.totempractice.commands.SubCommand;
import dev.lokspel.totempractice.commands.totempractice.subcommands.set.SetLobbySubCmd;
import dev.lokspel.totempractice.commands.totempractice.subcommands.set.SetMatchSubCmd;
import dev.lokspel.totempractice.util.MessageUtil;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetSubCmd implements SubCommand {

    private final @NotNull List<SubCommand> subCommands;
    private final @NotNull List<String> tabCompletes;

    public SetSubCmd() {
        this.subCommands = ImmutableList.of(
                new SetLobbySubCmd(),
                new SetMatchSubCmd()
        );
        this.tabCompletes = subCommands.stream().map(SubCommand::label).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    @Override
    public @NotNull String label() {
        return "set";
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 2) {
            String current = args[1].toLowerCase();
            return tabCompletes.stream()
                    .filter(s -> s.toLowerCase().startsWith(current))
                    .collect(Collectors.toList());
        }

        if (args.length >= 3) {
            for (final SubCommand subCommand : subCommands) {
                if (args[1].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.tabComplete(sender, alias, args);
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length >= 2) {
            for (final SubCommand subCommand : subCommands) {
                if (args[1].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.execute(sender, commandLabel, args);
                }
            }
        }

        MessageUtil.send(sender, "<red>Usage: /totempractice set <lobby|match>");
        return true;
    }
}
