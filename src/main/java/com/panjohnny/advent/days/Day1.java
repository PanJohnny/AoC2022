package com.panjohnny.advent.days;

import com.panjohnny.advent.util.Day;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <h1>Calorie counting for elves</h1>
 * <a href="https://adventofcode.com/2022/day/2">Day 1</a>
 * Example output:
 * <pre>
 * --- [ DAY 1 ] ---
 * There are 2250 lines of data
 * = Part1
 *
 * Best elf is 71780
 * = Part2
 *
 * Top 3: [69228, 71481, 71780]
 * 	Total: 212489
 * </pre>
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Day1 extends Day {
    private final ArrayList<Integer> calories;

    public Day1() {
        super(1);
        calories = new ArrayList<>();
    }

    public void prepareData() {
        String[] lines = data.split("\n");
        System.out.printf("There are %d lines of data%n", lines.length);

        int currentElfScore = 0;
        for (String line : lines) {
            try {
                currentElfScore += Integer.parseInt(line);
            } catch (NumberFormatException exception) {
                calories.add(currentElfScore);

                currentElfScore = 0;
            }
        }

        Collections.sort(calories);
    }

    public void part1() {
        System.out.printf("Best elf is %s%n", calories.get(calories.size() - 1));
    }

    public void part2() {
        ArrayList<Integer> top3 = new ArrayList<>(calories.subList(calories.size() -3, calories.size()));
        System.out.printf("Top 3: %s", top3);
        System.out.println();
        int cal = 0;
        for (Integer integer : top3) {
            cal+=integer;
        }
        System.out.printf("\tTotal: %d%n", cal);
    }
}
