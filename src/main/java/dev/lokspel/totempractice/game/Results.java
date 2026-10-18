package dev.lokspel.totempractice.game;

import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.util.MessageUtil;
import dev.lokspel.totempractice.util.PlayerUtil;
import org.bukkit.entity.Player;

public final class Results {

    private Results() {
    }

    public static void show(Player player, Round round) {
        var results = TotemPracticeConfig.messages().getResults();
        PlayerUtil.showTitle(
                player,
                MessageUtil.format(results.getTitle()),
                MessageUtil.format(results.getSubtitle(), "score", round.score())
        );
    }
}