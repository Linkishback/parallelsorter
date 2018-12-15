package com.mikedammers.parallelsorter.ui;

import com.mikedammers.parallelsorter.Constants;
import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;
import com.mikedammers.parallelsorter.ui.interfaces.SorterUIActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.Enumeration;

public class SorterUI implements SortAlgorithmListener {
    private SorterUIActionListener listener;

    private JFrame frame;
    private JPanel mainPanel;
    private SorterPanel sorterPanel;
    private JButton startButton;
    private JButton stopButton;
    private JButton shuffleButton;
    private JButton fixedArrayHundredButton;
    private JButton fixedButtonTen;
    private ButtonGroup radioButtonGroup;
    private JSlider threadSlider;
    private JSlider sleepTimeSlider;

    public SorterUI(SorterUIActionListener listener) {
        this.listener = listener;

        loadUI();

        setRunning(false);
    }

    public void present() {
        frame.setVisible(true);
    }

    // SortAlgorithmListener
    @Override
    public void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads) {
        // TODO: do something
    }

    @Override
    public void didStep(SortAlgorithmType type, int[] values, int[] highlighted) {
        sorterPanel.didStep(type, values, highlighted);
    }

    @Override
    public void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish) {
        setRunning(false);
    }

    public void setRunning(boolean isRunning) {
        startButton.setEnabled(!isRunning);
        stopButton.setEnabled(isRunning);
        shuffleButton.setEnabled(!isRunning);
        fixedArrayHundredButton.setEnabled(!isRunning);
        fixedButtonTen.setEnabled(!isRunning);
        threadSlider.setEnabled(!isRunning);

        setRadioButtonsEnabled(!isRunning);
    }

    private void setRadioButtonsEnabled(boolean enable) {
        Enumeration<AbstractButton> enumeration = radioButtonGroup.getElements();
        while (enumeration.hasMoreElements()) {
            enumeration.nextElement().setEnabled(enable);
        }
    }

    private void loadUI() {
        loadMainPanel();
        loadSorterPanel();
        loadRadioButtons();
        loadThreadSlider();
        loadSleepTimeSlider();
        loadControlButtons();
        loadFrame();
    }

    private void loadFrame() {
        frame = new JFrame("Parallel Sorter");
        frame.setSize(900, 530);
        frame.add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadMainPanel() {
        mainPanel = new JPanel();
        BoxLayout boxlayout = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
        mainPanel.setLayout(boxlayout);
    }

    private void loadSorterPanel() {
        sorterPanel = new SorterPanel();
        sorterPanel.setBackground(Color.black);
        mainPanel.add(sorterPanel);
    }

    private void loadRadioButtons() {
        JPanel sortAlgorithmPanel = new JPanel();
        sortAlgorithmPanel.setLayout(new BoxLayout(sortAlgorithmPanel, BoxLayout.X_AXIS));

        radioButtonGroup = new ButtonGroup();

        for (SortAlgorithmType type : SortAlgorithmType.values()) {
            JRadioButton b = new JRadioButton(type.getName());
            b.addActionListener(e -> selectSortAlgorithmType(type));
            sortAlgorithmPanel.add(b);
            radioButtonGroup.add(b);

            if (type == Constants.DEFAULT_SORT_ALGORTIHM_TYPE) {
                b.setSelected(true);
            }
        }

        mainPanel.add(sortAlgorithmPanel);
    }

    private void loadThreadSlider() {
        threadSlider = new JSlider();
        threadSlider.setMinimum(1);
        threadSlider.setMaximum(10);
        threadSlider.setMajorTickSpacing(1);
        threadSlider.setPaintTicks(true);
        threadSlider.setPaintLabels(true);
        threadSlider.setSnapToTicks(true);
        threadSlider.setValue(1);
        threadSlider.addChangeListener(e -> didChangeNumberOfThreads(threadSlider.getValue()));

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        sliderPanel.add(threadSlider);

        mainPanel.add(sliderPanel);
    }

    private void loadSleepTimeSlider() {
        sleepTimeSlider = new JSlider();
        sleepTimeSlider.setMinimum(0);
        sleepTimeSlider.setMaximum(200);
        sleepTimeSlider.setMajorTickSpacing(10);
        sleepTimeSlider.setPaintTicks(true);
        sleepTimeSlider.setPaintLabels(true);
        sleepTimeSlider.setSnapToTicks(true);
        sleepTimeSlider.setValue(Constants.DEFAULT_SLEEP_TIME);
        sleepTimeSlider.addChangeListener(e -> didChangeSleepTime(sleepTimeSlider.getValue()));

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        sliderPanel.add(sleepTimeSlider);

        mainPanel.add(sliderPanel);
    }

    private void didChangeSleepTime(int value) {
        listener.didChangeSleepTime(value);
    }

    private void didChangeNumberOfThreads(int sliderValue) {
        listener.didChangeNumberOfThreads(sliderValue);
    }

    private void loadControlButtons() {
        JPanel buttonPanel = new JPanel();

        startButton = new JButton("Start");
        startButton.addActionListener(e -> startButtonPressed());
        buttonPanel.add(startButton);

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopButtonPressed());
        buttonPanel.add(stopButton);

        shuffleButton = new JButton("Shuffle Array");
        shuffleButton.addActionListener(e -> shuffleButtonPressed());
        buttonPanel.add(shuffleButton);

        fixedArrayHundredButton = new JButton("Fixed Array [100]");
        fixedArrayHundredButton.addActionListener(e -> fixedArrayHundredButtonPressed());
        buttonPanel.add(fixedArrayHundredButton);

        fixedButtonTen = new JButton("Fixed Array [10]");
        fixedButtonTen.addActionListener(e -> fixedArrayTenButtonPressed());
        buttonPanel.add(fixedButtonTen);

        BoxLayout boxlayout2 = new BoxLayout(buttonPanel, BoxLayout.X_AXIS);
        buttonPanel.setLayout(boxlayout2);

        mainPanel.add(buttonPanel);
    }

    private void selectSortAlgorithmType(SortAlgorithmType type) {
        listener.didSelectSortAlgorithmType(type);
    }

    // Control buttons
    private void startButtonPressed() {
        listener.didPressStartButton();
    }

    private void stopButtonPressed() {
        listener.didPressStopButton();
    }

    private void shuffleButtonPressed() {
        listener.didPressShuffleButton();
    }

    private void fixedArrayHundredButtonPressed() {
        listener.didPressFixedArrayHundredButton();
    }

    private void fixedArrayTenButtonPressed() {
        listener.didPressFixedArrayTenButton();
    }
}
