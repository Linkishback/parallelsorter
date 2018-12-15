package com.mikedammers.parallelsorter.sorting.enums;

import com.mikedammers.parallelsorter.sorting.algorithms.*;
import com.mikedammers.parallelsorter.sorting.algorithms.base.BaseSortAlgorithm;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

public enum SortAlgorithmType {
    BUBBLE_SORT,
    QUICK_SORT,
    MERGE_SORT;

    public BaseSortAlgorithm createAlgorithm(int[] values, SortAlgorithmListener listener, int numberOfThreads, long sleepTime) {
        switch (this) {
            case BUBBLE_SORT:
                return new BubbleSort(this, values, listener, numberOfThreads, sleepTime);
            case QUICK_SORT:
                return new QuickSort(this, values, listener, numberOfThreads, sleepTime);
            case MERGE_SORT:
                return new MergeSort(this, values, listener, numberOfThreads, sleepTime);
            default:
                return null;
        }
    }

    /**
     * @return name of the algorithm to show in the UI
     */
    public String getName() {
        switch (this) {
            case BUBBLE_SORT:
                return "Bubble (Odd Even)";
            case QUICK_SORT:
                return "Quick";
            case MERGE_SORT:
                return "Merge";
            default:
                return null;
        }
    }

    /**
     * @return description of the algorithm, used for printing debug data
     */
    public String getDescription() {
        switch (this) {
            case BUBBLE_SORT:
                return "BUBBLE_SORT";
            case QUICK_SORT:
                return "QUICK_SORT";
            case MERGE_SORT:
                return "MERGE_SORT";
            default:
                return null;
        }
    }
}
