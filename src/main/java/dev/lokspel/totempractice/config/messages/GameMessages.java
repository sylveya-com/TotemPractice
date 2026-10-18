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
public class GameMessages {

  @JsonProperty("already-running")
  private String alreadyRunning = "{prefix}<red>A round is already running.";

  @JsonProperty("cannot-start-dead")
  private String cannotStartDead = "{prefix}<red>You cannot start a round while dead.";

  @JsonProperty("round-starting")
  private String roundStarting = "<white>Starting in <gold>{seconds}s";
}
