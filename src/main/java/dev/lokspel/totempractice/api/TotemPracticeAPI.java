package dev.lokspel.totempractice.api;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.game.Round;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
@SuppressWarnings("ClassCanBeRecord")
public class TotemPracticeAPI {

    @Getter
    private static TotemPracticeAPI instance;
    private final TotemPractice plugin;

    public TotemPracticeAPI(TotemPractice plugin) {
        this.plugin = plugin;
        instance = this;
    }

    /**
     * Returns the round a player is currently in, or {@code null} if none.
     */
    public Round getRound(Player player) {
        return plugin.getRounds().find(player.getUniqueId());
    }

    /**
     * Returns the player's current score, or 0 if not in a round.
     */
    public int getScore(Player player) {
        Round round = getRound(player);
        return round == null ? 0 : round.score();
    }

    /**
     * Milliseconds until the next lethal hit for the player's round, or -1 if
     * not in a round.
     */
    public long getMillisUntilNextHit(Player player) {
        Round round = getRound(player);
        return round == null ? -1 : round.nextHitAt() - System.currentTimeMillis();
    }
}