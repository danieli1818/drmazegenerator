package me.danieli1818.drmazegenerator.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.regions.Region;

import me.danieli1818.drmazegenerator.algorithm.factories.CircularMazeFactory;
import me.danieli1818.drmazegenerator.algorithm.factories.MazeFactory;
import me.danieli1818.drmazegenerator.algorithm.factories.SquareMazeFactory;
import me.danieli1818.drmazegenerator.mazeblocksgeneration.MazeBlocksGenerator;
import me.danieli1818.drmazegenerator.utils.MessagesSender;

public class DRMazeGeneratorCommands implements CommandExecutor {
	
	private static WorldEditPlugin wep = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
	
	private final static Map<String, MazeFactory> mazesFactories = new HashMap<>();
    static {
    	mazesFactories.put("SQUARED", new SquareMazeFactory());
    	mazesFactories.put("CIRCULAR", new CircularMazeFactory());
    }

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!sender.hasPermission("drmazegenerator.commands")) {
			MessagesSender.getInstance().sendMessage("You Don't Have Permissions To Run This Command!", sender);
			return false;
		}
		if (args.length < 1) {
			onHelpCommand(sender);
			return false;
		}
		
		String subCommand = args[0];
		if (subCommand.equals("generate")) {
			String[] arguments = new String[args.length - 1];
			for (int i = 0; i < args.length - 1; i++) {
				arguments[i] = args[i + 1];
			}
			onGenerateCommand(sender, arguments);
		} else if (subCommand.equals("help")) {
			onHelpCommand(sender);
		} else {
			MessagesSender.getInstance().sendMessage("Error! Unknown Command! Type /drmazegenerator help for help!", sender);
			return false;
		}
		
		
		return true;
	}
	
	private void onHelpCommand(CommandSender sender) {
		
	}
	
	@SuppressWarnings("deprecation")
	private boolean onGenerateCommand(CommandSender sender, String[] args) {
		if (!(sender instanceof Player)) {
			MessagesSender.getInstance().sendMessage("Error! You have to be a player to run this command!", sender);
		}
		Player player = (Player)sender;
		Material material = null;
		byte subID = 0;
		int width = 1;
		MazeFactory mazeFactory = DRMazeGeneratorCommands.mazesFactories.get("SQUARED");
		if (args.length >= 1) {
			try {
				width = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				MessagesSender.getInstance().sendMessage("Error! Invalid Command! Type /drmazegenerator {Width}!", sender);
				return false;
			}
		}
		if (args.length >= 2) {
			String type = args[1].toUpperCase();
			mazeFactory = DRMazeGeneratorCommands.mazesFactories.get(type);
			if (mazeFactory == null) {
				MessagesSender.getInstance().sendMessage("Error! Invalid Command! Type /drmazegenerator {Width} {Type}!", sender);
				return false;
			}
		}
		if (args.length == 3) {
			String blockName = args[2];
			material = Material.getMaterial(blockName);
			if (material == null) {
				MessagesSender.getInstance().sendMessage("Error! Invalid Command! Type /drmazegenerator {Width} {Type} {Block Name}!", sender);
				return false;
			}
		}
		if (args.length >= 3) {
			MessagesSender.getInstance().sendMessage("Error! Invalid Command! Type /drmazegenerator {Width} {Block Name}!", sender);
			return false;
		}
		if (material == null) {
			ItemStack heldItem = player.getInventory().getItemInMainHand();
			if (heldItem == null) {
				MessagesSender.getInstance().sendMessage("Error! You don't hold any block in your inventory!", sender);
				return false;
			}
			material = heldItem.getType();
			subID = heldItem.getData().getData();
			System.out.println(subID);
			if (!material.isBlock()) {
				MessagesSender.getInstance().sendMessage("Error! You don't hold any block in your inventory!", sender);
				return false;
			}
		}
		
		LocalSession session = wep.getSession(player);
		Region region;
		try {
			region = (Region) session.getSelection(session.getSelectionWorld());
		} catch (IncompleteRegionException e) {
			MessagesSender.getInstance().sendMessage("Error! You don't have a valid worldedit selection!", sender);
			return false;
		}
		if (region == null) {
			MessagesSender.getInstance().sendMessage("Error! You don't have a worldedit selection!", sender);
			return false;
		}
		
		MazeBlocksGenerator.generateMaze(material, subID, region, width, mazeFactory.create(region));
		
		return true;
	}

}
