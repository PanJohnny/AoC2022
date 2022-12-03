package com.panjohnny.advent.days;

import com.panjohnny.advent.util.Day;
import com.panjohnny.advent.util.InputLoader;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * <h1>Rucksack troubles</h1>
 * <a href="https://adventofcode.com/2022/day/3">Day 3</a>
 * Example output:
 * <pre>
 * --- [ DAY 3 ] ---
 * = Part1
 *
 * Total score: 7826
 * = Part2
 *
 * Total score: 2577
 * </pre>
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Day3 extends Day {
    private final String data;
    private String[] backpacks;

    public Day3() {
        data = InputLoader.readTxt(3);
    }

    @Override
    public void prepareData() {
        backpacks = data.split("\n");
    }

    @Override
    public void part1() {
        int score = 0;
        for (String backpack : backpacks) {
            final int mid = backpack.length() / 2;
            Set<Character> part1 = backpack.substring(0, mid).chars()
                    .mapToObj(e->(char)e).collect(Collectors.toSet());
            Set<Character> part2 = backpack.substring(mid).chars()
                    .mapToObj(e->(char)e).collect(Collectors.toSet());

            part1.retainAll(part2);

            if (!part1.isEmpty()) {
                for (Character c : part1) {
                    score += getItemPriority(c);
                }
            }
        }

        System.out.printf("Total score: %d%n", score);
    }

    @Override
    public void part2() {
        Set<Character> elf1 = null;
        Set<Character> elf2 = null;
        int score = 0;
        for (String backpack : backpacks) {
            final int mid = backpack.length() / 2;
            Set<Character> mySet = backpack.chars()
                    .mapToObj(e->(char)e).collect(Collectors.toSet());
            if (elf1 == null)
                elf1 = mySet;
            else if (elf2 == null)
                elf2 = mySet;
            else {
                // lets check the stuff here!
                // mySet is the third elf
                elf1.retainAll(elf2);
                elf1.retainAll(mySet);

                // if there is more than one stuff error!
                if (elf1.size() > 1)
                    System.err.printf("There is too much stuff in the backpacks! %s%n", elf1);
                else if (elf1.isEmpty())
                    System.err.println("No common items!");
                else
                    for (char c : elf1)
                        score += getItemPriority(c);
                elf1 = null;
                elf2 = null;
            }
        }
        System.out.printf("Total score: %d%n", score);
    }

    public int getItemPriority(char i) {

        // lowercase 97-122 ASCII range, scores: 1-26
        if (i >= 97 && i <= 122)
            return i - 96;

        // uppercase 65-90 ASCII range, scores: 27-52
        if (i >= 65 && i <= 90)
            return i - 38;

        throw new IllegalArgumentException("Invalid character, out of ranges");
    }
}
