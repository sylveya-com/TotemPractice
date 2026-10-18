package dev.lokspel.totempractice.config.gui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
public class SlotEntry {

  @JsonProperty("slot")
  private int slot = 0;

  @JsonProperty("material")
  private String material = "lime_concrete";

  @JsonProperty("name")
  private String name = "";

  @JsonProperty("glint")
  private boolean glint = false;

  @JsonProperty("lore")
  private List<String> lore = new ArrayList<>();
}
