package me.danieli1818.drmazegenerator.algorithm;

public class CircularMazeGenerator implements MazeGenerationAlgorithm {
	
	private int diameter;
	
	public CircularMazeGenerator(int diameter) {
		this.diameter = diameter;
	}

	@Override
	public boolean[][] generateMaze() {
		boolean[][] maze = new boolean[this.diameter][this.diameter];
		
		for (int i = 0; i < maze.length; i++) {
			for (int j = 0; j < maze[i].length; j++) {
				maze[i][j] = true;
			}
		}
		
		System.out.println(mazeToString(maze));
		
		int radius = this.diameter/2;
		for (int i = 0; i < radius; i++) {
			for (int j = 0; j < radius; j++) {
				if (this.diameter - i - j - 2 < radius + radius / 2) {
					maze[i][j] = false;
					maze[i][this.diameter - j - 1] = false;
					maze[this.diameter - i - 1][j] = false;
					maze[this.diameter - i - 1][this.diameter - j - 1] = false;
				} else {
					maze[i][j] = true;
					maze[i][this.diameter - j - 1] = true;
					maze[this.diameter - i - 1][j] = true;
					maze[this.diameter - i - 1][this.diameter - j - 1] = true;
				}
				System.out.println(mazeToString(maze));
			}
		}
		for (int i = 0; i < maze.length; i++) {
			maze[i][radius] = false;
			maze[radius][i] = false;
			System.out.println(mazeToString(maze));
		}
		return new RandomizedDepthFirstSearch(this.diameter, this.diameter).generateMaze(maze);
	}

	@Override
	public MazeGenerationAlgorithm resize(int multiply) {
		if (multiply > 0) {
			return new CircularMazeGenerator(this.diameter * multiply);
		} else if (multiply < 0) {
			return new CircularMazeGenerator(this.diameter / -multiply);
		} else {
			return new CircularMazeGenerator(this.diameter);
		}
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

}
