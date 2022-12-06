package com.panjohnny.advent.days;

import com.panjohnny.advent.util.DataPairSet;
import com.panjohnny.advent.util.Day;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("unused")
public class Day4 extends Day {
    private final DataPairSet<IntRange, IntRange> pairs;
    public Day4() {
        super(4);
        this.pairs = new DataPairSet<>();
    }

    @Override
    public void prepareData() {
        String[] lines = data.split("\n");
        for (String line : lines) {
            String[] ranges = line.split(",");
            pairs.put(IntRange.fromString(ranges[0]), IntRange.fromString(ranges[1]));
        }
    }

    int contains = 0;
    int overlaps = 0;
    @Override
    public void part1() {
        pairs.forEach((r1, r2) -> {
            if (r1.contains(r2) || r2.contains(r1))
                contains++;
        });
        System.out.printf("Ranges that contains each other: %d%n", contains);
    }

    @Override
    public void part2() {
        pairs.forEach((r1, r2) -> {
            if (r1.overlaps(r2) || r2.overlaps(r1))
                overlaps++;
        });
        System.out.printf("Ranges that overlap each other: %d%n", overlaps);
    }

    private record IntRange(int from, int to) {
        public boolean contains(IntRange range) {
            return range.from <= from && range.to >= to;
        }

        public boolean overlaps(IntRange range) {
            Set<Integer> thisSet = toSet();
            Set<Integer> otherSet = range.toSet();

            otherSet.retainAll(thisSet);

            return otherSet.size() > 0;
        }

        public Set<Integer> toSet() {
            Set<Integer> set = new HashSet<>();
            for (int i = from; i <= to; i++) {
                set.add(i);
            }
            return set;
        }

        public static IntRange fromString(String s) {
            String[] arr = s.split("-");
            int i1 = Integer.parseInt(arr[0]);
            int i2 = Integer.parseInt(arr[1]);
            return new IntRange(i1, i2);
        }
    }
}
