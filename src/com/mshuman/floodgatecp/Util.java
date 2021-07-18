package com.mshuman.floodgatecp;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

public class Util {
    public static String getLang(String key) {
        return FloodgateCP.getInstance().getConfig().getString("lang." + key);
    }
    public static FileConfiguration getConfig() {
        return FloodgateCP.getInstance().getConfig();
    }
    public static void handleResponse(String panel, SimpleFormResponse response, SimpleForm form, Player player) {

        String buttonHandle = getConfig().getConfigurationSection("panels." + panel + ".buttons").getKeys(false).toArray()[response.getClickedButtonId()].toString();

        for (String i : getConfig().getStringList("panels." + panel + ".buttons." + buttonHandle + ".commands")) {
            if (i.startsWith("console= ")) {
                FloodgateCP.getInstance().getServer().dispatchCommand(FloodgateCP.getInstance().getServer().getConsoleSender(), i.replaceFirst("console= ", "").replaceAll("%cp-player-name%", player.getName()));
            } else if (i.startsWith("msg= ")) {
                player.sendMessage(i.replaceFirst("msg= ", "").replaceAll("%cp-player-name%", player.getName()));
            } else {
                player.performCommand(i.replaceAll("%cp-player-name%", player.getName()));
            }
        }

    }
}