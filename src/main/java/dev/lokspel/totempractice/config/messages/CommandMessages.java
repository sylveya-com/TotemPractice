package dev.lokspel.totempractice.config.messages;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class CommandMessages {

  @JsonProperty("player-only")
  private String playerOnly = "<red>Only players can use this command.";

  @JsonProperty("no-permission")
  private String noPermission = "<red>You don't have permission to use this command.";

  @JsonProperty("config-reloaded")
  private String configReloaded = "{prefix}<green>Configuration reloaded successfully.";

  @JsonProperty("point-set")
  private String pointSet = "{prefix}<green>Teleport point {type} set.";
}
