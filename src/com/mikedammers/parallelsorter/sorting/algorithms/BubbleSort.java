package com.mikedammers.parallelsorter.sorting.algorithms;

import com.mikedammers.parallelsorter.sorting.algorithms.base.BaseSortAlgorithm;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class BubbleSort extends BaseSortAlgorithm {
    public BubbleSort(SortAlgorithmType type, int[] values, SortAlgorithmListener listener, int numberOfThreads, long sleepTime) {
        super(type, values, listener, numberOfThreads, sleepTime);
    }

    @Override
    public void run() {
        begin();

        int i, n = values.length;

        for (int phase = 0; phase < n; phase++) {

            if (phase % 2 == 0) { // EVEN PHASE

                List<Callable<Object>> swapOperations = new ArrayList<>();

                for (i = 1; i < n; i += 2) {
                    swapOperations.add(Executors.callable(new ParallelOddEvenSortSwapRunnable(values, i - 1, i)));
                }

                try {
                    threadPool.invokeAll(swapOperations);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } else { // ODD PHASE

                List<Callable<Object>> swapOperations = new ArrayList<>();

                for (i = 1; i < n - 1; i += 2) {
                    swapOperations.add(Executors.callable(new ParallelOddEvenSortSwapRunnable(values, i, i + 1)));
                }

                try {
                    threadPool.invokeAll(swapOperations);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        end(true);
    }

    private class ParallelOddEvenSortSwapRunnable implements Runnable {
        private final int[] values;
        private final int i, j;

        private ParallelOddEvenSortSwapRunnable(int[] values, int i, int j) {
            this.values = values;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            if (values[i] > values[j]) {
                int temp = values[i];
                values[i] = values[j];
                values[j] = temp;

                updateUIAndSleep(values, new int[] {i, j});
            }
        }
    }

}