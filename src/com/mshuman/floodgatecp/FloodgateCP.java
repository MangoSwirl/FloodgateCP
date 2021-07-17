package com.mshuman.floodgatecp;

import org.bukkit.plugin.java.JavaPlugin;

public class FloodgateCP extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getConsoleSender().sendMessage("§2FloodgateCP Enabled!");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§cFloodgateCP Disabled.");
    }
}
