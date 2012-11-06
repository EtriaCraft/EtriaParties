package com.etriacraft.etriaparties.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.PartyAPI;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.wrappers.Party;

public class Message extends GameCommand {

    @Override
    public boolean canExecute(final CommandSender sender, final String[] split) {
        return split.length > 0 && split[0].equalsIgnoreCase("pc");
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] split) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't chat with parties!");
            return true;
        }
        final Player player = (Player) sender;
        Party p;
        if (!PartyAPI.inParty(player)) {
            player.sendMessage(ChatColor.RED
                    + "You must be in a party to chat with the party!");
            return true;
        }
        if (split.length == 1) {
            if (PartyAPI.getPartyChatMode(player)) {
                PartyAPI.setPartyChatMode(player, false);
                player.sendMessage(ChatColor.GREEN
                        + "You messages will now go to game chat!");
                return true;
            } else {
                PartyAPI.setPartyChatMode(player, true);
                player.sendMessage(ChatColor.GREEN
                        + "You messages will now go to party chat!");
                return true;
            }
        } else {
            if ((p = PartyAPI.getParty(player)) != null) {
                String message = "";
                for (int i = 1; i < split.length; i++) {
                    message += split[i] + " ";
                }
                p.sendPartyChat(player,
                        message.substring(0, message.length() - 1));
                return true;
            }
            player.sendMessage(ChatColor.RED
                    + "Your party was not found... say what?!?!?");
        }
        player.sendMessage(ChatColor.RED
                + "Invalid command usage! Type /p help for help!");
        return true;
    }

    @Override
    public String[] getPermission() {
        return new String[] { "etriaparties.chat" };
    }

}