package com.mshuman.floodgatecp;

import me.rockyhawk.commandpanels.api.PanelOpenedEvent;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.geysermc.cumulus.SimpleForm;
import org.geysermc.cumulus.response.SimpleFormResponse;
import org.geysermc.cumulus.util.FormImage;
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
            form = form.title(fgPanel.getString("title").replaceAll("%cp-player-name%", event.getPlayer().getName()));
            form = form.content(fgPanel.getString("content").replaceAll("%cp-player-name%", event.getPlayer().getName()));

            for (String i : fgPanel.getConfigurationSection("buttons").getKeys(false)) {
                if (!fgPanel.contains("buttons." + i + ".icon")) {
                    form = form.button(fgPanel.getString("buttons." + i + ".content").replaceAll("%cp-player-name%", event.getPlayer().getName()));
                } else if (fgPanel.getString("buttons." + i + ".icon.type").equalsIgnoreCase("PATH")) {
                    form = form.button(fgPanel.getString("buttons." + i + ".content").replaceAll("%cp-player-name%", event.getPlayer().getName()), FormImage.Type.PATH, fgPanel.getString("buttons." + i + ".icon.texture"));
                } else if (fgPanel.getString("buttons." + i + ".icon.type").equalsIgnoreCase("URL")) {
                    form = form.button(fgPanel.getString("buttons." + i + ".content").replaceAll("%cp-player-name%", event.getPlayer().getName()), FormImage.Type.URL, fgPanel.getString("buttons." + i + ".icon.texture"));
                }
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
