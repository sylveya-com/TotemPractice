package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.MainConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class SetCommand implements SubCommand, TabCompleter {

    private final MainConfig config;

    public SetCommand(TotemPractice plugin) {
        this.config = plugin.getMainConfig();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(config.messages().get("player-only"));
            return true;
        }

        if (!sender.hasPermission("totempractice.set")) {
            sender.sendMessage(config.messages().get("no-permission"));
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage(config.messages().prefixed("usage"));
            return true;
        }

        String target = args[1].toLowerCase(Locale.ROOT);

        if (!target.equals("match") && !target.equals("lobby")) {
            sender.sendMessage(config.messages().prefixed("usage"));
            return true;
        }

        config.backed().setLocation(target, player.getLocation());
        sender.sendMessage(config.messages().prefixed("point-set", "type", target));
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