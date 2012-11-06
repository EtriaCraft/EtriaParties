package com.etriacraft.etriaparties.command.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.PartyAPI;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.wrappers.Party;

public class Create extends GameCommand {
	
	@Override
	public boolean canExecute(final CommandSender sender, final String[] split) {
		return split.length > 2 && split[0].equalsIgnoreCase("party")
				&& split[1].equalsIgnoreCase("create");
	}
	
	@Override
	public boolean execute(final CommandSender sender, final String[] split) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("You can't create parties!");
			return true;
		}
		final Player player = (Player) sender;
		if (PartyAPI.inParty(player)) {
			player.sendMessage(ChatColor.RED + "You must leave your current party before making a new one!");
			return true;
		}
		if (PartyAPI.getParty(split[2]) != null) {
			player.sendMessage(ChatColor.RED + "A party with that name already exists!");
			return true;
		}
		if (split.length == 3) {
			PartyAPI.addParty(new Party(player, split[2]));
			player.sendMessage(ChatColor.GREEN + "Party successfully created!");
			return true;
		} else if (split.length == 4) {
			if (split[3].startsWith("p:")) {
				if (split[3].length() > 2) {
					PartyAPI.addParty(new Party(player, split[2], split[3]
						.substring(2)));
					player.sendMessage(ChatColor.GREEN + 
							"Party successfully created!");
					return true;
				}
			} else if (split[3].equalsIgnoreCase("-lock")) {
				PartyAPI.addParty(new Party(player, split[2], "", true));
				player.sendMessage(ChatColor.GREEN + 
						" Party successfully created!");
				return true;
			}
		}
		player.sendMessage(ChatColor.RED + "Invalid Command Usage! Type /p help for help!");
		return true;
	}
	
	@Override
	public String[] getPermission() {
		return new String[] { "etriaparties.create", "etriaparties.join", "etriaparties.leave" };
	}

}
