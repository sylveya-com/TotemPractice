package dev.lokspel.totempractice.gui.guis;

import dev.lokspel.totempractice.config.GuiConfig;
import dev.lokspel.totempractice.config.section.DifficultiesSection;
import dev.lokspel.totempractice.game.Difficulty;
import dev.lokspel.totempractice.game.Rounds;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DifficultyGui extends Gui {

    private final Rounds rounds;
    private final DifficultiesSection difficulties;
    private final Map<Difficulty, GuiConfig.SlotConfig> slots;
    private final Map<Integer, Difficulty> slotToDifficulty;
    private final String fillMaterial;

    public DifficultyGui(Rounds rounds, DifficultiesSection difficulties, GuiConfig config) {
        super(GuiType.DIFFICULTY_SELECTION, config.title(), config.size());
        this.rounds = rounds;
        this.difficulties = difficulties;
        this.slots = config.difficultySlots();
        this.slotToDifficulty = slots.entrySet().stream()
                .collect(Collectors.toMap(entry -> entry.getValue().slot(), Map.Entry::getKey));
        this.fillMaterial = config.fillMaterial();
    }

    @Override
    public void handleClickEvent(InventoryClickEvent event) {
        if (event.getClickedInventory() != event.getView().getTopInventory()) {
            return;
        }
        Difficulty difficulty = slotToDifficulty.get(event.getSlot());
        if (difficulty == null) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        close(player);
        rounds.start(player, difficulty);
    }

    @Override
    protected void populate(Inventory inventory) {
        InventoryUtil.fillAll(inventory, fillMaterial, Material.GRAY_STAINED_GLASS_PANE);
        slots.forEach((difficulty, slot) -> inventory.setItem(slot.slot(), icon(difficulty, slot)));
    }

    private ItemStack icon(Difficulty difficulty, GuiConfig.SlotConfig slot) {
        return new ItemBuilder(Material.matchMaterial(slot.material()), Material.LIME_CONCRETE)
                .name(slot.name())
                .lore(placeholders(slot.lore(), difficulty))
                .glint(slot.glint())
                .build();
    }

    private List<String> placeholders(List<String> lore, Difficulty difficulty) {
        return lore.stream()
                .map(line -> line
                        .replace("%interval%", NumberUtil.format(difficulties.hitInterval(difficulty)))
                        .replace("%multiplier%", NumberUtil.format(difficulties.scoreMultiplier(difficulty))))
                .toList();
    }
}