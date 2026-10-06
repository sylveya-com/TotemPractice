package dev.lokspel.totempractice;

import dev.lokspel.totempractice.api.TotemPracticeAPI;
import dev.lokspel.totempractice.command.CommandDispatcher;
import dev.lokspel.totempractice.command.CommandDispatcher.RegisteredCommand;
import dev.lokspel.totempractice.command.SetCommand;
import dev.lokspel.totempractice.command.TotemPracticeCommand;
import dev.lokspel.totempractice.config.GuiConfig;
import dev.lokspel.totempractice.config.MainConfig;
import dev.lokspel.totempractice.game.Rounds;
import dev.lokspel.totempractice.gui.guis.DifficultyGui;
import dev.lokspel.totempractice.listener.EntityResurrectListener;
import dev.lokspel.totempractice.listener.GuiCloseListener;
import dev.lokspel.totempractice.listener.GuiInteractionListener;
import dev.lokspel.totempractice.listener.PlayerDeathListener;
import dev.lokspel.totempractice.listener.PlayerDropItemListener;
import dev.lokspel.totempractice.listener.PlayerPickupListener;
import dev.lokspel.totempractice.listener.PlayerQuitListener;
import dev.lokspel.totempractice.util.SoftDependUtil;
import dev.lokspel.totempractice.util.entityhider.PlayerHider;
import dev.lokspel.totempractice.util.placeholderapi.PlayerExpansion;
import com.github.retrooper.packetevents.PacketEvents;
import dev.faststats.Metrics;
import dev.faststats.bukkit.BukkitContext;
import io.github.retrooper.packetevents.factory.spigot.SpigotPacketEventsBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;

@Getter
public final class TotemPractice extends JavaPlugin {

    @Getter
    private static TotemPractice instance;

    private MainConfig mainConfig;
    private Rounds rounds;
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

        mainConfig = new MainConfig(this);
        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.getAPI().init();
            playerHider = new PlayerHider(this);
        }
        rounds = new Rounds(this);

        new DifficultyGui(rounds, mainConfig.difficulties(), new GuiConfig(this));

        getServer().getScheduler().runTaskTimer(this, rounds::tick, 5L, 1L);

        CommandDispatcher dispatcher = new CommandDispatcher(mainConfig, List.of(
                new RegisteredCommand("gui", new TotemPracticeCommand(this)),
                new RegisteredCommand("set", new SetCommand(this))
        ));
        var command = Objects.requireNonNull(getCommand("totempractice"));
        command.setExecutor(dispatcher);
        command.setTabCompleter(dispatcher);

        getServer().getPluginManager().registerEvents(new GuiInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new GuiCloseListener(), this);
        getServer().getPluginManager().registerEvents(new EntityResurrectListener(rounds), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDropItemListener(rounds), this);
        getServer().getPluginManager().registerEvents(new PlayerPickupListener(rounds), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(rounds), this);

        new TotemPracticeAPI(this);

        if (SoftDependUtil.PLACEHOLDER_API_ENABLED) {
            new PlayerExpansion(this, "totempractice").register();
            new PlayerExpansion(this, "tp").register();
        }

        fastStatsContext.ready();
    }

    @Override
    public void onDisable() {
        fastStatsContext.shutdown();
        if (SoftDependUtil.PACKET_EVENTS_ENABLED) {
            PacketEvents.getAPI().terminate();
        }
        instance = null;
    }
}