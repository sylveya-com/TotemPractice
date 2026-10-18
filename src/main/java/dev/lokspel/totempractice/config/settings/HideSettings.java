package dev.lokspel.totempractice.config.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
@Setter
public class HideSettings {
  @Comment("Hide players in a round from tab list of players outside that round.")
  @JsonProperty("match-players-in-tab")
  private boolean matchPlayersInTab = false;

  @Comment("Hide players participating in the same round from each other.")
  @JsonProperty("match-players-from-each-other")
  private boolean matchPlayersFromEachOther = true;
}
