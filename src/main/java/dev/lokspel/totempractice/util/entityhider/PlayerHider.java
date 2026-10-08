package dev.lokspel.totempractice.util.entityhider;

import com.github.retrooper.packetevents.PacketEvents;
import com.github.retrooper.packetevents.event.PacketListenerAbstract;
import com.github.retrooper.packetevents.event.PacketListenerPriority;
import com.github.retrooper.packetevents.event.PacketSendEvent;
import com.github.retrooper.packetevents.protocol.packettype.PacketType;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerEntitySoundEffect;
import com.github.retrooper.packetevents.wrapper.play.server.WrapperPlayServerPlayerInfoUpdate;
import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.api.event.RoundEndEvent;
import dev.lokspel.totempractice.api.event.RoundStartEvent;
import dev.lokspel.totempractice.config.section.HideSection;
import dev.lokspel.totempractice.game.Round;
import dev.lokspel.totempractice.util.SoftDependUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Keeps entity, sound and tab-list visibility consistent with round membership.
 *
 * <p>A player inside a round is visible only to the other members of that same
 * round; everyone else sees them hidden. When
 * {@code hide.match-players-from-each-other} is disabled, players in different
 * rounds can see each other. When {@code hide.match-players-in-tab} is enabled
 * the hidden player is also removed from the observer's tab list.</p>
 */
public class PlayerHider implements Listener {

    private final TotemPractice plugin;
    private final HideSection hide;

    private final Map<Integer, UUID> playerEntityIds = new ConcurrentHashMap<>();

    public PlayerHider(TotemPractice plugin) {
        this.plugin = plugin;
        this.hide = plugin.getMainConfig().hide();

        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.getAPI().getEventManager().registerListener(
                    new PacketListenerAbstract(PacketListenerPriority.NORMAL) {

                        @Override
                        public void onPacketSend(@NotNull PacketSendEvent event) {
                            if (!hide.matchPlayersFromEachOther()) {
                                return;
                            }

                            if (event.getPacketType() != PacketType.Play.Server.ENTITY_SOUND_EFFECT) {
                                return;
                            }

                            WrapperPlayServerEntitySoundEffect packet =
                                    new WrapperPlayServerEntitySoundEffect(event);

                            UUID targetId = playerEntityIds.get(packet.getEntityId());
                            if (targetId == null) {
                                return;
                            }

                            Player viewer = Bukkit.getPlayer(event.getUser().getUUID());
                            if (viewer == null) {
                                return;
                            }

                            if (viewer.getUniqueId().equals(targetId)) {
                                return;
                            }

                            Player target = Bukkit.getPlayer(targetId);
                            if (target == null) {
                                return;
                            }

                            if (!isVisibleTo(roundOf(viewer), target)) {
                                event.setCancelled(true);
                            }
                        }
                    }
            );
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        refreshLater();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTeleport(PlayerTeleportEvent event) {
        if (event.getTo() == null) {
            return;
        }

        if (event.getFrom().getWorld() != event.getTo().getWorld()) {
            refreshLater();
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRoundStart(RoundStartEvent event) {
        refreshLater();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRoundEnd(RoundEndEvent event) {
        refreshLater();
    }

    /**
     * Recomputes the visibility of every online player from scratch, based on
     * their current round membership.
     */
    public void refreshVisibility() {
        List<Player> online = List.copyOf(Bukkit.getOnlinePlayers());

        playerEntityIds.clear();

        for (Player player : online) {
            playerEntityIds.put(player.getEntityId(), player.getUniqueId());
        }

        for (Player viewer : online) {
            Round viewerRound = roundOf(viewer);

            for (Player target : online) {
                if (viewer == target) {
                    continue;
                }

                if (isVisibleTo(viewerRound, target)) {
                    showPlayer(viewer, target);
                    setListed(viewer, target, true);
                } else {
                    hidePlayer(viewer, target);

                    if (hide.matchPlayersInTab()) {
                        setListed(viewer, target, false);
                    }
                }
            }
        }
    }

    private void refreshLater() {
        Bukkit.getScheduler().runTaskLater(plugin, this::refreshVisibility, 2L);
    }

    private boolean isVisibleTo(Round viewerRound, Player target) {
        Round targetRound = roundOf(target);

        if (viewerRound == null) {
            return targetRound == null;
        }

        if (targetRound == viewerRound) {
            return true;
        }

        if (targetRound == null) {
            return false;
        }

        return !hide.matchPlayersFromEachOther();
    }

    private Round roundOf(Player player) {
        return plugin.getRounds().find(player.getUniqueId());
    }

    private void showPlayer(Player viewer, Player target) {
        if (!viewer.canSee(target)) {
            viewer.showPlayer(plugin, target);
        }
    }

    private void hidePlayer(Player viewer, Player target) {
        if (viewer.canSee(target)) {
            viewer.hidePlayer(plugin, target);
        }
    }

    private void setListed(Player viewer, Player target, boolean listed) {
        if (!SoftDependUtil.PACKET_EVENTS_ENABLED) {
            return;
        }

        WrapperPlayServerPlayerInfoUpdate.PlayerInfo info =
                new WrapperPlayServerPlayerInfoUpdate.PlayerInfo(target.getUniqueId());

        info.setListed(listed);

        WrapperPlayServerPlayerInfoUpdate update =
                new WrapperPlayServerPlayerInfoUpdate(
                        WrapperPlayServerPlayerInfoUpdate.Action.UPDATE_LISTED,
                        info
                );

        PacketEvents.getAPI().getPlayerManager().sendPacket(viewer, update);
    }
}