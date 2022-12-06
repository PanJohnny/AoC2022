package com.panjohnny.advent.days;

import com.panjohnny.advent.util.DataPairSet;
import com.panjohnny.advent.util.Day;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * <h1>Playing rock-paper-scissors tournament</h1>
 * <a href="https://adventofcode.com/2022/day/2">Day 2</a>
 * Example output:
 * <pre>
 * --- [ DAY 2 ] ---
 *
 * There are: 2500 moves
 * = Part1
 *
 * Final score: 15691
 * = Part1
 *
 * Final score: 12989
 * </pre>
 * @author PanJohnny
 */
@SuppressWarnings("unused")
public class Day2 extends Day {
    private final DataPairSet<Option, Option> options;
    private final int WIN = 6;
    private final int DRAW = 3;

    public Day2() {
        super(2);
        options = new DataPairSet<>();
    }

    @Override
    public void prepareData() {
        for (String s : data.split("\n")) {
            String[] arr = s.split(" ");
            Option other = Option.fromChar(arr[0].charAt(0));
            Option me = Option.fromChar(arr[1].charAt(0));
            if (other == Option.ERROR || me == Option.ERROR)  {
                System.err.println("Error! " + arr[0] + " " + arr[1] + "");
                throw new RuntimeException("Invalid data!");
            }
            options.put(other, me);
        }
        System.out.printf("\nThere are: %d moves%n", options.size());
    }

    int myScore = 0;
    @Override
    public void part1() {
        BiConsumer<Option, Option> consumer = (other, my) -> {
            if (my.defeats(other)) {
                myScore += WIN;
            } else if (my.isDraw(other)) {
                myScore += DRAW;
            }
            myScore += my.score;
        };
        options.forEach(consumer);
        System.out.printf("Final score: %d%n", myScore);
    }

    @Override
    public void part2() {
        // from now one the "my option" becomes outcome of the game
        myScore = 0;
        BiConsumer<Option, Option> consumer = (other, my) -> {
            switch (my.getOutcome()) {
                case WIN -> {
                    Option use = Option.getOptionThatDefeats(other);
                    myScore += 6;
                    myScore += use.score;
                }
                case LOSE -> {
                    Option use = other.getDefeats();
                    myScore += use.score;
                }
                case DRAW -> {
                    myScore += DRAW;
                    myScore += other.score;
                }
                case NONE -> {
                    System.err.println("Error: Unknown outcome");
                    throw new RuntimeException("Unknown outcome");
                }
            }
        };

        options.forEach(consumer);
        System.out.printf("Final score: %d%n", myScore);
    }

    private enum Option {
        // scissors score (no forward references allowed doh)
        ROCK(1, 3, 'A', 'X'), // LOSE
        PAPER(2, ROCK.score, 'B', 'Y'), // DRAW
        SCISSORS(3, PAPER.score, 'C', 'Z'), // WIN
        ERROR(-1, -1);

        final char[] keys;
        final int score;
        final int defeats;
        Option(int score, int defeats, char... keys) {
            this.keys = keys;
            this.score = score;
            this.defeats = defeats;
        }

        public boolean defeats(Option option) {
            return defeats == option.score;
        }

        public Option getDefeats() {
           return Arrays.stream(Option.values()).filter(this::defeats).findFirst().orElse(ERROR);
        }

        public boolean isDraw(Option option) {
            return this.equals(option);
        }

        public static Option fromChar(char a) {
            return Arrays.stream(Option.values()).filter(option -> option.keys[0] == a || option.keys[1] == a).findFirst().orElse(ERROR);
        }

        @Override
        public String toString() {
            return name();
        }

        public GameOutcome getOutcome() {
            return switch (this) {
                case ROCK -> GameOutcome.LOSE;
                case PAPER -> GameOutcome.DRAW;
                case SCISSORS -> GameOutcome.WIN;
                default -> GameOutcome.NONE;
            };
        }

        public static Option getOptionThatDefeats(Option option) {
            return switch (option) {
                case ROCK -> Option.PAPER;
                case PAPER -> Option.SCISSORS;
                case SCISSORS -> Option.ROCK;
                default -> Option.ERROR;
            };
        }

        private enum GameOutcome {
            WIN,
            LOSE,
            DRAW,
            NONE
        }
    }
}
