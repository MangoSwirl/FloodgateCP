package com.mshuman.floodgatecp;

import me.rockyhawk.commandpanels.api.PanelOpenedEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.floodgate.api.FloodgateApi;
import org.geysermc.floodgate.api.player.FloodgatePlayer;


public class Events implements Listener {
    @EventHandler
    public void onPanelOpen(PanelOpenedEvent event) {
        if (!FloodgateApi.getInstance().isFloodgatePlayer(event.getPlayer().getUniqueId())) return;

        if (FloodgateCP.getInstance().getConfig().getConfigurationSection("panels").contains(event.getPanel().getName())) {
            FloodgatePlayer fgPlayer = FloodgateApi.getInstance().getPlayer(event.getPlayer().getUniqueId());
            ConfigurationSection fgPanel = Util.getConfig().getConfigurationSection("panels." + event.getPanel().getName());
            SimpleForm.Builder form = SimpleForm.builder();
            form = form.title(fgPanel.getString("title"));
            form = form.content(fgPanel.getString("content"));

            for (String i : fgPanel.getConfigurationSection("buttons").getKeys(false)) {
                form = form.button(fgPanel.getString("buttons." + i + ".content"));
            }
            form = form.responseHandler((SimpleForm returnedForm, String rawResponse) -> {
                SimpleFormResponse response = returnedForm.parseResponse(rawResponse);
                if (!response.isCorrect()) return;
                Util.handleResponse(event.getPanel().getName(), response, returnedForm, event.getPlayer());
            });
            fgPlayer.sendForm(form.build());
        } else {
            event.getPlayer().sendMessage(Util.getLang("prefix") + Util.getLang("panel-not-available"));
        }
        event.setCancelled(true);
    }
}
