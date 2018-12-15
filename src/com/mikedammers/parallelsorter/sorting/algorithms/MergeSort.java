package com.mikedammers.parallelsorter.sorting.algorithms;

import com.mikedammers.parallelsorter.sorting.algorithms.base.BaseSortAlgorithm;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeSort extends BaseSortAlgorithm {
    public MergeSort(SortAlgorithmType type, int[] values, SortAlgorithmListener listener, int maxNumberOfThreads, long sleepTime) {
        super(type, values, listener, maxNumberOfThreads, sleepTime);
    }

    @Override
    public void run() {
        begin();

        final AtomicInteger runningThreadCount = new AtomicInteger(1);
        Future result = threadPool.submit(Executors.callable(new MergeSortRunnable(values, 0, values.length - 1, runningThreadCount)));

        try {
            result.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        end(true);
    }

    private class MergeSortRunnable implements Runnable {
        private final int[] values;
        private final int l;
        private final int r;
        private final AtomicInteger runningThreadCount;

        public MergeSortRunnable(int[] values, int l, int r, AtomicInteger runningThreadCount) {
            this.values = values;
            this.l = l;
            this.r = r;
            this.runningThreadCount = runningThreadCount;
        }

        void merge(int[] values, int left, int middle, int right) {
            int leftArrayLength = middle - left + 1;
            int rightArrayLength = right - middle;

            int[] tempLeftArray = new int[leftArrayLength];
            int[] tempRightArray = new int[rightArrayLength];

            for (int i = 0; i < leftArrayLength; ++i) {
                tempLeftArray[i] = values[left + i];
            }

            for (int j = 0; j < rightArrayLength; ++j) {
                tempRightArray[j] = values[middle + 1 + j];
            }

            int i = 0, j = 0;

            int k = left;
            while (i < leftArrayLength && j < rightArrayLength) {
                if (tempLeftArray[i] <= tempRightArray[j]) {
                    values[k] = tempLeftArray[i];
                    i++;
                } else {
                    values[k] = tempRightArray[j];
                    j++;
                }

                k++;
            }

            while (i < leftArrayLength) {
                values[k] = tempLeftArray[i];
                i++;
                k++;
            }

            while (j < rightArrayLength) {
                values[k] = tempRightArray[j];
                j++;
                k++;
            }
        }

        void sort(int[] values, int left, int right) {
            if (left < right) {
                int middle = (left + right) / 2;

                if (runningThreadCount.get() <= maxNumberOfThreads - 2) {
                    runningThreadCount.getAndAdd(2);
                    Future resultA = threadPool.submit(new MergeSortRunnable(values, left, middle, runningThreadCount));
                    Future resultB = threadPool.submit(new MergeSortRunnable(values, middle + 1, right, runningThreadCount));

                    try {
                        resultA.get();
                        resultB.get();
                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                } else {
                    sort(values, left, middle);
                    sort(values, middle + 1, right);
                }

                merge(values, left, middle, right);

                updateUIAndSleep(MergeSort.this.values, new int[]{left, right, middle});
            }
        }

        @Override
        public void run() {
            sort(values, l, r);

            synchronized (runningThreadCount) {
                runningThreadCount.getAndDecrement();
            }
        }
    }
}