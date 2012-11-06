package com.etriacraft.etriaparties;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.etriacraft.etriaparties.listeners.EtriaPartiesListener;
import com.etriacraft.etriaparties.managers.CommandManager;

public class EtriaParties extends JavaPlugin {

	private CommandManager command = null;
	
	@Override
	public void onEnable() {
		EtriaParties.log("Starting plugin.");
		command = new CommandManager();
		getServer().getPluginManager().registerEvents(new EtriaPartiesListener(this), this);
	}
	
	@Override
	public void onDisable() {
		EtriaParties.log("Successfully disabled plugin");
	}
	
	// Command Handling
	
	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		return getCommandManager().onGameCommand(sender, command, label, args);
	}
	
	// Plugin Settings
	protected CommandManager getCommandManager() {
		return command;
	}
	
	// Logs messages to console
	public static void debug(final Exception error) {
		Bukkit.getLogger().log(Level.SEVERE, "Critical error detected!", error);
	}
	
	public static void log(final String msg) {
		Bukkit.getLogger().info(msg);
	}
}
