package com.etriacraft.etriaparties.managers;

import java.util.LinkedList;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.etriacraft.etriaparties.EtriaParties;
import com.etriacraft.etriaparties.command.GameCommand;
import com.etriacraft.etriaparties.command.commands.*;

public class CommandManager {

	private final LinkedList<GameCommand> gameCommands = new LinkedList<GameCommand>();
	
	public CommandManager() {
		gameCommands.add(new Create());
		gameCommands.add(new Invite());
		gameCommands.add(new Join());
		gameCommands.add(new Leave());
		gameCommands.add(new Kick());
		gameCommands.add(new ListMembers());
		gameCommands.add(new Message());
		gameCommands.add(new Help());
	}
	
	public boolean onGameCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		final String[] split = new String[args.length + 1];
		split[0] = label;
		for (int i = 0; i < args.length; i++) {
			split[i + 1] = args[i];
		}
		for (final GameCommand c : gameCommands) {
			if (c.canExecute(sender, split)) {
				if (hasPerms(sender, c)) {
					try {
						c.execute(sender, split);
						return true;
					} catch (final Exception e) {
						EtriaParties.debug(e);
					}
				} else {
					return true;
				}
			}
		}
		sender.sendMessage(ChatColor.RED + "Command not found, here is a list of available commands!");
		Help.help(sender);
		return true;
	}
	
	private boolean hasPerms(final CommandSender sender, final GameCommand command) {
		if (sender instanceof Player) {
			if (sender.hasPermission("etriaparties.*")) {
				return true;
			}
			for (final String permission : command.getPermission()) {
				if (!sender.hasPermission(permission)) {
					sender.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
					return false;
				}
			}
		}
		return true;
	}
}