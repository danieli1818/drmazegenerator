package me.danieli1818.drmazegenerator.algorithm.maze;

import java.util.AbstractMap;

public interface Maze {

	static enum CellStatus {
		BLOCK,
		EMPTY,
		BORDER,
		OUTSIDE
	}
	
	public CellStatus getCell(int i, int j);
	
	public void setCell(int i, int j, CellStatus status);
	
	public boolean[][] toMatrix();
	
	public AbstractMap.SimpleEntry<Integer, Integer> getRandomCellWithStatus(CellStatus status);
	
	public int getRows();
	
	public int getCols();
	
}
