package dev.lokspel.totempractice.config.settings;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class DifficultyEntry {

  @JsonProperty("hit-interval")
  private double hitInterval = 1.0;

  @JsonProperty("offhand-hit-interval")
  private double offhandHitInterval = 1.0;

  @JsonProperty("score-multiplier")
  private double scoreMultiplier = 1.0;

  @JsonProperty("offhand-only")
  private boolean offhandOnly = false;

  @JsonProperty("random-totem")
  private boolean randomTotem = false;

  public double effectiveHitInterval() {
    return offhandOnly ? offhandHitInterval : hitInterval;
  }
}
