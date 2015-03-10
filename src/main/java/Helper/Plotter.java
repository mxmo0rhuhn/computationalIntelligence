package Helper;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

import Exercise_3_VEGA.VEGA;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.util.ShapeUtilities;

/**
 * Created by Max Schrimpf
 */
public class Plotter extends JFrame {

    /**
     * The number of generations run by the step all
     */
    private static final int NUM_GENERATION = 100;

    private static final String title = "Computational Intelligence";
    private JLabel label = new JLabel();
    private ChartPanel chartPanel;
    final DefaultXYDataset dataset = new DefaultXYDataset();

    private EA curEA;

    public Plotter() {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0, 5));
        curEA = new VEGA();
        chartPanel = createChart();
        plot(curEA.firstGeneration());
        this.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(step());
        panel.add(stepall());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(label), BorderLayout.EAST);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void plot(List<Individuum> individuums) {
        int generation = curEA.getGeneration();
        String text= "<html>";
        int i = 0;
        double[][] series = new double[2][curEA.getMaxIndividual()];
        for (Individuum indi : individuums) {
            series[0][i] = indi.getFValue();
            series[1][i] = indi.getGValue();
            i++;
            text += indi.toExtendedString() + "<br/>";
        }
        dataset.addSeries("Generation " + generation, series);
        dataset.removeSeries("Generation " + (generation - 1));
        text += "</html>";
        label.setText(text);
        chartPanel.getChart().setTitle("Generation " + generation);
    }
    private JButton stepall() {
        final JButton auto = new JButton(new AbstractAction(NUM_GENERATION + " Steps") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable(){

                    @Override
                    public void run() {
                        for (int i = 0; i < NUM_GENERATION; i++) {
                            SwingUtilities.invokeLater(new Runnable() {

                                @Override
                                public void run() {
                                    plot(curEA.runGeneration());
                                }});
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }
                }).start();
            };
        });
        return auto;
    }
    private JButton step() {
        final JButton auto = new JButton(new AbstractAction("Next") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EventQueue.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        plot(curEA.runGeneration());
                    }
                });
            }
        });
        return auto;
    }

    private ChartPanel createChart() {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
                "Scatter Plot Demo", "F", "G", dataset,
                PlotOrientation.VERTICAL, false, false, false);
        Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();

        ValueAxis gAxis = new NumberAxis("G");
        gAxis.setRange(0, curEA.getMaxG());
        gAxis.setAutoRange(false);

        ValueAxis fAxis = new NumberAxis("F");
        fAxis.setRange(0, curEA.getMaxF());
        fAxis.setAutoRange(false);

        xyPlot.setDomainAxis(fAxis);
        xyPlot.setRangeAxis(gAxis);
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, cross);
        renderer.setSeriesPaint(0, Color.red);
        return new ChartPanel(jfreechart);
    }

    public static void main(String[] args) {
        new Plotter();
    }
}
