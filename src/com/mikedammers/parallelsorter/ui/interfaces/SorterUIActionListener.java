package com.mikedammers.parallelsorter.ui.interfaces;

import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;

public interface SorterUIActionListener {
    void didPressStartButton();
    void didPressStopButton();
    void didPressShuffleButton();
    void didPressFixedArrayHundredButton();
    void didPressFixedArrayTenButton();
    void didSelectSortAlgorithmType(SortAlgorithmType type);
    void didChangeNumberOfThreads(int sliderValue);
    void didChangeSleepTime(long sleepTime);
}
