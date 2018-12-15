package com.mikedammers.parallelsorter.util;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ArrayUtils {
    public static int[] generateRandomArray(int size) {
        int[] values = new int[size];

        for (int i = 0; i < size; i++) {
            values[i] = i;
        }

        return shuffle(values);
    }

    public static int[] generateFixedArrayTen() {
        return new int[]{5, 2, 1, 6, 8, 7, 10, 9, 4, 3};
    }

    public static int[] generateFixedArrayHundred() {
        return new int[]{10, 65, 78, 74, 72, 95, 99, 91, 45, 84, 20, 17, 24, 6, 3, 38, 97, 98, 89, 60, 61, 29, 66, 58, 50, 15, 62, 8, 43, 67, 100, 71, 83, 90, 28, 80, 93, 64, 77, 63, 54, 52, 70, 13, 34, 59, 35, 46, 57, 96, 14, 86, 25, 23, 31, 33, 76, 1, 85, 48, 82, 81, 2, 19, 18, 39, 69, 73, 30, 12, 87, 92, 40, 68, 36, 47, 42, 49, 75, 51, 21, 37, 44, 27, 55, 26, 11, 9, 41, 22, 32, 53, 94, 88, 4, 56, 16, 7, 79, 5};
    }

    public static int[] generateFixedArrayInOrder(int size) {
        int[] values = new int[size];

        for (int i = 0; i < size; i++) {
            values[i] = i;
        }

        return values;
    }

    public static boolean checkIntegrity(int[] input, int[] output) {
        // handige functies van Java8
        Arrays.sort(input);
        Arrays.sort(output);

        if (Arrays.equals(input, output)) {
            return true;
        }

        return false;
    }

    public static int[] shuffle(int[] values) {
        Random rnd = ThreadLocalRandom.current();

        for (int i = values.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = values[index];

            values[index] = values[i];
            values[i] = a;
        }

        return values;
    }

    public static String arrayToString(int[] values) {
        String arrayString = "";

        for (int i = 0; i < values.length; i++) {
            arrayString += values[i];
            if (i != values.length - 1) {
                arrayString += ", ";
            }
        }

        return arrayString;
    }

    public static void printArray(int[] values) {
        System.out.println(arrayToString(values));
    }

    public static boolean isSorted(int[] values) {
        for (int i = 1; i < values.length - 1; i++) {
            if (values[i] < values[i - 1]) return false;
        }

        return true;
    }
}
