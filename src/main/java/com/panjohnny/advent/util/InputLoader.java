package com.panjohnny.advent.util;

import java.io.InputStream;
import java.util.Scanner;

public final class InputLoader {
    public static String readInput(int day, String fileType) {
        InputStream stream = InputLoader.class.getResourceAsStream("/inputs/%d.%s".formatted(day, fileType));
        StringBuilder stringBuilder = new StringBuilder();
        assert stream != null;
        Scanner scanner = new Scanner(stream);
            while (scanner.hasNextLine())
                stringBuilder.append(scanner.nextLine()).append("\n");
        return stringBuilder.toString();
    }

    public static String readTxt(int day) {
        return readInput(day, "txt");
    }

    @SuppressWarnings("unused")
    public static String readTest() {
        return readInput(0, "test");
    }
}
