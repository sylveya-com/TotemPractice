package dev.lokspel.totempractice.api.event;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class RoundStartEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private final Player player;

    public RoundStartEvent(Player player) {
        this.player = player;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}