package me.danieli1818.drmazegenerator.algorithm.factories;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.Region;

import me.danieli1818.drmazegenerator.algorithm.CircularMazeGenerator;
import me.danieli1818.drmazegenerator.algorithm.MazeGenerationAlgorithm;

public class CircularMazeFactory implements MazeFactory {

	@Override
	public MazeGenerationAlgorithm create(Region region) {
		Vector minimumPoint = region.getMinimumPoint();
		Vector maximumPoint = region.getMaximumPoint();
		
		int rowsCount = maximumPoint.getBlockX() - minimumPoint.getBlockX() + 1;
		int colsCount = maximumPoint.getBlockZ() - minimumPoint.getBlockZ() + 1;
		
		int diameter = Math.min(rowsCount, colsCount);
		
		return new CircularMazeGenerator(diameter);
	}

}
