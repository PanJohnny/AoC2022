package com.panjohnny.advent.days;

import com.panjohnny.advent.util.Day;

import java.util.LinkedList;
import java.util.PrimitiveIterator;

@SuppressWarnings("unused")
public class Day6 extends Day {

    public Day6() {
        super(6);
    }

    @Override
    public void prepareData() {

    }

    @Override
    public void part1() {
        System.out.printf("Beginning of the packet index: %d%n", processUntilNDifferent(4));
    }

    @Override
    public void part2() {
        System.out.printf("Beginning of the message index: %d%n", processUntilNDifferent(14));
    }

    private int processUntilNDifferent(int nDifferent) {
        PrimitiveIterator.OfInt stream = data.chars().iterator();
        LinkedList<Integer> last4 = new LinkedList<>();
        int fIndex = -1;
        for (int i = 1; stream.hasNext(); i++) {
            Integer a = stream.next();
            if (!last4.contains(a)) {
                last4.add(a);
                if (last4.size() == nDifferent) {
                    fIndex = i;
                    break;
                }
            } else {
                // move to the appearance of a
                while (last4.contains(a)) {
                    last4.removeFirst();
                }

                last4.add(a);
            }
        }
        return fIndex;
    }
}
