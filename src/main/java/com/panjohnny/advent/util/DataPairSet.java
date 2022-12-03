package com.panjohnny.advent.util;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * Simple utility to store more values with one key
 * @author PanJohnny
 */
public class DataPairSet<K, V> {
    private final HashMap<Integer, K> keys;
    private final HashMap<Integer, V> values;

    private int index = 0;

    public DataPairSet() {
        this.keys = new HashMap<>();
        this.values = new HashMap<>();
    }

    public void put(K key, V value) {
        this.keys.put(index, key);
        this.values.put(index, value);
        index++;
    }

    public void forEach(BiConsumer<K, V> consumer) {
        for (Map.Entry<Integer, K> entry : keys.entrySet()) {
            V value = values.get(entry.getKey());
            consumer.accept(entry.getValue(), value);
        }
    }

    public int size() {
        return index;
    }
}