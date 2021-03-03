package me.danieli1818.drmazegenerator.algorithm;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

import me.danieli1818.drmazegenerator.algorithm.maze.BaseMaze;
import me.danieli1818.drmazegenerator.algorithm.maze.Maze;
import me.danieli1818.drmazegenerator.algorithm.maze.Maze.CellStatus;

public class RandomizedDepthFirstSearch implements MazeGenerationAlgorithm {
	
	private static final Random rnd = new Random();
	
	private int rows;
	private int cols;

	public RandomizedDepthFirstSearch(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
	}

//	public boolean[][] generateMaze(int rows, int cols, int width) {
//		
//		int diffRows = width - rows % width;
//		
//		int diffCols = width - cols % width;
//		
//		rows = rows + diffRows;
//		
//		cols = cols + diffCols;
//		
//		return generateMaze(rows, cols);
//	}
	
	public boolean[][] generateMaze(boolean[][] maze) {
		
		// Checking Validation.
//		if (!(startingRow != 0 && startingRow != rows - 1) && !(startingColumn != 0 && startingColumn != cols - 1)) {
//			System.out.println("Validation Error!");
//			return null;
//		}
		
		if (maze.length == 0) {
			return null;
		}
		
		Maze baseMaze = new BaseMaze(maze);
		
//		int rows = maze.length;
//		int cols = maze[0].length;
		
		AbstractMap.SimpleEntry<Integer, Integer> randomCell = baseMaze.getRandomCellWithStatus(CellStatus.BLOCK);
		int startingRow = randomCell.getKey();
		int startingCol = randomCell.getValue();
		
		AbstractMap.SimpleEntry<Integer, Integer> currentCell = new AbstractMap.SimpleEntry<Integer, Integer>(startingRow, startingCol);
		
		Stack<AbstractMap.SimpleEntry<Integer, Integer>> algorithmStack = new Stack<AbstractMap.SimpleEntry<Integer,Integer>>();
		
		algorithmStack.push(currentCell);
		
		baseMaze.setCell(currentCell.getKey(), currentCell.getValue(), Maze.CellStatus.EMPTY);
		
		while (!algorithmStack.empty()) {
			currentCell = algorithmStack.peek();
			List<AbstractMap.SimpleEntry<Integer, Integer>> neighbors = getUnvisitedNeighbors(baseMaze, currentCell.getKey(), currentCell.getValue());
			
			if (!neighbors.isEmpty()) {
				AbstractMap.SimpleEntry<Integer, Integer> selectedNeighbor = getRandomListItem(neighbors);
				baseMaze.setCell(selectedNeighbor.getKey(), selectedNeighbor.getValue(), Maze.CellStatus.EMPTY);
				baseMaze.setCell((selectedNeighbor.getKey() + currentCell.getKey()) / 2
						, (selectedNeighbor.getValue() + currentCell.getValue()) / 2, Maze.CellStatus.EMPTY);
				algorithmStack.push(selectedNeighbor);
			} else {
				algorithmStack.pop();
			}
		}
		
		boolean[][] matrix = baseMaze.toMatrix();
		
		return matrix;
	}
	
	private static List<AbstractMap.SimpleEntry<Integer, Integer>> getUnvisitedNeighbors(Maze maze, int row, int col) {
		List<AbstractMap.SimpleEntry<Integer, Integer>> neighbors = new ArrayList<AbstractMap.SimpleEntry<Integer,Integer>>();
		
		if (row - 2 > 0 && maze.getCell(row - 2, col) == Maze.CellStatus.BLOCK && maze.getCell(row - 1, col) == Maze.CellStatus.BLOCK) {
			neighbors.add(new AbstractMap.SimpleEntry<Integer, Integer>(row - 2, col));
		}
		if (row + 2 < maze.getRows() - 1 && maze.getCell(row + 2, col) == Maze.CellStatus.BLOCK && maze.getCell(row + 1, col) == Maze.CellStatus.BLOCK) {
			neighbors.add(new AbstractMap.SimpleEntry<Integer, Integer>(row + 2, col));
		}
		if (col - 2 > 0 && maze.getCell(row, col - 2) == Maze.CellStatus.BLOCK && maze.getCell(row, col - 1) == Maze.CellStatus.BLOCK) {
			neighbors.add(new AbstractMap.SimpleEntry<Integer, Integer>(row, col - 2));
		}
		if (col + 2 < maze.getCols() - 1 && maze.getCell(row, col + 2) == Maze.CellStatus.BLOCK && maze.getCell(row, col + 1) == Maze.CellStatus.BLOCK) {
			neighbors.add(new AbstractMap.SimpleEntry<Integer, Integer>(row, col + 2));
		}
		
		
		return neighbors;
	}
	
	private static <T> T getRandomListItem(List<T> list) {
		if (list == null) {
			return null;
		}
		int randomIndex = rnd.nextInt(list.size());
		return list.get(randomIndex);
	}

	private boolean[][] generateMaze(int rows, int cols) {
		boolean[][] maze = new boolean[rows][cols];
		
		// Initialize Maze.
		for (int row = 0; row < maze.length; row++) {
			for (int col = 0; col < maze[row].length; col++) {
				maze[row][col] = false;
			}
		}
		
		return generateMaze(maze);
	}

	@Override
	public boolean[][] generateMaze() {
		return generateMaze(this.rows, this.cols);
	}

	@Override
	public MazeGenerationAlgorithm resize(int multiply) {
		if (multiply > 0) {
			return new RandomizedDepthFirstSearch(this.rows * multiply, this.cols * multiply);
		} else if (multiply < 0) {
			return new RandomizedDepthFirstSearch(this.rows / -multiply, this.cols / -multiply);
		} else {
			return new RandomizedDepthFirstSearch(this.rows, this.cols);
		}
	}
	
	@SuppressWarnings("unused")
	private String mazeToString(boolean[][] maze) {
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
