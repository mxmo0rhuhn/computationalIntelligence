import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

public class EA extends JFrame {


    public static final int NUM_INDIVIDUUM = 30;
    public static final int NUM_GENERATION = 100;
    public static final int NUM_RECOMBINATED = 10;
    public static final int BLOCK_SIZE = 15;

    public List<Individuum> individuums;
    public int generation;

    private static final String title = "Computational Intelligence";
    private JLabel label = new JLabel();
    private ChartPanel chartPanel;
    final DefaultXYDataset dataset = new DefaultXYDataset();

    public void firstGeneration() {
        individuums = new ArrayList<Individuum>();
        generation = 0;

        for(int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.add(newIndividuum());
        }
    }

    public void runGeneration() {
        List<Individuum> block1 ;
        List<Individuum> block2 ;
        Random rnd = new Random();
        int curRnd;

        generation++;

        block1 = new ArrayList<Individuum>();
        for (Individuum i : individuums) {
            block1.add(i);
        }
        block2 = new ArrayList<Individuum>();

        for(int i = 1; i <= BLOCK_SIZE; i++) {
            curRnd = Math.round(((rnd.nextFloat() * block1.size())-0.5f));

            block2.add(block1.get(curRnd));
            block1.remove(curRnd);
        }

        block1 = Selector.RankBasedSelectionF1(block1);
        block2 = Selector.RankBasedSelectionF2(block2);

        individuums = new ArrayList<Individuum>();
        individuums.addAll(block1);
        individuums.addAll(block2);

        // recombinator
        individuums = Recombinator.singlePointRecombinationList(NUM_RECOMBINATED, individuums);

        // mutate
        for (int i = 0; i < NUM_INDIVIDUUM; i++) {
            individuums.set(i, Recombinator.mutate(individuums.get(i)));
        }
    }

    private Individuum newIndividuum() {
        Individuum ind = new Individuum(newRandomBinaryString(Individuum.D_LEN), newRandomBinaryString(Individuum.H_LEN));
        return ind;
    }

    private String newRandomBinaryString(int maxLen) {
        Random rnd = new Random();
        char[] binaryString = new char[maxLen];

        for(int i = 0; i < maxLen; i++) {
            if(rnd.nextBoolean()) {
                binaryString[i] = '1';
            } else {
                binaryString[i] = '0';
            }
        }
        return String.valueOf(binaryString);
    }

    public EA() {
        super(title);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout(0, 5));
        firstGeneration();
        chartPanel = createChart();
        plot();
        this.add(chartPanel, BorderLayout.CENTER);
        chartPanel.setHorizontalAxisTrace(true);
        chartPanel.setVerticalAxisTrace(true);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel.add(step());
        panel.add(stepall());
        this.add(panel, BorderLayout.SOUTH);
        this.add(new JScrollPane(label), BorderLayout.EAST);
        plot();
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public void plot() {
        String text= "<html>";
        int i = 0;
        double[][] series = new double[2][NUM_GENERATION];
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
                                    EA.this.runGeneration();
                                    plot();
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
                        EA.this.runGeneration();
                        plot();
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
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, cross);
        renderer.setSeriesPaint(0, Color.red);
        return new ChartPanel(jfreechart);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                EA cpd = new EA();
            }
        });
    }
}
