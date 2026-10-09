package dev.lokspel.totempractice.util.placeholderapi;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.api.TotemPracticeAPI;
import dev.lokspel.totempractice.game.Round;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerExpansion extends PlaceholderExpansion {

    private final TotemPractice plugin;
    private final String identifier;

    public PlayerExpansion(TotemPractice plugin, String identifier) {
        this.plugin = plugin;
        this.identifier = identifier;
    }

    @Override
    public @NotNull String getIdentifier() {
        return identifier;
    }

    @Override
    public @NotNull String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        if (player == null) {
            return null;
        }

        TotemPracticeAPI api = TotemPracticeAPI.getInstance();
        Round round = api.getRound(player);

        return switch (params) {
            case "state" -> round == null ? "none" : "round";
            case "score" -> round == null ? "0" : String.valueOf(api.getScore(player));
            case "totems_used" -> round == null ? "0" : String.valueOf(round.totemsUsed());
            case "difficulty" -> round == null ? "none" : round.difficulty().name();
            case "hit_interval" -> round == null ? "-1" : String.valueOf(round.hitIntervalSeconds());
            case "score_multiplier" -> round == null ? "-1" : String.valueOf(round.scoreMultiplier());
            case "offhand_only" -> round == null ? "false" : String.valueOf(round.offhandOnly());
            case "next_hit" -> String.valueOf(secondsUntilNextHit(api, player));
            default -> null;
        };
    }

    private long secondsUntilNextHit(TotemPracticeAPI api, Player player) {
        double millis = api.getMillisUntilNextHit(player);
        return Math.max(0, (long) Math.ceil(millis / 1000.0D));
    }
}