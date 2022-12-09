package com.panjohnny.advent.util;

import java.awt.Point;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class NumGrid {
    private final int[][] grid;
    public NumGrid(int[][] grid) {
        this.grid = grid;
    }

    public int get(int x, int y) {
        try {
            return grid[x][y];
        } catch (ArrayIndexOutOfBoundsException exception) {
            return -1;
        }
    }

    public int get(Point p) {
        return get(p.x, p.y);
    }

    public boolean contains(Point p) {
        return p.x > 0 && p.y > 0 && p.x < getWidth() && p.y < getHeight();
    }

    public int getWidth() {
        return grid.length;
    }

    public int getHeight() {
        return grid[0].length;
    }

    public Stream<Point> stream() {
        return IntStream.range(0, grid.length).boxed().flatMap(x -> IntStream.range(0, grid[x].length).mapToObj(y -> new Point(x, y)));
    }
}
