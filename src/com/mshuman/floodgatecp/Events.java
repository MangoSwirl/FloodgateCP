package com.mshuman.floodgatecp;

import me.rockyhawk.commandpanels.api.PanelOpenedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.geysermc.floodgate.api.FloodgateApi;

public class Events implements Listener {
    @EventHandler
    public void onPanelOpen(PanelOpenedEvent event) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(event.getPlayer().getUniqueId())) return;

        event.getPlayer().sendMessage(FloodgateCP.getInstance().getConfig().getString("lang.prefix") + FloodgateCP.getInstance().getConfig().getString("lang.panel-not-available"));
        event.setCancelled(true);
    }
}
