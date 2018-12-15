package com.mikedammers.parallelsorter.sorting.algorithms;

import com.mikedammers.parallelsorter.sorting.algorithms.base.BaseSortAlgorithm;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

import java.util.concurrent.atomic.AtomicInteger;

public class QuickSort extends BaseSortAlgorithm {

    public QuickSort(SortAlgorithmType type, int[] values, SortAlgorithmListener listener, int numberOfThreads, long sleepTime) {
        super(type, values, listener, numberOfThreads, sleepTime);
    }

    @Override
    public void run() {
        begin();

        final AtomicInteger runningThreadCount = new AtomicInteger(1);
        threadPool.execute(new QuickSortRunnable(values, 0, values.length - 1, runningThreadCount));

        try {
            synchronized (runningThreadCount) {
                runningThreadCount.wait();

                end(true);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class QuickSortRunnable implements Runnable {
        private int[] values;
        private final int left;
        private final int right;
        private final AtomicInteger runningThreadCount;

        public QuickSortRunnable(int[] values, int left, int right, AtomicInteger runningThreadCount) {
            this.values = values;
            this.left = left;
            this.right = right;
            this.runningThreadCount = runningThreadCount;
        }

        @Override
        public void run() {
            sort(values, left, right);

            synchronized (runningThreadCount) {
                if (runningThreadCount.getAndDecrement() == 1) {
                    runningThreadCount.notify();
                }
            }
        }

        private void sort(int[] values, int low, int high) {
            updateUIAndSleep(values, new int[]{low, high});

            if (low < high) {
                int pivot = partition(values, low, high);

                if (runningThreadCount.get() <= maxNumberOfThreads - 2) {
                    runningThreadCount.getAndAdd(2);
                    threadPool.execute(new QuickSortRunnable(values, low, pivot - 1, runningThreadCount));
                    threadPool.execute(new QuickSortRunnable(values, pivot + 1, high, runningThreadCount));
                } else {
                    sort(values, low, pivot - 1);
                    sort(values, pivot + 1, high);
                }

                updateUIAndSleep(values, new int[] {low, high, pivot});
            }
        }

        private int partition(int values[], int low, int high) {
            int pivot = values[high];
            int i = (low - 1);

            for (int j = low; j < high; j++) {
                if (values[j] <= pivot) {
                    i++;

                    int temp = values[i];
                    values[i] = values[j];
                    values[j] = temp;
                }
            }

            int temp = values[i + 1];
            values[i + 1] = values[high];
            values[high] = temp;

            return i + 1;
        }
    }
}
