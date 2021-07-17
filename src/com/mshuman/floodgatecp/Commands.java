package com.mshuman.floodgatecp;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (command.getName().equalsIgnoreCase("floodgatecp")) {
            if (args.length == 0) {
                commandSender.sendMessage("§cUsage: /fgcp reload");
                return true;
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (!commandSender.hasPermission("floodgatecp.reload")) {
                    commandSender.sendMessage(FloodgateCP.getInstance().getConfig().getString("lang.prefix") + FloodgateCP.getInstance().getConfig().getString("lang.no-permission"));
                    return true;
                }

                FloodgateCP.getInstance().reloadConfig();
                commandSender.sendMessage(FloodgateCP.getInstance().getConfig().getString("lang.prefix") + FloodgateCP.getInstance().getConfig().getString("lang.config-reloaded"));

            }
        }
        return true;
    }
}
