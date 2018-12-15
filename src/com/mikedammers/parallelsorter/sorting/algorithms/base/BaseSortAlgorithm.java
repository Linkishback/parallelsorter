package com.mikedammers.parallelsorter.sorting.algorithms.base;

import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseSortAlgorithm implements Runnable {
    private SortAlgorithmType type;
    protected int[] values;
    private SortAlgorithmListener listener;
    protected ExecutorService threadPool;
    protected final int maxNumberOfThreads;
    private long start;
    private final long sleepTime;

    public BaseSortAlgorithm(SortAlgorithmType type, int[] values, SortAlgorithmListener listener, int maxNumberOfThreads, long sleepTime) {
        this.type = type;
        this.values = values;
        this.listener = listener;
        this.maxNumberOfThreads = maxNumberOfThreads;
        this.sleepTime = sleepTime;

        setupThreadPool();
    }

    private void setupThreadPool() {
        this.threadPool = Executors.newFixedThreadPool(maxNumberOfThreads);
    }

    protected void updateUIAndSleep(int[] values, int[] highlighted) {
        if (listener != null) {
            listener.didStep(this.type, values, highlighted);
        }

        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) { }
    }

    protected void begin() {
        start = System.currentTimeMillis();

        if (listener != null) {
            listener.didBegin(this.type, values, maxNumberOfThreads);
        }
    }

    protected void end(boolean didFinish) {
        long duration = getDuration();

        if (listener != null) {
            listener.didEnd(this.type, values, duration, didFinish);
            listener = null;
        }
    }

    public void stop() {
        end(false);
    }

    private long getDuration() {
        long end = System.currentTimeMillis();

        return end - start;
    }
}
