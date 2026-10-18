package dev.lokspel.totempractice.gui.guis;

import dev.lokspel.totempractice.config.TotemPracticeConfig;
import dev.lokspel.totempractice.config.gui.DifficultySelectionGui;
import dev.lokspel.totempractice.config.gui.SlotEntry;
import dev.lokspel.totempractice.game.Difficulty;
import dev.lokspel.totempractice.game.RoundManager;
import dev.lokspel.totempractice.gui.Gui;
import dev.lokspel.totempractice.gui.GuiType;
import dev.lokspel.totempractice.util.InventoryUtil;
import dev.lokspel.totempractice.util.ItemBuilder;
import dev.lokspel.totempractice.util.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

public final class DifficultyGui extends Gui {

    private final RoundManager roundManager;

    public DifficultyGui(RoundManager roundManager) {
        super(
                GuiType.DIFFICULTY_SELECTION,
                TotemPracticeConfig.gui().getDifficultySelection().getTitle(),
                TotemPracticeConfig.gui().getDifficultySelection().getSize()
        );
        this.roundManager = roundManager;
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }
        DifficultySelectionGui gui = TotemPracticeConfig.gui().getDifficultySelection();
        String name = null;
        for (Map.Entry<String, SlotEntry> entry : gui.getSlots().entrySet()) {
            if (entry.getValue().getSlot() == event.getSlot()) {
                name = entry.getKey();
                break;
            }
        }
        if (name == null) {
            return;
        }
        Difficulty difficulty = TotemPracticeConfig.getDifficulty(name);
        if (difficulty == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        close(player);
        roundManager.start(player, difficulty);
    }

    @Override
    protected void populate(Inventory inventory) {
        DifficultySelectionGui gui = TotemPracticeConfig.gui().getDifficultySelection();
        InventoryUtil.fillAll(inventory, gui.getFillMaterial(), Material.GRAY_STAINED_GLASS_PANE);
        gui.getSlots().forEach((name, slot) -> {
            Difficulty difficulty = TotemPracticeConfig.getDifficulty(name);
            if (difficulty != null) {
                inventory.setItem(slot.getSlot(), icon(difficulty, slot, gui));
            }
        });
    }

    private ItemStack icon(Difficulty difficulty, SlotEntry slot, DifficultySelectionGui gui) {
        return new ItemBuilder(Material.matchMaterial(slot.getMaterial()), Material.LIME_CONCRETE)
                .name(slot.getName())
                .lore(placeholders(slot.getLore(), difficulty, gui))
                .glint(slot.isGlint())
                .build();
    }

    private List<String> placeholders(List<String> lore, Difficulty difficulty, DifficultySelectionGui gui) {
        String offhand = difficulty.offhandOnly() ? gui.getOffhandWarning() : "";
        String random = difficulty.randomTotem() ? gui.getRandomWarning() : "";
        return lore.stream()
                .map(line -> line
                        .replace("%interval%", NumberUtil.format(difficulty.effectiveHitInterval()))
                        .replace("%multiplier%", NumberUtil.format(difficulty.scoreMultiplier()))
                        .replace("%offhand%", offhand)
                        .replace("%random%", random))
                .filter(line -> !line.isBlank())
                .toList();
    }
}