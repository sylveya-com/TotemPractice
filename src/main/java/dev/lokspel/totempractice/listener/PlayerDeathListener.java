package dev.lokspel.totempractice.listener;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.api.event.RoundEndEvent;
import dev.lokspel.totempractice.config.BackedConfig;
import dev.lokspel.totempractice.config.MessagesConfig;
import dev.lokspel.totempractice.game.Results;
import dev.lokspel.totempractice.game.Round;
import dev.lokspel.totempractice.game.Rounds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class PlayerDeathListener implements Listener {

    private final Rounds rounds;
    private final MessagesConfig messages;
    private final BackedConfig backed;
    private final Set<UUID> toLobby = new HashSet<>();

    public PlayerDeathListener(TotemPractice plugin) {
        this.rounds = plugin.getRounds();
        this.messages = plugin.getMainConfig().messages();
        this.backed = plugin.getMainConfig().backed();
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void handle(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (!rounds.isParticipant(player)) {
            return;
        }
        event.getDrops().clear();

        Round round = rounds.remove(player);
        if (round != null) {
            Results.show(player, round, messages);
            toLobby.add(player.getUniqueId());
            Bukkit.getPluginManager().callEvent(new RoundEndEvent(player, round));
        }
    }

    @EventHandler
    public void handle(PlayerRespawnEvent event) {
        if (!toLobby.remove(event.getPlayer().getUniqueId())) {
            return;
        }
        Location lobby = backed.location("lobby");
        if (lobby != null) {
            event.setRespawnLocation(lobby);
        }
    }
}
