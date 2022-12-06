package com.panjohnny.advent.util;

public abstract class Day {
    protected final String data;
    public Day(int day) {
        data = InputLoader.readTxt(day);
    }

    public void run() {
        long timeStart = System.currentTimeMillis();
        prepareData();
        System.out.println("= Part1\n");
        part1();
        System.out.println("= Part2\n");
        part2();
        long timeEnd = System.currentTimeMillis();
        long elapsed = timeEnd - timeStart;
        System.out.printf("%n%n\t\t->Finished in %dms%n", elapsed);
    }
    public abstract void prepareData();
    public abstract void part1();
    public abstract void part2();
}
