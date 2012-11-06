package com.etriacraft.etriaparties.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.PartyAPI;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.wrappers.Party;

public class Leave extends GameCommand {

    @Override
    public boolean canExecute(final CommandSender sender, final String[] split) {
        return split.length > 1 && split[0].equalsIgnoreCase("party")
                && split[1].equalsIgnoreCase("leave");
    }

    @Override
    public boolean execute(final CommandSender sender, final String[] split) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You can't leave parties!");
            return true;
        }
        final Player player = (Player) sender;
        Party p;
        if (!PartyAPI.inParty(player)) {
            player.sendMessage(ChatColor.RED + "You're not in a party!");
            return true;
        }
        if ((p = PartyAPI.getParty(player)) != null) {
            p.removeMember(player);
            PartyAPI.setPartyChatMode(player, false);
            if (!player.hasPermission("etriaparties.admin")) {
                p.sendPartyMessage(ChatColor.GREEN + player.getDisplayName()
                        + " has left the party!");
            }
            if (p.isEmpty()) {
                PartyAPI.removeParty(p);
            } else {
                if (p.getOwner().equals(player)) {
                    p.setNewOwner();
                    p.sendPartyMessage(ChatColor.GREEN
                            + p.getOwner().getDisplayName()
                            + " is the new party owner!");
                }
            }
            player.sendMessage(ChatColor.GREEN + "You have left the party!");
            return true;
        }
        player.sendMessage(ChatColor.RED
                + "Invalid command usage! Type /p help for help!");
        return true;
    }

    @Override
    public String[] getPermission() {
        return new String[] { "etriaparties.leave" };
    }

}