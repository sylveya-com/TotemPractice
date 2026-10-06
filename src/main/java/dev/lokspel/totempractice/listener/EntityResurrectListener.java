package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.game.Round;
import dev.lokspel.totempractice.game.Rounds;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;

public final class EntityResurrectListener implements Listener {

    private final Rounds rounds;

    public EntityResurrectListener(Rounds rounds) {
        this.rounds = rounds;
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void handle(EntityResurrectEvent event) {
        if (event.getHand() == null || !(event.getEntity() instanceof Player player)) {
            return;
        }
        Round round = rounds.find(player.getUniqueId());
        if (round != null) {
            round.useTotem();
        }
    }
}