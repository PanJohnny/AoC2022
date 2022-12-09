package com.panjohnny.advent.util;

import java.io.Serializable;

@SuppressWarnings("unused")
public record Pair<A, B>(A a, B b) implements Serializable {
    @Override
    public String toString() {
        return a.toString() + ", " + b.toString();
    }
}
