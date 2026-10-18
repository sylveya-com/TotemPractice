package dev.lokspel.totempractice.config.settings;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;

@Getter
@Setter
public class CommandsSettings {
  @Comment({
      "Commands run after a player respawns in the lobby.",
      "Prefix with [player] to run as the player or [console] for console (default).",
      "Placeholders: %player%, %uuid%.",
      "",
      "Example:",
      "  on-round-end:",
      "    - command: \"[player] practice lobby load\"",
      "      bypass-permissions:",
      "        - \"zpp.practice.lobby\"",
      "    - command: \"[console] say Round finished\""
  })
  @JsonProperty("on-round-end")
  private List<CommandEntry> onRoundEnd = new ArrayList<>();
}
