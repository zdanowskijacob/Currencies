import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import net.miginfocom.swing.MigLayout;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AreaRendererEndType;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtils;

import static java.awt.Color.white;

public class MainFrame extends JFrame {
    static final DecimalFormat df = new DecimalFormat("0.00");
    static MainFrame frame1;
    ApiCurrency apiCurrency = new ApiCurrency();

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainFrame.frame1 = new MainFrame();
                } catch (Exception var2) {
                    var2.printStackTrace();
                }

            }
        });
    }

    public static TableModel toTableModel(TreeMap<String, Object> map, String[] header) {
        Object[][] tableData = new Object[map.keySet().size()][2];
        int index = 0;

        for(Iterator var5 = map.entrySet().iterator(); var5.hasNext(); ++index) {
            Map.Entry<?, ?> entry = (Map.Entry)var5.next();
            tableData[index] = new Object[]{entry.getKey(), entry.getValue()};
        }

        DefaultTableModel model = new DefaultTableModel(tableData, header) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        return model;
    }

    protected void addLiveCurrencyDialog(ActionEvent e, JFrame frame) {
        try {
            LiveCurrencyDialog dialog = new LiveCurrencyDialog();
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(2);
            dialog.setSize(1000, 600);
            dialog.setLocationRelativeTo(frame);
            dialog.getContentPane().setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
            dialog.setLayout(new MigLayout());
            JLabel jLabel_title = new JLabel("HERE ARE SOME LIVE DATA");
            jLabel_title.setForeground(white);
            jLabel_title.setFont(jLabel_title.getFont().deriveFont(1, 32.0F));
            dialog.add(jLabel_title, "center, wrap, pushx, gap bottom 0px");
            CurrencyData currencyData = this.apiCurrency.getMainCurrencyData();
            JLabel base_currency = new JLabel("BASE CURRENCY: " + currencyData.getBase_info());
            base_currency.setFont(base_currency.getFont().deriveFont(1, 20.0F));
            base_currency.setForeground(white);
            dialog.add(base_currency, "left, wrap, pushx, gap top 40px");
            JLabel date_currency = new JLabel("DATE: " + currencyData.getDate_info());
            date_currency.setFont(date_currency.getFont().deriveFont(1, 20.0F));
            date_currency.setForeground(white);
            dialog.add(date_currency, "left, pushx");
            String[] title_names = new String[]{"CURRENCY", "PRICE PER 1 " + currencyData.getBase_info()};
            JTable t = new JTable(toTableModel(currencyData.getCurrencyPriceMap(), title_names));
            t.setBackground(Color.getHSBColor(0.5F, 0.01F, 0.75F));
            t.setIntercellSpacing(new Dimension(0, 0));
            t.setRowHeight(25);
            t.setSelectionBackground(new Color(79,93,117));
            t.setSelectionForeground(white);
            dialog.add(t, "center,wrap,pushx");
            JScrollPane js = new JScrollPane(t);
            js.setVisible(true);
            dialog.add(js);
            dialog.setVisible(true);
        } catch (Exception var11) {
            var11.printStackTrace();
        }

    }

    protected void addCalculatorDialog(ActionEvent e, JFrame frame) {
        try {
            InfoDialog dialog = new InfoDialog();
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(2);
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(frame);
            dialog.getContentPane().setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
            dialog.setLayout(new MigLayout("center,wrap"));
            JLabel jLabel_title = new JLabel("CURRENCY CALCULATOR");
            jLabel_title.setForeground(white);
            jLabel_title.setFont(jLabel_title.getFont().deriveFont(1, 32.0F));
            dialog.add(jLabel_title, "gap bottom 20px");
            TreeMap<String, Object> currencyPriceMap = this.apiCurrency.getCurrencyPriceMap();
            JLabel descCurr = new JLabel("Pick start currency and price:");
            descCurr.setForeground(white);
            descCurr.setFont(descCurr.getFont().deriveFont(1, 18.0F));
            dialog.add(descCurr);
            String[] arrayCurr = new String[currencyPriceMap.keySet().size()];
            final double[] arrayPrices = new double[currencyPriceMap.keySet().size()];
            int index = 0;

            for(Iterator var10 = currencyPriceMap.entrySet().iterator(); var10.hasNext(); ++index) {
                Map.Entry<?, ?> entry = (Map.Entry)var10.next();
                arrayCurr[index] = (String)entry.getKey();
                arrayPrices[index] = (Double)entry.getValue();
            }

            final JComboBox<String> jComboBox = new JComboBox(arrayCurr);
            jComboBox.setFont(jComboBox.getFont().deriveFont(1, 18.0F));
            final JTextField textFieldPrice = new JTextField(8);
            textFieldPrice.setFont(textFieldPrice.getFont().deriveFont(1, 18.0F));
            dialog.add(jComboBox);
            dialog.add(textFieldPrice, "gap bottom 10px");
            JLabel descCurr2 = new JLabel("Pick final currency:");
            descCurr2.setForeground(white);
            descCurr2.setFont(descCurr2.getFont().deriveFont(1, 18.0F));
            dialog.add(descCurr2);
            final JComboBox<String> jComboBox2 = new JComboBox(arrayCurr);
            jComboBox2.setFont(jComboBox2.getFont().deriveFont(1, 18.0F));
            dialog.add(jComboBox2);
            JButton jButton_check = new JButton("CHECK");
            jButton_check.setPreferredSize(new Dimension(235, 30));
            jButton_check.setMargin(new Insets(5, 20, 5, 20));
            jButton_check.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
            jButton_check.setForeground(white);
            jButton_check.setFocusable(false);
            jButton_check.setFont(jButton_check.getFont().deriveFont(1, 18.0F));
            dialog.add(jButton_check, "span 1 2, center, gap bottom 20px");
            final JLabel outputCurr = new JLabel();
            outputCurr.setFont(outputCurr.getFont().deriveFont(1, 18.0F));
            outputCurr.setForeground(white);
            dialog.add(outputCurr, "center, span 1 2, gap bottom 10px");
            jButton_check.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int start_index = jComboBox.getSelectedIndex();
                    int final_index = jComboBox2.getSelectedIndex();
                    double start_curr = arrayPrices[start_index];
                    double final_curr = arrayPrices[final_index];
                    double output = final_curr / start_curr * Double.parseDouble(textFieldPrice.getText());
                    outputCurr.setText("Price: " + MainFrame.df.format(output));
                }
            });
            dialog.setVisible(true);
        } catch (Exception var16) {
            var16.printStackTrace();
        }

    }

    protected void addInfoDialog(ActionEvent e, JFrame frame) {
        try {
            InfoDialog dialog = new InfoDialog();
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(2);
            dialog.setSize(500, 350);
            dialog.setLocationRelativeTo(frame);
            dialog.getContentPane().setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
            dialog.setLayout(new MigLayout());
            JLabel jLabel_title = new JLabel("CURRENCIES");
            jLabel_title.setFont(jLabel_title.getFont().deriveFont(1, 32.0F));
            jLabel_title.setForeground(white);
            dialog.add(jLabel_title, "center, wrap, pushx,gap bottom 30px");
            TreeMap<String, Object> currencyMap = this.apiCurrency.getMainCurrencyMap();
            String[] title_names = new String[]{"SHORTCUTS", "FULL NAMES"};
            JTable t = new JTable(toTableModel(currencyMap, title_names));
            t.setIntercellSpacing(new Dimension(0, 0));
            t.setRowHeight(25);
            t.setSelectionBackground(new Color(79,93,117));
            t.setSelectionForeground(white);
            dialog.add(t, "center,wrap,pushx");
            JScrollPane js = new JScrollPane(t);
            js.setVisible(true);
            dialog.add(js);
            dialog.setVisible(true);
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    protected void addCurrencyCharts(ActionEvent e, JFrame frame) {
        try {
            final InfoDialog dialog = new InfoDialog();
            dialog.setModalityType(ModalityType.APPLICATION_MODAL);
            dialog.setDefaultCloseOperation(2);
            dialog.setSize(1800, 1200);
            dialog.setLocationRelativeTo(frame);
            dialog.getContentPane().setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
            dialog.setLayout(new MigLayout());
            JLabel jLabel_title = new JLabel("CURRENCY CHARTS CREATOR");
            jLabel_title.setFont(jLabel_title.getFont().deriveFont(1, 32.0F));
            jLabel_title.setForeground(white);
            dialog.add(jLabel_title, "center, wrap, pushx,gap bottom 30px");
            List<TreeMap<String, Object>> treeMapList = this.apiCurrency.getTreeMapList("EUR", "");
            String[] arrayCurr = new String[((TreeMap)treeMapList.get(0)).size()];
            int index = 0;

            for(Iterator var8 = ((TreeMap)treeMapList.get(index)).entrySet().iterator(); var8.hasNext(); ++index) {
                Map.Entry<?, ?> entry = (Map.Entry)var8.next();
                arrayCurr[index] = (String)entry.getKey();
            }

            JLabel descCurr = new JLabel("Pick base currency:");
            descCurr.setForeground(white);
            descCurr.setFont(descCurr.getFont().deriveFont(1, 18.0F));
            dialog.add(descCurr, "center,wrap");
            final JComboBox<String> jComboBox = new JComboBox(arrayCurr);
            jComboBox.setFont(jComboBox.getFont().deriveFont(1, 18.0F));
            dialog.add(jComboBox, "center,wrap");
            JLabel descCurr2 = new JLabel("Pick final currency:");
            descCurr2.setForeground(white);
            descCurr2.setFont(descCurr2.getFont().deriveFont(1, 18.0F));
            dialog.add(descCurr2, "center,wrap");
            final JComboBox<String> jComboBox2 = new JComboBox(arrayCurr);
            jComboBox2.setFont(jComboBox2.getFont().deriveFont(1, 18.0F));
            dialog.add(jComboBox2, "center,wrap");
            JButton jButton_check = new JButton("CHECK");
            jButton_check.setPreferredSize(new Dimension(235, 30));
            jButton_check.setMargin(new Insets(5, 20, 5, 20));
            jButton_check.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
            jButton_check.setForeground(white);
            jButton_check.setFocusable(false);
            jButton_check.setFont(jButton_check.getFont().deriveFont(1, 18.0F));
            dialog.add(jButton_check, "center,wrap");
            final JLabel descCurr3 = new JLabel("");
            descCurr3.setFont(descCurr3.getFont().deriveFont(1, 18.0F));
            dialog.add(descCurr3, "center,wrap");
            jButton_check.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    List<TreeMap<String, Object>> treeMapListIn = null;

                    try {
                        treeMapListIn = MainFrame.this.apiCurrency.getTreeMapList(jComboBox.getSelectedItem().toString(), jComboBox2.getSelectedItem().toString());
                        descCurr3.setText("wynik:");
                    } catch (ParseException var10) {
                        throw new RuntimeException(var10);
                    } catch (IOException var11) {
                        throw new RuntimeException(var11);
                    }

                    String[] arrayDates = new String[treeMapListIn.size()];
                    Object[] arrayPrices = new BigDecimal[treeMapListIn.size()];
                    LocalDate todayLd = LocalDate.now();
                    String today = todayLd.toString();
                    int index = 0;

                    int ix;
                    for(ix = 0; index < 365; ++ix) {
                        LocalDate year_agoLd = todayLd.minusDays((long)index);
                        arrayDates[ix] = year_agoLd.toString();
                        index += 5;
                    }

                    index = 0;

                    for(ix = 0; ix < treeMapListIn.size(); ++ix) {
                        arrayPrices[index] = (BigDecimal)((TreeMap)treeMapListIn.get(index)).firstEntry().getValue();
                        ++index;
                    }

                    Collections.reverse(Arrays.asList(arrayDates));
                    Collections.reverse(Arrays.asList(arrayPrices));
                    TreeMap<String, Object> data_price = new TreeMap();

                    for(int i = 0; i < treeMapListIn.size(); ++i) {
                        data_price.put(arrayDates[i], arrayPrices[i]);
                    }

                    MainFrame.this.initUI(arrayDates, arrayPrices, dialog);
                }
            });
            dialog.setVisible(true);
        } catch (Exception var14) {
            var14.printStackTrace();
        }

    }

    void initUI(String[] arrayD, Object[] arrayP, Dialog dialog) {
        CategoryDataset dataset = this.createDataset(arrayD, arrayP);
        JFreeChart chart = this.createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(white);
        dialog.add(chartPanel, "center, wrap, grow, span");
        this.pack();
        this.setTitle("Line chart");
        this.setLocationRelativeTo((Component)null);
        this.setDefaultCloseOperation(3);
    }

    private CategoryDataset createDataset(String[] arrayD, Object[] arrayP) {
        double[] data1st = new double[arrayP.length];

        for(int i = 0; i < data1st.length; ++i) {
            data1st[i] = Double.parseDouble(arrayP[i].toString());
        }

        double[][] data = new double[][]{data1st};
        CategoryDataset dataset = DatasetUtils.createCategoryDataset(new String[]{"date"}, arrayD, data);
        return dataset;
    }

    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createAreaChart("price per day", "data", "price per day", dataset, PlotOrientation.VERTICAL, false, true, true);
        CategoryPlot plot = (CategoryPlot)chart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
        domainAxis.setCategoryMargin(0.3);
        AreaRenderer renderer = (AreaRenderer)plot.getRenderer();
        renderer.setEndType(AreaRendererEndType.LEVEL);
        renderer.setSeriesPaint(0, Color.RED);
        renderer.setSeriesStroke(0, new BasicStroke(2.0F));
        plot.setBackgroundPaint(white);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        chart.setTitle(new TextTitle("Prices for last 365 days", new Font("Serif", 1, 18)));
        return chart;
    }

    public MainFrame() throws IOException {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(3);
        frame.setSize(1080, 720);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocationRelativeTo((Component)null);
        frame.getContentPane().setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
        frame.setLayout(new MigLayout());
        JLabel jLabel_title = new JLabel("Currency Tracker");
        jLabel_title.setFont(jLabel_title.getFont().deriveFont(1, 40.0F));
        jLabel_title.setForeground(white);
        JButton jButton_live = new JButton("Live Currencies");
        jButton_live.setFocusable(false);
        jButton_live.setMargin(new Insets(5, 20, 5, 20));
        jButton_live.setPreferredSize(new Dimension(235, 30));
        jButton_live.setFont(jLabel_title.getFont().deriveFont(1, 20.0F));
        jButton_live.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
        jButton_live.setForeground(white);
        JButton jButton_calculator = new JButton("Currency Calculator");
        jButton_calculator.setFocusable(false);
        jButton_calculator.setMargin(new Insets(5, 20, 5, 20));
        jButton_calculator.setPreferredSize(new Dimension(235, 30));
        jButton_calculator.setFont(jLabel_title.getFont().deriveFont(1, 20.0F));
        jButton_calculator.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
        jButton_calculator.setForeground(white);
        JButton jButton_info = new JButton("Currencies");
        jButton_info.setFocusable(false);
        jButton_info.setMargin(new Insets(5, 20, 5, 20));
        jButton_info.setPreferredSize(new Dimension(235, 30));
        jButton_info.setFont(jLabel_title.getFont().deriveFont(1, 20.0F));
        jButton_info.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
        jButton_info.setForeground(white);
        JButton jButton_charts = new JButton("Currency charts");
        jButton_charts.setFocusable(false);
        jButton_charts.setMargin(new Insets(5, 20, 5, 20));
        jButton_charts.setPreferredSize(new Dimension(235, 30));
        jButton_charts.setFont(jLabel_title.getFont().deriveFont(1, 20.0F));
        jButton_charts.setBackground(Color.getHSBColor(0.60F, 0.32F, 0.46F));
        jButton_charts.setForeground(white);
        JPanel Button_panel = new JPanel();
        Button_panel.setBackground(Color.getHSBColor(0.63F, 0.32F, 0.26F));
        Button_panel.setPreferredSize(new Dimension(1000, 60));
        Button_panel.add(jButton_live);
        Button_panel.add(jButton_calculator);
        Button_panel.add(jButton_info);
        Button_panel.add(jButton_charts);

        JLabel jLabel_img = new JLabel();
        jLabel_img.setIcon(new ImageIcon(".\\images\\Currencies_image.png"));
        frame.add(jLabel_title, "center, wrap, pushx, gap bottom 80px, span");
        frame.add(Button_panel, "center, gap bottom 20px, wrap");
//        frame.add(jButton_live, " span 2, gap bottom 10px");
//        frame.add(jButton_calculator, " span 2, gap bottom 10px");
//        frame.add(jButton_info, "span 2, gap bottom 10px");
//        frame.add(jButton_charts, "span 2, gap bottom 10px, wrap");
        frame.add(jLabel_img, "center, span , wrap");

        frame.setVisible(true);
        jButton_live.addActionListener((e) -> {
            this.addLiveCurrencyDialog(e, frame);
        });
        jButton_calculator.addActionListener((e) -> {
            this.addCalculatorDialog(e, frame);
        });
        jButton_info.addActionListener((e) -> {
            this.addInfoDialog(e, frame);
        });
        jButton_charts.addActionListener((e) -> {
            this.addCurrencyCharts(e, frame);
        });
    }
}


