//package treasureHunting;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Dungeon {
    private static Dungeon dungeon;
    private Scanner input = new Scanner(System.in);

    private Dungeon() {}

    public static Dungeon getDungeonInstance() {
        if (dungeon == null) {
            dungeon = new Dungeon();
        }
        return dungeon;
    }

    public void start() {
        System.out.println("Enter the Row size of the Treasure Area:");
        int row = input.nextInt();
        System.out.println("Enter the Column size of the Treasure Area:");
        int column = input.nextInt();
        char[][] treasureArea = constructTreasureArea(row, column);
        initializeTreasureArea(treasureArea);
        startGame(treasureArea);
    }

    private char[][] constructTreasureArea(int row, int column) {
        return new char[row][column];
    }

    private void initializeTreasureArea(char[][] treasureArea) {
        for (int i = 0; i < treasureArea.length; i++) {
            for (int j = 0; j < treasureArea[i].length; j++) {
                treasureArea[i][j] = ' ';
            }
        }
    }

    private void startGame(char[][] treasureArea) {
        int rowOfGold, columnOfGold, rowOfAdventurer, columnOfAdventurer;
        do {
            System.out.println("Enter the Row Position of the Gold in the Treasure Area:");
            rowOfGold = input.nextInt() - 1;  
            System.out.println("Enter the Column Position of the Gold in the Treasure Area:");
            columnOfGold = input.nextInt() - 1;
            if (!isValidPosition(treasureArea, rowOfGold, columnOfGold)) {
                System.out.println("Invalid position for gold. Please try again.");
            }
        } while (!isValidPosition(treasureArea, rowOfGold, columnOfGold));
        initialize(treasureArea, rowOfGold, columnOfGold, 'G');

       
        do {
            System.out.println("Enter the Row Position of the Adventurer in the Treasure Area:");
            rowOfAdventurer = input.nextInt() - 1;  
            System.out.println("Enter the Column Position of the Adventurer in the Treasure Area:");
            columnOfAdventurer = input.nextInt() - 1; 
            if (!isValidPosition(treasureArea, rowOfAdventurer, columnOfAdventurer)) {
                System.out.println("Invalid position for adventurer. Please try again.");
            }
        } while (!isValidPosition(treasureArea, rowOfAdventurer, columnOfAdventurer));
        initialize(treasureArea, rowOfAdventurer, columnOfAdventurer, 'A');

        System.out.println("The Minimal Path to reach the Gold is:");
        int pathLength = findMinimalPath(treasureArea, rowOfAdventurer, columnOfAdventurer);
        System.out.println(pathLength >= 0 ? pathLength : "Gold cannot be reached.");
    }

    private boolean isValidPosition(char[][] treasureArea, int row, int column) {
        return row >= 0 && row < treasureArea.length && column >= 0 && column < treasureArea[0].length;
    }

    private void initialize(char[][] treasureArea, int row, int column, char value) {
        treasureArea[row][column] = value;
    }

    private int findMinimalPath(char[][] treasureArea, int startRow, int startCol) {
        int rows = treasureArea.length;
        int cols = treasureArea[0].length;
        boolean[][] visited = new boolean[rows][cols];
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol, 0});
        visited[startRow][startCol] = true;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int distance = current[2];

            if (treasureArea[row][col] == 'G') {
                return distance;
            }

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newCol = col + direction[1];

                if (isValidPosition(treasureArea, newRow, newCol) && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol, distance + 1});
                }
            }
        }
        return -1;  
    }

    public static void main(String[] args) {
        Dungeon dungeon = Dungeon.getDungeonInstance();
        dungeon.start();
    }
}

