package dev.lokspel.totempractice.config.gui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class GuiConfig {

  @Comment({
      "Difficulty selection GUI settings.",
      "",
      "Placeholders for item lore:",
      "  %interval%   - Time between lethal hits in seconds.",
      "  %multiplier% - Score multiplier.",
      "  %offhand%    - Offhand warning or empty.",
      "  %random%     - Random totem warning or empty.",
      "Slot keys must match difficulty names in config.yml."
  })
  @JsonProperty("difficulty-selection")
  private DifficultySelectionGui difficultySelection = new DifficultySelectionGui();
}
