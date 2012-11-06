package com.etriacraft.etriaparties.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.PartyAPI;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.wrappers.Party;

public class ListMembers extends GameCommand {

    @Override
    public boolean canExecute(final CommandSender sender, final String[] split) {
        return split.length > 1 && split[0].equalsIgnoreCase("party")
                && split[1].equalsIgnoreCase("list");
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] split) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't list the players of a party from the console!");
            return true;
        }
        final Player player = (Player) sender;
        Party p;
        if (split.length == 1) {
            if (PartyAPI.inParty(player)) {
                if ((p = PartyAPI.getParty(player)) != null) {
                    player.sendMessage(p.listMembers());
                    return true;
                }
            } else {
                player.sendMessage(ChatColor.GREEN + "Green " + ChatColor.WHITE
                        + " are open parties" + ChatColor.RED + " Red"
                        + ChatColor.WHITE
                        + " are password protected parties and "
                        + ChatColor.BLUE + "Blue" + ChatColor.WHITE
                        + " are invite only parties!");
                player.sendMessage(PartyAPI.listParties());
                return true;
            }
        } else if (split.length == 2) {
            if (PartyAPI.exists(split[1])) {
                final Party par = PartyAPI.getParty(split[1]);
                player.sendMessage(par.listMembers());
                return true;
            }
            sender.sendMessage(ChatColor.RED + "Party not found!");
            return true;
        }
        player.sendMessage(ChatColor.RED
                + "Invalid command usage! Type /p help for help!");
        return true;
    }

    @Override
    public String[] getPermission() {
        return new String[] { "etriaparties.list" };
    }
    
}

