package dev.lokspel.totempractice.command;

import dev.lokspel.totempractice.TotemPractice;
import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.gui.Gui;
import dev.lokspel.totempractice.gui.GuiType;
import dev.lokspel.totempractice.util.MessageUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GuiCommand implements SubCommand {

    public GuiCommand(TotemPractice ignored) {
    }

    @Override
    public boolean execute(CommandSender sender, String[] args) {
        var messages = TotemPracticeConfig.messages().getCommands();
        if (!(sender instanceof Player player)) {
            MessageUtil.send(sender, messages.getPlayerOnly());
            return true;
        }
        Gui.search(GuiType.DIFFICULTY_SELECTION).open(player);
        return true;
    }
}