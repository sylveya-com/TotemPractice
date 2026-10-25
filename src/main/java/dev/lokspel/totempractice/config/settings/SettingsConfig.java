package dev.lokspel.totempractice.config.settings;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class SettingsConfig {

  @Comment({
      "Configure the difficulty levels for your Totem Practice rounds.",
      "",
      "hit-interval: Time in seconds between lethal hits.",
      "              Lower values make the difficulty more challenging.",
      "",
      "offhand-hit-interval: Hit interval when offhand-only is enabled.",
      "                      A longer interval gives players more time",
      "                      to manually re-equip their totems.",
      "",
      "score-multiplier: Score multiplier awarded for this difficulty.",
      "                  Higher values grant more points per action.",
      "",
      "offhand-only: If enabled, totems can only be used from the offhand.",
      "              The main hand cannot be used to activate totems.",
      "",
      "random-totem: If enabled, only a single totem is placed in a random",
      "              inventory slot instead of filling the whole inventory.",
      "              After each successful totem pop a new one is hidden in a",
      "              random slot again, so the player must find it before use.",
      "",
      "You can create custom difficulties by copying an existing section,",
      "renaming it, and adjusting its values."
  })
  @JsonProperty("difficulties")
  private Map<String, DifficultyEntry> difficulties = defaultDifficulties();

  @Comment("Player visibility during active rounds (requires PacketEvents).")
  @JsonProperty("hide")
  private HideSettings hide = new HideSettings();

  private static Map<String, DifficultyEntry> defaultDifficulties() {
    Map<String, DifficultyEntry> map = new LinkedHashMap<>();
    map.put("easy", new DifficultyEntry(1.5, 1.5, 1.0, false, false));
    map.put("normal", new DifficultyEntry(0.8, 0.8, 2.0, false, true));
    map.put("hard", new DifficultyEntry(0.45, 0.45, 3.0, false, true));
    map.put("impossible", new DifficultyEntry(0.25, 1.25, 5.0, true, true));
    return map;
  }
}
