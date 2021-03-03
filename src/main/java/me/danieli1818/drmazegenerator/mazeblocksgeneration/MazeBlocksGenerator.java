package me.danieli1818.drmazegenerator.mazeblocksgeneration;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import me.danieli1818.drmazegenerator.DRMazeGenerator;
import me.danieli1818.drmazegenerator.algorithm.MazeGenerationAlgorithm;
import me.danieli1818.drmazegenerator.algorithm.WidthsMazeGenerator;

public class MazeBlocksGenerator {

	public static void generateMaze(Material blockMaterial, byte subID, Region region, int width, MazeGenerationAlgorithm algorithm) {
		
		Vector minimumPoint = region.getMinimumPoint();
		Vector maximumPoint = region.getMaximumPoint();
		
		int height = maximumPoint.getBlockY() - minimumPoint.getBlockY() + 1;
		
//		boolean[][] maze = new WidthsMazeGenerator(new CircularMazeGenerator(Math.min(rowsCount, colsCount)), width).generateMaze();
		
		boolean[][] maze = new WidthsMazeGenerator(algorithm, width).generateMaze();
		
//		System.out.println(mazeToString(maze));
		
		generateBlocks(maze, new Location(Bukkit.getWorld(region.getWorld().getName()), minimumPoint.getBlockX(), minimumPoint.getBlockY(), minimumPoint.getBlockZ()), height, blockMaterial, subID);
		
	}
	
//	private static AbstractMap.SimpleEntry<Integer, Integer> getStartDelta(Vector minimumPoint, int rowsCount, int colsCount, Location startLocation) {
//		int rowDelta = startLocation.getBlockX() - minimumPoint.getBlockX();
//		int colDelta = startLocation.getBlockZ() - minimumPoint.getBlockZ();
//		
//		if ((rowDelta != 0 && rowDelta != rowsCount - 1) || (colDelta != 0 && colDelta != colsCount - 1)) {
//			return null;
//		}
//		
//		return new AbstractMap.SimpleEntry<Integer, Integer>(rowDelta, colDelta);
//	}
	
	@SuppressWarnings("deprecation")
	private static void generateBlocks(final boolean[][] maze, Location minimumLocation, int height, Material blockMaterial, byte subID) {
		for (int i = 0; i < maze.length; i++) {
			final int iCopy = i;
			Bukkit.getScheduler().scheduleSyncDelayedTask(DRMazeGenerator.getPlugin(DRMazeGenerator.class), () -> {
				for (int j = 0; j < maze[iCopy].length; j++) {
					if (!maze[iCopy][j]) {
						for (int y = 0; y < height; y++) {
							Location currentLocation = getLocationAfterDelta(minimumLocation, iCopy, y, j);
							currentLocation.getBlock().setType(blockMaterial);
							currentLocation.getBlock().setData(subID);
						}
					}
				}
			});

		}
	}
	
	private static Location getLocationAfterDelta(Location origin, int deltaX, int deltaY, int deltaZ) {
		return new Location(origin.getWorld(), origin.getX() + deltaX, origin.getY() + deltaY, origin.getZ() + deltaZ);
	}
	
	@SuppressWarnings("unused")
	private static String mazeToString(boolean[][] maze) {
		String mazeString = "\n";
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (maze[i][j]) {
					mazeString += " ";
				} else {
					mazeString += "X";
				}
			}
			mazeString += "\n";
		}
		return mazeString;
	}
}
