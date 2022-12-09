package com.panjohnny.advent.days;

import com.panjohnny.advent.util.Day;
import com.panjohnny.advent.util.Direction;
import com.panjohnny.advent.util.NumGrid;

import java.awt.*;

@SuppressWarnings("unused")
public class Day8 extends Day {

    private NumGrid grid;


    public Day8() {
        super(8);
    }

    @Override
    public void prepareData() {
        String[] lines = data.split("\n");
        int[][] heights = new int[lines[0].length()][lines.length];
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String row = line.trim();
            char[] cols = row.toCharArray();
            for (int j = 0; j < cols.length; j++) {
                char col = cols[j];
                int g = Integer.parseInt(String.valueOf(col));

                heights[j][i] = g;
            }
        }

        grid = new NumGrid(heights);
    }

    @Override
    public void part1() {
        System.out.printf("Edge finds: %d%n", grid.stream().filter(p -> reachesEdge(grid, p)).count());
    }

    private boolean reachesEdge(NumGrid grid, Point p) {
        Direction[] dirs = Direction.values();
        int tree = grid.get(p);
        for (Direction d : dirs) {
            Point np = p;
            while (true) {
                // move the point in direction
                np = d.move(np);

                // our current height
                int currentHeight = grid.get(np);

                // there is something of the same height or higher this is the end
                if (currentHeight >= tree) break;

                // we are out so that means we won
                if (!grid.contains(np))
                    return true;
            }
        }
        return false;
    }


    @Override
    public void part2() {
        System.out.printf("Best scenic score: %d%n", grid.stream().mapToInt(p -> scenicScore(grid, p)).max().orElse(-1));
    }

    private int scenicScore(NumGrid grid, Point p) {
        Direction[] dirs = Direction.values();
        int tree = grid.get(p);
        int score = 1;
        for (Direction d : dirs) {
            Point np = p;
            int sc = 0;
            while (true) {
                // move the point in direction
                np = d.move(np);

                // our current height
                int currentHeight = grid.get(np);

                // there is something of the same height or higher this is the end
                if (currentHeight >= tree) {
                    sc++;
                    break;
                } else if (currentHeight == -1) // out
                    break;

                sc++;
            }
            score *= sc;
        }

        return score;
    }
}
