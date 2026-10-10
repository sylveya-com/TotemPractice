package dev.lokspel.totempractice.util;

import dev.lokspel.totempractice.TotemPractice;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public final class CommandUtil {

    private CommandUtil() {
    }

    public static void run(List<String> commands, Player player) {
        if (commands.isEmpty()) {
            return;
        }
        Bukkit.getScheduler().runTask(TotemPractice.getInstance(), () -> {
            if (!player.isOnline()) {
                return;
            }
            for (String command : commands) {
                String resolved = command
                        .replace("%player%", player.getName())
                        .replace("%uuid%", player.getUniqueId().toString());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), resolved);
            }
        });
    }
}
