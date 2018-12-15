package com.mikedammers.parallelsorter.tests;

import com.mikedammers.parallelsorter.sorting.SortExecutor;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;
import com.mikedammers.parallelsorter.util.ArrayUtils;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTests implements SortAlgorithmListener {
    private int[] input;

    @Test
    public void testMergeSortSingleThread() {
        CountDownLatch latch = new CountDownLatch(2);

        int[] values = ArrayUtils.generateFixedArrayTen();

        SortExecutor sortExecutor = new SortExecutor(this);
        sortExecutor.setSelectedAlgorithmType(SortAlgorithmType.MERGE_SORT);
        sortExecutor.setSleepTime(0);
        sortExecutor.sort(values);

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMergeSortMultiThread() {
        CountDownLatch latch = new CountDownLatch(2);

        int[] values = ArrayUtils.generateFixedArrayTen();

        SortExecutor sortExecutor = new SortExecutor(this);
        sortExecutor.setSelectedAlgorithmType(SortAlgorithmType.MERGE_SORT);
        sortExecutor.setNumberOfThreads(5);
        sortExecutor.setSleepTime(0);
        sortExecutor.sort(values);

        try {
            latch.await(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads) {
        System.out.println("in: " + ArrayUtils.arrayToString(values));
        input = values.clone();
    }

    @Override
    public void didStep(SortAlgorithmType type, int[] values, int[] highlighted) { }

    @Override
    public void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish) {
        System.out.println("out: " + ArrayUtils.arrayToString(values));

        boolean isSorted = ArrayUtils.isSorted(values);
        System.out.println("isSorted: " + isSorted);
        assertTrue(isSorted);

        int[] output = values.clone();
        boolean checkIntegrity = ArrayUtils.checkIntegrity(input, output);
        System.out.println("checkIntegrity: "+ checkIntegrity);
        assertTrue(checkIntegrity);

        System.out.println("===========================================================================");
    }
}
