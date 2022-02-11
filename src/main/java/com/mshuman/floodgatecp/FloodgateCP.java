package com.mshuman.floodgatecp;

import org.bukkit.plugin.java.JavaPlugin;

public class FloodgateCP extends JavaPlugin {

    private static FloodgateCP instance;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        instance = this;
        getServer().getPluginCommand("floodgatecp").setExecutor(new Commands());
        getServer().getPluginManager().registerEvents(new Events(), this);
        getServer().getConsoleSender().sendMessage("§2FloodgateCP Enabled!");
    }
    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("§cFloodgateCP Disabled.");
    }

    public static FloodgateCP getInstance() {
        return instance;
    }
}
