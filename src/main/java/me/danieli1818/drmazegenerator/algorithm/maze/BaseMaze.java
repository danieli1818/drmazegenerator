package me.danieli1818.drmazegenerator.algorithm.maze;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseMaze implements Maze {
	
	private CellStatus[][] maze;
	
	private static Random rnd = new Random();

	public BaseMaze(int rows, int cols) {
		this.maze = new CellStatus[rows][cols];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze.length; j++) {
				if (i == 0 || j == 0 || i == rows - 1 || j == cols - 1) {
					this.maze[i][j] = CellStatus.BORDER;
				} else {
					this.maze[i][j] = CellStatus.BLOCK;
				}
			}
		}
	}
	
	public BaseMaze(boolean[][] maze) {
		int rows = maze.length;
		int cols = maze[0].length;
		this.maze = new CellStatus[rows][cols];
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if ((i == 0 || j == 0 || i == rows - 1 || j == cols - 1) && !maze[i][j]) {
					this.maze[i][j] = CellStatus.BORDER;
				} else if (!maze[i][j] && (maze[i - 1][j] || maze[i + 1][j] || maze[i][j - 1] || maze[i][j + 1])) {
					this.maze[i][j] = CellStatus.BORDER;
				} else if (maze[i][j]) {
					this.maze[i][j] = CellStatus.EMPTY;
				} else {
					this.maze[i][j] = CellStatus.BLOCK;
				}
			}
		}
	}
	
	@Override
	public CellStatus getCell(int i, int j) {
		if (0 <= i && i < maze.length && 0 <= j && j < maze[0].length) {
			return this.maze[i][j];
		}
		return CellStatus.OUTSIDE;
	}

	@Override
	public void setCell(int i, int j, CellStatus status) {
		if (0 <= i && i < maze.length && 0 <= j && j < maze[0].length) {
			this.maze[i][j] = status;
		}
	}

	@Override
	public boolean[][] toMatrix() {
		boolean[][] matrixMaze = new boolean[this.maze.length][this.maze[0].length];
		for (int i = 0; i < matrixMaze.length; i++) {
			for (int j = 0; j < matrixMaze[i].length; j++) {
				if (this.maze[i][j] == CellStatus.EMPTY) {
					matrixMaze[i][j] = true;
				} else {
					matrixMaze[i][j] = false;
				}
			}
		}
		return matrixMaze;
	}

	public AbstractMap.SimpleEntry<Integer, Integer> getRandomCellWithStatus(CellStatus status) {
		List<AbstractMap.SimpleEntry<Integer, Integer>> cells = new ArrayList<>();
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				if (maze[i][j] == status) {
					cells.add(new AbstractMap.SimpleEntry<Integer, Integer>(i, j));
				}
			}
		}
		return cells.get(rnd.nextInt(cells.size()));
	}

	@Override
	public int getRows() {
		return this.maze.length;
	}

	@Override
	public int getCols() {
		return this.maze[0].length;
	}
	
}
