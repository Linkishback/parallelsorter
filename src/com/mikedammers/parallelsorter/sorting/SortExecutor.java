package com.mikedammers.parallelsorter.sorting;

import com.mikedammers.parallelsorter.Constants;
import com.mikedammers.parallelsorter.sorting.algorithms.base.BaseSortAlgorithm;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

public class SortExecutor implements SortAlgorithmListener {
    private final SortAlgorithmListener listener;

    private Thread sortAlgorithmThread;
    private SortAlgorithmType selectedAlgorithmType = Constants.DEFAULT_SORT_ALGORTIHM_TYPE;
    private BaseSortAlgorithm sortAlgorithm;
    private int numberOfThreads = 1;
    private long sleepTime = Constants.DEFAULT_SLEEP_TIME;

    // Public functions

    public SortExecutor(SortAlgorithmListener listener) {
        this.listener = listener;
    }

    public void sort(int[] values) {
        sortAlgorithm = selectedAlgorithmType.createAlgorithm(values, this, numberOfThreads, sleepTime);

        sortAlgorithmThread = new Thread(sortAlgorithm);
        sortAlgorithmThread.start();
    }

    // Private functions

    public void stopCurrent() {
        if (sortAlgorithm != null) {
            sortAlgorithm.stop();
        }

        if (sortAlgorithmThread != null) {
            sortAlgorithmThread.stop();
            sortAlgorithmThread = null;
        }
    }

    // Getters & Setters

    public void setSelectedAlgorithmType(SortAlgorithmType sortAlgorithmType) {
        this.selectedAlgorithmType = sortAlgorithmType;
    }

    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    // SortAlgorithmListener

    @Override
    public void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads) {
        System.out.println("didBegin: " + type.getDescription() + " - numberOfThreads: " + numberOfThreads);

        listener.didBegin(type, values, numberOfThreads);
    }

    @Override
    public void didStep(SortAlgorithmType type, int[] values, int[] highlighted) {
        listener.didStep(type, values, highlighted);
    }

    @Override
    public void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish) {
        System.out.println("didEnd: " + type.getDescription() + " - didFinish: " + didFinish + " - duration: " + duration + "ms");

        listener.didEnd(type, values, duration, didFinish);
    }
}
