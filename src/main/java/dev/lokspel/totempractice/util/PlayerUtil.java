package dev.lokspel.totempractice.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public final class PlayerUtil {

    private PlayerUtil() {
    }

    public static void switchToSurvival(Player player) {
        GameMode gameMode = player.getGameMode();
        if (gameMode == GameMode.CREATIVE || gameMode == GameMode.SPECTATOR) {
            player.setGameMode(GameMode.SURVIVAL);
        }
    }

    public static void showActionBar(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(message));
    }

    public static void showTitle(Player player, String title, String subtitle) {
        player.sendTitle(
                title,
                subtitle,
                10,
                80,
                10
        );
    }
}