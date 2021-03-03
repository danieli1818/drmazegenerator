package me.danieli1818.drmazegenerator.algorithm;

public class WidthsMazeGenerator implements MazeGenerationAlgorithm {
	
	private MazeGenerationAlgorithm algorithm;
	private int width;

	public WidthsMazeGenerator(MazeGenerationAlgorithm algorithm, int width) {
		this.algorithm = algorithm;
		this.width = width;
	}
	
	@Override
	public boolean[][] generateMaze() {
		boolean[][] maze = algorithm.resize(-width).generateMaze();
		System.out.println(mazeToString(maze));
		int rows = maze.length * width;
		int cols = maze[0].length * width;
		boolean[][] biggerMaze = new boolean[rows][cols];
		System.out.println(mazeToString(biggerMaze));
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				for (int k = 0; k < width; k++) {
					for (int l = 0; l < width; l++) {
						biggerMaze[width * i + k][width * j + l] = maze[i][j];
						System.out.println(mazeToString(biggerMaze));
					}
				}
			}
		}
		return biggerMaze;
	}
	
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

	@Override
	public MazeGenerationAlgorithm resize(int multiply) {
		if (multiply > 0) {
			return new WidthsMazeGenerator(this.algorithm, this.width * multiply);
		} else if (multiply < 0) {
			return new WidthsMazeGenerator(this.algorithm, this.width / -multiply);
		} else {
			return new WidthsMazeGenerator(this.algorithm, this.width);
		}
		
	}

}
