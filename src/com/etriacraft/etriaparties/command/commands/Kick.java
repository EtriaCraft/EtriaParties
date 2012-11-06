package com.etriacraft.etriaparties.command.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.PartyAPI;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.wrappers.Party;

public class Kick extends GameCommand {

    @Override
    public boolean canExecute(final CommandSender sender, final String[] split) {
        return split.length > 2 && split[0].equalsIgnoreCase("party")
                && split[1].equalsIgnoreCase("kick");
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] split) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't invite to parties!");
            return true;
        }
        final Player player = (Player) sender;
        Party p;
        if (!PartyAPI.inParty(player)) {
            player.sendMessage(ChatColor.RED + "You are not in a party!");
            return true;
        }
        if (split.length == 3) {
            if ((p = PartyAPI.getParty(player)) != null) {
                if (!player.hasPermission("etriaparties.admin")) {
                    if (!p.getOwner().equals(player)) {
                        player.sendMessage(ChatColor.RED
                                + "You are not the party owner!");
                        return true;
                    }
                }
                Player kick;
                if ((kick = Bukkit.getPlayer(split[2])) != null) {
                    p.removeMember(kick);
                    PartyAPI.setPartyChatMode(player, false);
                    p.sendPartyMessage(ChatColor.GREEN + kick.getDisplayName()
                            + " has been kicked from the party!");
                    kick.sendMessage(ChatColor.RED
                            + "You have been kicked from the party!");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "Player not found!");
                return true;
            }
            player.sendMessage(ChatColor.RED
                    + "Your party was not found... Say what?!?!");
            return true;
        }
        player.sendMessage(ChatColor.RED
                + "Invalid command usage! Type /p help for help!");
        return true;
    }

    @Override
    public String[] getPermission() {
        return new String[] { "etriaparties.kick" };
    }

}