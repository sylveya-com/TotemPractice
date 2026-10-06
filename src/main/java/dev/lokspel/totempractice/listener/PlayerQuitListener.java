package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.Rounds;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    private final Rounds rounds;

    public PlayerQuitListener(Rounds rounds) {
        this.rounds = rounds;
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        rounds.remove(event.getPlayer());
    }
}