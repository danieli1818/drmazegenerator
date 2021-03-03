package me.danieli1818.drmazegenerator;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;

import me.danieli1818.drmazegenerator.commands.DRMazeGeneratorCommands;
import me.danieli1818.drmazegenerator.utils.MessagesSender;

public class DRMazeGenerator extends JavaPlugin {

	@Override
	public void onEnable() {
		super.onEnable();
		
		if (getWorldEditPlugin() == null) {
			System.err.println("Error!!!! WorldEdit Plugin Is Missing!!!!");
			return;
		}
		
		MessagesSender.getInstance(getName());
		
		getCommand("drmazegenerator").setExecutor(new DRMazeGeneratorCommands());
		
	}
	
	private WorldEditPlugin getWorldEditPlugin() {
		return (WorldEditPlugin)Bukkit.getPluginManager().getPlugin("WorldEdit");
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
	}
	
}
