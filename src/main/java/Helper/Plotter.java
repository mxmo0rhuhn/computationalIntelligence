package Helper;

import javax.swing.*;
import javax.swing.plaf.BorderUIResource;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

import Exercise_1_GA.GeneticAlgorithm;
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
    private JLabel individuals = new JLabel();
    private JLabel topIndividual = new JLabel();
    private ChartPanel chartPanel;
    final DefaultXYDataset dataset = new DefaultXYDataset();

    private EA curEA;

    public static void main(String[] args) {
        new Plotter();
    }

    public Plotter() {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0, 5));
        chartPanel = createChart();
        this.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(step());
        panel.add(stepall());
        this.add(panel, BorderLayout.SOUTH);

        JPanel panel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel2.add(new JLabel("Best: "));
        panel2.add(topIndividual);
        this.add(panel2 , BorderLayout.NORTH);

        this.add(new JScrollPane(individuals), BorderLayout.EAST);

        initiateVEGA();

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
        individuals.setText(text);
        topIndividual.setText(curEA.getBest());
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
                "Scatter Plot Demo", "X", "Y", dataset,
                PlotOrientation.VERTICAL, false, false, false);

        Shape cross = ShapeUtilities.createDiagonalCross(3, 1);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, cross);
        renderer.setSeriesPaint(0, Color.red);
        return new ChartPanel(jfreechart);
    }

    private void initiateVEGA(){
        curEA = new GeneticAlgorithm();
        plot(curEA.firstGeneration());

        XYPlot xyPlot = (XYPlot) chartPanel.getChart().getPlot();

        ValueAxis gAxis = new NumberAxis("G");
        gAxis.setRange(0, curEA.getMaxG());
        gAxis.setAutoRange(false);

        ValueAxis fAxis = new NumberAxis("F");
        fAxis.setRange(0, curEA.getMaxF());
        fAxis.setAutoRange(false);

        xyPlot.setDomainAxis(fAxis);
        xyPlot.setRangeAxis(gAxis);
    }
}
