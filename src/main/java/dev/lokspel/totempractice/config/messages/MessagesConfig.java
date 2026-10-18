package dev.lokspel.totempractice.config.messages;

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
public class MessagesConfig {

  @Comment("Prefix prepended to messages using {prefix}.")
  @JsonProperty("prefix")
  private String prefix = "<gold>[TotemPractice] ";

  @JsonProperty("commands")
  private CommandMessages commands = new CommandMessages();

  @JsonProperty("game")
  private GameMessages game = new GameMessages();

  @JsonProperty("results")
  private ResultsMessages results = new ResultsMessages();
}
