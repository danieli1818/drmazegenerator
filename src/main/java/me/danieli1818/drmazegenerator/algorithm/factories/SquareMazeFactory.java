package me.danieli1818.drmazegenerator.algorithm.factories;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import me.danieli1818.drmazegenerator.algorithm.MazeGenerationAlgorithm;
import me.danieli1818.drmazegenerator.algorithm.RandomizedDepthFirstSearch;

public class SquareMazeFactory implements MazeFactory {

	@Override
	public MazeGenerationAlgorithm create(Region region) {
		Vector minimumPoint = region.getMinimumPoint();
		Vector maximumPoint = region.getMaximumPoint();
		
		int rowsCount = maximumPoint.getBlockX() - minimumPoint.getBlockX() + 1;
		int colsCount = maximumPoint.getBlockZ() - minimumPoint.getBlockZ() + 1;
		
		return new RandomizedDepthFirstSearch(rowsCount, colsCount);
	}

	
	
}
