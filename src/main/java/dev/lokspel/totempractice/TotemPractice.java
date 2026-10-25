package dev.lokspel.totempractice;

import dev.lokspel.totempractice.api.TotemPracticeAPI;
import dev.lokspel.totempractice.commands.totempractice.TotemPracticeCmd;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.game.RoundManager;
import dev.lokspel.totempractice.gui.guis.DifficultyGui;
import dev.lokspel.totempractice.gui.listener.GuiCloseListener;
import dev.lokspel.totempractice.gui.listener.GuiInteractionListener;
import dev.lokspel.totempractice.listener.EntityResurrectListener;
import dev.lokspel.totempractice.listener.PlayerDeathListener;
import dev.lokspel.totempractice.listener.PlayerDropItemListener;
import dev.lokspel.totempractice.listener.PlayerPickupListener;
import dev.lokspel.totempractice.listener.PlayerQuitListener;
import dev.lokspel.totempractice.listener.PlayerTeleportListener;
import dev.lokspel.totempractice.util.PluginPermission;
import dev.lokspel.totempractice.util.SoftDependUtil;
import dev.lokspel.totempractice.util.entityhider.PlayerHider;
import dev.lokspel.totempractice.util.placeholderapi.PlayerExpansion;
import com.github.retrooper.packetevents.PacketEvents;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class TotemPractice extends JavaPlugin {

    @Getter
    private static TotemPractice instance;

    private RoundManager roundManager;
    private PlayerHider playerHider;

    private final BukkitContext fastStatsContext = new BukkitContext.Factory(this, "7a66e6383b0e054e6a12749a58fb28f8")
            .metrics(Metrics.Factory::create)
            .create();

    @Override
    public void onLoad() {
        instance = this;

        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.setAPI(SpigotPacketEventsBuilder.build(this));
            PacketEvents.getAPI().load();
        }
    }

    @Override
    public void onEnable() {
        instance = this;

        TotemPracticeConfig.load(getDataFolder().toPath());

        roundManager = new RoundManager(this);

        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.getAPI().init();
            playerHider = new PlayerHider(this);
            getServer().getPluginManager().registerEvents(playerHider, this);
        }

        new DifficultyGui(roundManager);

        getServer().getScheduler().runTaskTimer(this, roundManager::tick, 5L, 1L);

        PluginPermission.registerAll();
        new TotemPracticeCmd().enable();

        getServer().getPluginManager().registerEvents(new GuiInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new GuiCloseListener(), this);
        getServer().getPluginManager().registerEvents(new EntityResurrectListener(roundManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(roundManager), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupListener(roundManager), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(roundManager), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListener(this), this);

        new TotemPracticeAPI(this);

        if (SoftDependUtil.PLACEHOLDER_API_ENABLED) {
            new PlayerExpansion(this, "totempractice").register();
            new PlayerExpansion(this, "tp").register();
        }

        fastStatsContext.ready();
    }

    @Override
    public void onDisable() {
        PluginPermission.unregisterAll();
        fastStatsContext.shutdown();
        if (roundManager != null) {
            roundManager.disable();
        }
        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.getAPI().terminate();
        }
        instance = null;
    }
}
