package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.RoundManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public final class PlayerQuitListener implements Listener {

    private final RoundManager roundManager;

    public PlayerQuitListener(RoundManager roundManager) {
        this.roundManager = roundManager;
    }

    @EventHandler
    public void handle(PlayerQuitEvent event) {
        roundManager.remove(event.getPlayer());
    }
}