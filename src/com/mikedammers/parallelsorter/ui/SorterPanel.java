package com.mikedammers.parallelsorter.ui;

import com.mikedammers.parallelsorter.sorting.enums.SortAlgorithmType;
import com.mikedammers.parallelsorter.sorting.interfaces.SortAlgorithmListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SorterPanel extends JPanel implements SortAlgorithmListener {
    private int[] values;
    private ArrayList highlightedIndexesList = new ArrayList<Integer>();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                int barHeight = getHeight() / values.length * values[i];
                int barWidth = getWidth() / values.length;

                g.setColor(Color.green);

                if (highlightedIndexesList.contains(i)) {
                    g.setColor(Color.red);
                }

                g.fillRect(i * barWidth, getHeight() - barHeight, barWidth, barHeight);
            }
        }
    }

    @Override
    public void didBegin(SortAlgorithmType type, int[] values, int numberOfThreads) {

    }

    @Override
    public void didStep(SortAlgorithmType type, int[] values, int[] highlighted) {
        this.values = values;

        highlightedIndexesList.clear();

        if (highlighted != null) {
            for (int i = 0; i < highlighted.length; i++) {
                highlightedIndexesList.add(highlighted[i]);
            }
        }

        repaint();
    }

    @Override
    public void didEnd(SortAlgorithmType type, int[] values, long duration, boolean didFinish) {

    }
}
