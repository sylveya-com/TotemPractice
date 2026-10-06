package dev.lokspel.totempractice.game;

import dev.lokspel.totempractice.config.MessagesConfig;
import dev.lokspel.totempractice.util.PlayerUtil;
import org.bukkit.entity.Player;

public final class Results {

    private static final String TITLE_KEY = "results.title";
    private static final String SUBTITLE_KEY = "results.subtitle";

    private Results() {
    }

    public static void show(Player player, Round round, MessagesConfig messages) {
        PlayerUtil.showTitle(player,
                messages.get(TITLE_KEY),
                messages.get(SUBTITLE_KEY, "score", String.valueOf(round.score()))
        );
    }
}