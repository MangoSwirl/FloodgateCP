# FloodgateCP
FloodgateCP is an indev [Spigot](https://www.spigotmc.org/) plugin that adds Bedrock Edition forms from [Floodgate](https://github.com/GeyserMC/Floodgate) to [CommandPanels](https://www.spigotmc.org/resources/command-panels-custom-guis.67788/).

[Download the plugin here](https://github.com/MangoSwirl/FloodgateCP/releases).

[Report bugs here](https://github.com/MangoSwirl/FloodgateCP/issues).

[Get support or chat on our Discord server here](https://discord.gg/N7hKDtFwZA).

## Differences on Bedrock Edition we cannot change:
- When a button is pressed, the panel will always close. You can get around this by reopening the panel in the commands.
- The server can't close bedrock panels, only the player can.
- You can't move the items around, because they aren't items.
- While a panel is opened, the text/content on it cannot be dynamically changed.
## Planned features:
- `onopen` and `onclose` support for each panel.
- Maybe other types of bedrock panels, like `FORM` or `MODAL`.
- _Possibly_ an ingame editor.
- _Possibly_ an automatic panel converter.
