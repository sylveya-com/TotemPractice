package dev.lokspel.totempractice.commands.totempractice;

import com.google.common.collect.ImmutableList;
import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.commands.SubCommand;
import dev.lokspel.totempractice.commands.totempractice.subcommands.GuiSubCmd;
import dev.lokspel.totempractice.commands.totempractice.subcommands.ReloadSubCmd;
import dev.lokspel.totempractice.commands.totempractice.subcommands.SetSubCmd;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.util.Enableable;
import dev.lokspel.totempractice.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class TotemPracticeCmd extends Command implements Enableable {

    private final @NotNull SubCommand defaultSubCommand;
    private final @NotNull List<SubCommand> subCommands;
    private final @NotNull List<String> tabCompletes;

    public TotemPracticeCmd() {
        super(
                "totempractice",
                "Main TotemPractice command",
                "/totempractice <gui|set|reload>",
                Collections.emptyList()
        );
        this.defaultSubCommand = new GuiSubCmd();
        this.subCommands = ImmutableList.of(
                defaultSubCommand,
                new SetSubCmd(),
                new ReloadSubCmd()
        );
        this.tabCompletes = subCommands.stream().map(SubCommand::label).collect(Collectors.collectingAndThen(Collectors.toList(), ImmutableList::copyOf));
    }

    @Override
    public void enable() {
        PluginCommand pluginCommand = TotemPractice.getInstance().getCommand(getName());
        if (pluginCommand != null) {
            pluginCommand.setExecutor((sender, command, label, args) -> execute(sender, label, args));
            pluginCommand.setTabCompleter((sender, command, alias, args) -> tabComplete(sender, alias, args));
        }
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws CommandException, IllegalArgumentException {
        if (args.length == 1) {
            return tabCompletes;
        }

        if (args.length >= 2) {
            for (final SubCommand subCommand : subCommands) {
                if (args[0].equalsIgnoreCase(subCommand.label())) {
                    return subCommand.tabComplete(sender, alias, args);
                }
            }
        }

        return Collections.emptyList();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (args.length == 0) {
            return defaultSubCommand.execute(sender, commandLabel, args);
        }

        for (final SubCommand subCommand : subCommands) {
            if (args[0].equalsIgnoreCase(subCommand.label())) {
                return subCommand.execute(sender, commandLabel, args);
            }
        }

        MessageUtil.send(sender, "<red>Usage: /totempractice <gui|set|reload>");
        return true;
    }
}
