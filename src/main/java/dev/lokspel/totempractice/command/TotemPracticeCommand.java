package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.MainConfig;
import dev.lokspel.totempractice.gui.Gui;
import dev.lokspel.totempractice.gui.GuiType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TotemPracticeCommand implements SubCommand {

    private final MainConfig config;

    public TotemPracticeCommand(TotemPractice plugin) {
        this.config = plugin.getMainConfig();
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(config.messages().get("player-only"));
            return true;
        }
        Gui.search(GuiType.DIFFICULTY_SELECTION).open(player);
        return true;
    }
}