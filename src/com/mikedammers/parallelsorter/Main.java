package com.mikedammers.parallelsorter;

import com.mikedammers.parallelsorter.sorting.SortExecutor;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;
import com.mikedammers.parallelsorter.ui.SorterUI;
import com.mikedammers.parallelsorter.ui.interfaces.SorterUIActionListener;
import com.mikedammers.parallelsorter.util.ArrayUtils;

public class Main implements SorterUIActionListener, SortAlgorithmListener {
    private SorterUI sorterUI;
    private SortExecutor sortExecutor;
    private int[] values;

    public Main() {
        sorterUI = new SorterUI(this);
        sorterUI.present();

        sortExecutor = new SortExecutor(this);

        resetValues();
    }

    public static void main(String[] args) {
        new Main();
    }

    // Control buttons
    @Override
    public void didPressStartButton() {
        sorterUI.setRunning(true);
        sortExecutor.sort(values); // TODO: input slider value
    }

    @Override
    public void didPressStopButton() {
        sorterUI.setRunning(false);
        sortExecutor.stopCurrent();
    }

    @Override
    public void didPressShuffleButton() {
        values = ArrayUtils.shuffle(values);
        sorterUI.didStep(null, values, null);
    }

    @Override
    public void didPressFixedArrayHundredButton() {
        values = ArrayUtils.generateFixedArrayHundred();
        sorterUI.didStep(null, values, null);
    }

    @Override
    public void didPressFixedArrayTenButton() {
        values = ArrayUtils.generateFixedArrayTen();
        sorterUI.didStep(null, values, null);
    }

    // DID CHANGE SLIDER VALUE

    @Override
    public void didSelectSortAlgorithmType(SortAlgorithmType type) {
        sortExecutor.setSelectedAlgorithmType(type);
    }

    @Override
    public void didChangeNumberOfThreads(int sliderValue) {
        sortExecutor.setNumberOfThreads(sliderValue);
    }

    @Override
    public void didChangeSleepTime(long sleepTime) {
        sortExecutor.setSleepTime(sleepTime);
    }

    // private functions
    private void resetValues() {
        values = ArrayUtils.generateFixedArrayHundred();
        sorterUI.didStep(null, values, null);
    }

    // SortAlgorithmListener

    @Override
    public void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads) {
        // TODO: do something
    }

    @Override
    public void didStep(SortAlgorithmType type, int[] values, int[] highlighted) {
        sorterUI.didStep(type, values, highlighted);
    }

    @Override
    public void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish) {
        sorterUI.didEnd(type, values, duration, didFinish);
    }
}
