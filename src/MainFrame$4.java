import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import javax.swing.JComboBox;
import javax.swing.JLabel;

class MainFrame$4 implements ActionListener {
    private final MainFrame this$0;
    private final JComboBox val$jComboBox;
    private final JComboBox val$jComboBox2;
    private final JLabel val$descCurr3;
    private final InfoDialog val$dialog;

    MainFrame$4(MainFrame this$0, JComboBox var2, JComboBox var3, JLabel var4, InfoDialog var5) {
        this.this$0 = this$0;
        this.val$jComboBox = var2;
        this.val$jComboBox2 = var3;
        this.val$descCurr3 = var4;
        this.val$dialog = var5;
    }

    public void actionPerformed(ActionEvent e) {
        List<TreeMap<String, Object>> treeMapListIn = null;

        try {
            treeMapListIn = this.this$0.apiCurrency.getTreeMapList(this.val$jComboBox.getSelectedItem().toString(), this.val$jComboBox2.getSelectedItem().toString());
            this.val$descCurr3.setText("wynik:");
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

        int i;
        for(i = 0; index < 365; ++i) {
            LocalDate year_agoLd = todayLd.minusDays((long)index);
            arrayDates[i] = year_agoLd.toString();
            index += 5;
        }

        index = 0;

        for(i = 0; i < treeMapListIn.size(); ++i) {
            arrayPrices[index] = (BigDecimal)((TreeMap)treeMapListIn.get(index)).firstEntry().getValue();
            ++index;
        }

        Collections.reverse(Arrays.asList(arrayDates));
        Collections.reverse(Arrays.asList(arrayPrices));
        TreeMap<String, Object> data_price = new TreeMap();

        for(i = 0; i < treeMapListIn.size(); ++i) {
            data_price.put(arrayDates[i], arrayPrices[i]);
        }

        this.this$0.initUI(arrayDates, arrayPrices, this.val$dialog);
    }
}
