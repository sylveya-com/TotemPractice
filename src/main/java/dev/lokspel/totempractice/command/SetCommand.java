package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.config.backed.LocationEntry;
import dev.lokspel.totempractice.util.MessageUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class SetCommand implements SubCommand, TabCompleter {

    public SetCommand(TotemPractice ignored) {
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        var messages = TotemPracticeConfig.messages().getCommands();
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, messages.getPlayerOnly());
            return true;
        }

        if (!sender.hasPermission("totempractice.set")) {
            MessageUtil.send(sender, messages.getNoPermission());
            return true;
        }

        if (args.length < 2) {
            MessageUtil.send(sender, messages.getUsage());
            return true;
        }

        String target = args[1].toLowerCase(Locale.ROOT);

        LocationEntry entry = TotemPracticeConfig.backed().get(target);
        if (entry == null) {
            MessageUtil.send(sender, messages.getUsage());
            return true;
        }

        entry.setFromLocation(player.getLocation());
        TotemPracticeConfig.saveBacked();
        MessageUtil.send(sender, messages.getPointSet(), "type", target);
        return true;
    }

    @Override
    public List<String> onTabComplete(
            @NonNull CommandSender sender,
            @NonNull Command command,
            @NonNull String alias,
            String[] args
    ) {
        if (args.length == 2) {
            String input = args[1].toLowerCase(Locale.ROOT);

            return Stream.of("match", "lobby")
                    .filter(target -> target.startsWith(input))
                    .toList();
        }

        return List.of();
    }
}