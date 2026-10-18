package dev.lokspel.totempractice.config.backed;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import me.whereareiam.configura.feature.comment.api.annotation.Comment;
import org.bukkit.Location;

@Getter
@Setter
@JsonAutoDetect(
    fieldVisibility = JsonAutoDetect.Visibility.ANY,
    getterVisibility = JsonAutoDetect.Visibility.NONE,
    isGetterVisibility = JsonAutoDetect.Visibility.NONE,
    setterVisibility = JsonAutoDetect.Visibility.NONE
)
public class BackedConfig {

  @Comment("Plugin lobby location.")
  @JsonProperty("lobby")
  private LocationEntry lobby = new LocationEntry();

  @Comment("Practice match location.")
  @JsonProperty("match")
  private LocationEntry match = new LocationEntry();

  public LocationEntry get(String name) {
    if ("lobby".equalsIgnoreCase(name)) {
      return lobby;
    }
    if ("match".equalsIgnoreCase(name)) {
      return match;
    }
    return null;
  }

  public Location location(String name) {
    LocationEntry entry = get(name);
    return entry != null ? entry.toLocation() : null;
  }
}
