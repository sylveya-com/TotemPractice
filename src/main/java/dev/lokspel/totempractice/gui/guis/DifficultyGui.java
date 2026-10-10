package dev.lokspel.totempractice.gui.guis;

import dev.lokspel.totempractice.config.GuiConfig;
import dev.lokspel.totempractice.config.section.DifficultiesSection;
import dev.lokspel.totempractice.game.Difficulty;
import dev.lokspel.totempractice.game.RoundManager;
import dev.lokspel.totempractice.gui.Gui;
import dev.lokspel.totempractice.gui.GuiType;
import dev.lokspel.totempractice.util.ItemBuilder;
import dev.lokspel.totempractice.util.InventoryUtil;
import dev.lokspel.totempractice.util.NumberUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class DifficultyGui extends Gui {

    private final RoundManager roundManager;
    private final DifficultiesSection difficulties;
    private final Map<String, GuiConfig.SlotConfig> slots;
    private final Map<Integer, String> slotToDifficulty;
    private final String fillMaterial;
    private final String offhandWarning;
    private final String randomWarning;

    public DifficultyGui(RoundManager roundManager, DifficultiesSection difficulties, GuiConfig config) {
        super(GuiType.DIFFICULTY_SELECTION, config.title(), config.size());
        this.roundManager = roundManager;
        this.difficulties = difficulties;
        this.slots = config.difficultySlots();
        this.slotToDifficulty = new HashMap<>();
        for (Map.Entry<String, GuiConfig.SlotConfig> entry : slots.entrySet()) {
            slotToDifficulty.put(entry.getValue().slot(), entry.getKey());
        }
        this.fillMaterial = config.fillMaterial();
        this.offhandWarning = config.offhandWarning();
        this.randomWarning = config.randomWarning();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }
        String name = slotToDifficulty.get(event.getSlot());
        if (name == null) {
            return;
        }
        Difficulty difficulty = difficulties.get(name);
        if (difficulty == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        close(player);
        roundManager.start(player, difficulty);
    }

    @Override
    protected void populate(Inventory inventory) {
        InventoryUtil.fillAll(inventory, fillMaterial, Material.GRAY_STAINED_GLASS_PANE);
        slots.forEach((name, slot) -> {
            Difficulty difficulty = difficulties.get(name);
            if (difficulty != null) {
                inventory.setItem(slot.slot(), icon(difficulty, slot));
            }
        });
    }

    private ItemStack icon(Difficulty difficulty, GuiConfig.SlotConfig slot) {
        return new ItemBuilder(Material.matchMaterial(slot.material()), Material.LIME_CONCRETE)
                .name(slot.name())
                .lore(placeholders(slot.lore(), difficulty))
                .glint(slot.glint())
                .build();
    }

    private List<String> placeholders(List<String> lore, Difficulty difficulty) {
        String offhand = difficulty.offhandOnly() ? offhandWarning : "";
        String random = difficulty.randomTotem() ? randomWarning : "";
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