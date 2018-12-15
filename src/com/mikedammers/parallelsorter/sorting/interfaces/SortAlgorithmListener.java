package com.mikedammers.parallelsorter.sorting.interfaces;

import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;

public interface SortAlgorithmListener {
    void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads);
    void didStep(SortAlgorithmType type, int[] values, int[] highlighted);
    void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish);
}
