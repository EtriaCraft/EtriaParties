package com.etriacraft.etriaparties.command;

import org.bukkit.command.CommandSender;

public abstract class GameCommand {
	
	public abstract boolean canExecute(final CommandSender sender, final String[] split);
	
	public abstract boolean execute(final CommandSender sender, final String[] split);
	
	public abstract String[] getPermission();

}
