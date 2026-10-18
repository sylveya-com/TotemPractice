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
public class ResultsMessages {

  @JsonProperty("title")
  private String title = "<gold><bold>Totem Practice";

  @JsonProperty("subtitle")
  private String subtitle = "<white>Ended with Score {score}";
}
