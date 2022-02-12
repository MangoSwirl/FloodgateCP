package com.mshuman.floodgatecp;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;

import java.util.ArrayList;
import java.util.Collections;

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
            executeCommand(i, player);
        }

    }



    public static void executeCommand(String cmd, Player player) {
        ArrayList<String> commandTags = new ArrayList<String>();
        commandTags.add("console");
        commandTags.add("msg");
        commandTags.add("server");

        if (!commandTags.contains(cmd.split("= ")[0])) {
            Bukkit.dispatchCommand(player, parsePlaceholders(cmd, player));
            return;
        }

        String tag = cmd.split("= ")[0];
        String cmdWithoutTag = cmd.split("= ")[1];

        switch (tag) {
            case "console":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), parsePlaceholders(cmdWithoutTag, player));
                break;
            case "msg":
                player.sendMessage(parsePlaceholders(cmdWithoutTag, player).replaceAll("%n", "\n"));
                break;
            case "server":
                // This should apparently tell BungeeCord to change the player's server.
                ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("Connect");
                out.writeUTF(cmdWithoutTag);
                Player p = Bukkit.getPlayerExact(player.getName());
                assert p != null;
                p.sendPluginMessage(FloodgateCP.getInstance(), "BungeeCord", out.toByteArray());
                break;
        }
    }

    public static String parsePlaceholders(String initialString, Player player) {
        String tempString = initialString;

        // PlaceholderAPI
        tempString = parsePapiPlaceholders(tempString, player);
        // CommandPanels
        tempString = tempString.replaceAll("%cp-player-name%", player.getName());
        tempString = tempString.replaceAll("%cp-player-displayname%", player.getDisplayName());
        tempString = tempString.replaceAll("%cp-player-x%", String.valueOf(player.getLocation().getBlockX()));
        tempString = tempString.replaceAll("%cp-player-y%", String.valueOf(player.getLocation().getBlockY()));
        tempString = tempString.replaceAll("%cp-player-z%", String.valueOf(player.getLocation().getBlockZ()));
        tempString = tempString.replaceAll("%cp-player-world%", player.getWorld().getName());

        return tempString;
    }

    // Parses PlaceholderAPI placeholders, if available.
    public static String parsePapiPlaceholders(String initialString, Player player) {
        // If the PlaceholderAPI isn't installed, return the original string.
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) return initialString;
        // Return the parsed string
        return PlaceholderAPI.setPlaceholders(player, initialString);
    }
}