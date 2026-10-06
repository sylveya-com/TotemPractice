# TotemPractice

Totem survival training plugin with configurable difficulties and rounds

## » About

TotemPractice is a training plugin focused on totem survival.

Players select a difficulty from a GUI and start a round at a configured match location. During the round, they receive a full inventory of totems and are periodically hit with lethal damage.

Each successful totem pop increases the player's score. The round continues until the player fails to survive a hit.

Difficulties control the time between hits and the score multiplier, allowing different levels of reaction speed to be practiced.

## » Difficulties

Each difficulty defines:

* Lethal hit interval
* Score multiplier

Difficulties and their GUI entries can be configured through `guis.yml`.

## » Rounds

Before a round starts, an actionbar countdown is shown and the player is teleported to the configured match location.

During a round:

* Totems are automatically provided
* Totems cannot be dropped
* Inventory is kept on death
* The current score and round statistics can be accessed through PlaceholderAPI

When the round ends, the final score is displayed in a title.

## » Commands

| Command                      | Aliases           | Description                       | Permission          |
| ---------------------------- | ----------------- | --------------------------------- | ------------------- |
| `/totempractice`             | `/tp`             | Open the difficulty selection GUI | `totempractice.use` |
| `/totempractice set match`   | `/tp set match`   | Set the match location            | `totempractice.set` |
| `/totempractice set lobby`   | `/tp set lobby`   | Set the lobby respawn location    | `totempractice.set` |

## » Tab List Hiding

When `hide.match-players-in-tab` is enabled and PacketEvents is installed, players currently in a round are hidden from the tab list of players outside that round.

## » Placeholders

Requires [PlaceholderAPI](https://placeholderapi.com).

The expansion is available under both `totempractice` and `tp`.

| Placeholder                        | Description                       |
| ---------------------------------- | --------------------------------- |
| `%totempractice_state%`            | Current state: `none` or `round`  |
| `%totempractice_score%`            | Current score                     |
| `%totempractice_totems_used%`      | Totems used in the current round  |
| `%totempractice_difficulty%`       | Current difficulty                |
| `%totempractice_hit_interval%`     | Seconds between lethal hits       |
| `%totempractice_score_multiplier%` | Current score multiplier          |
| `%totempractice_next_hit%`         | Seconds until the next lethal hit |

## » Requirements

- **Java 25**
- **Spigot, Paper**
- **Minecraft `1.21.11`, `26.1`, or `26.2`**
- Optional: [PacketEvents](https://github.com/retrooper/packetevents) for match player tab-list hiding
- Optional: [PlaceholderAPI](https://placeholderapi.com) for placeholders

## » Build

```bash 
gradlew build
```

Enjoy TotemPractice!
