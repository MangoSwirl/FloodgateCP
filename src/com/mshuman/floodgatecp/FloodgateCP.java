package com.mshuman.floodgatecp;

import com.mshuman.floodgatecp.bungeecord.PluginMessage;
import org.bukkit.plugin.java.JavaPlugin;

public class FloodgateCP extends JavaPlugin {

    private static FloodgateCP instance;

    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();
        getServer().getPluginCommand("floodgatecp").setExecutor(new Commands());

        getServer().getPluginManager().registerEvents(new Events(), this);

        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());

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
