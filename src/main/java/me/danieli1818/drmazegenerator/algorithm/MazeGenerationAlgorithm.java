package me.danieli1818.drmazegenerator.algorithm;

public interface MazeGenerationAlgorithm {

	public boolean[][] generateMaze();
	
	public MazeGenerationAlgorithm resize(int multiply);
	
}
