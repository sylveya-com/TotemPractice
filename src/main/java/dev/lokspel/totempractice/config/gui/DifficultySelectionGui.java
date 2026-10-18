package dev.lokspel.totempractice.config.gui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class DifficultySelectionGui {

  @Comment("GUI title.")
  @JsonProperty("title")
  private String title = "<white><bold>Choose difficulty";

  @Comment("Inventory size (multiple of 9).")
  @JsonProperty("size")
  private int size = 27;

  @Comment("Material used for empty slots.")
  @JsonProperty("fill-material")
  private String fillMaterial = "gray_stained_glass_pane";

  @Comment("Line shown only on difficulties with offhand-only enabled (via %offhand%).")
  @JsonProperty("offhand-warning")
  private String offhandWarning = "<red><bold>⚠ <red>Totem must be in offhand!";

  @Comment("Line shown only on difficulties with random-totem enabled (via %random%).")
  @JsonProperty("random-warning")
  private String randomWarning = "<gold><bold>? <gold>Find the hidden totem!";

  @Comment("Difficulty item slots mapped by difficulty name.")
  @JsonProperty("slots")
  private Map<String, SlotEntry> slots = defaultSlots();

  private static Map<String, SlotEntry> defaultSlots() {
    Map<String, SlotEntry> map = new LinkedHashMap<>();
    map.put("easy", new SlotEntry(
        10,
        "green_stained_glass_pane",
        "<green><bold>Easy",
        false,
        List.of(
            "<gray>Lethal interval <dark_gray>» <white>%interval%s",
            "<gray>Score multiplier <dark_gray>» <green>x%multiplier%",
            "%offhand%",
            "%random%",
            "",
            "<gray>(<white>Click to start)"
        )
    ));
    map.put("normal", new SlotEntry(
        12,
        "yellow_stained_glass_pane",
        "<yellow><bold>Normal",
        true,
        List.of(
            "<gray>Lethal interval <dark_gray>» <white>%interval%s",
            "<gray>Score multiplier <dark_gray>» <green>x%multiplier%",
            "%offhand%",
            "%random%",
            "",
            "<gray>(<white>Click to start)"
        )
    ));
    map.put("hard", new SlotEntry(
        14,
        "orange_stained_glass_pane",
        "<gold><bold>Hard",
        true,
        List.of(
            "<gray>Lethal interval <dark_gray>» <white>%interval%s",
            "<gray>Score multiplier <dark_gray>» <green>x%multiplier%",
            "%offhand%",
            "%random%",
            "",
            "<gray>(<white>Click to start)"
        )
    ));
    map.put("impossible", new SlotEntry(
        16,
        "red_stained_glass_pane",
        "<red><bold>Impossible",
        true,
        List.of(
            "<gray>Lethal interval <dark_gray>» <white>%interval%s",
            "<gray>Score multiplier <dark_gray>» <green>x%multiplier%",
            "%offhand%",
            "%random%",
            "",
            "<gray>(<white>Click to start)"
        )
    ));
    return map;
  }
}
