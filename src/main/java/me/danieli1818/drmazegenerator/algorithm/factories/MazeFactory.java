package me.danieli1818.drmazegenerator.algorithm.factories;

import com.sk89q.worldedit.regions.Region;

import me.danieli1818.drmazegenerator.algorithm.MazeGenerationAlgorithm;

public interface MazeFactory {

	public MazeGenerationAlgorithm create(Region region);
	
}
