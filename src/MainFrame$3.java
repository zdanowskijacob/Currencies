import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

class MainFrame$3 implements ActionListener {
    private final MainFrame this$0;
    private final JComboBox val$jComboBox;
    private final JComboBox val$jComboBox2;
    private final double[] val$arrayPrices;
    private final JTextField val$textFieldPrice;
    private final JLabel val$outputCurr;

    MainFrame$3(MainFrame this$0, JComboBox var2, JComboBox var3, double[] var4, JTextField var5, JLabel var6) {
        this.this$0 = this$0;
        this.val$jComboBox = var2;
        this.val$jComboBox2 = var3;
        this.val$arrayPrices = var4;
        this.val$textFieldPrice = var5;
        this.val$outputCurr = var6;
    }

    public void actionPerformed(ActionEvent e) {
        int start_index = this.val$jComboBox.getSelectedIndex();
        int final_index = this.val$jComboBox2.getSelectedIndex();
        double start_curr = this.val$arrayPrices[start_index];
        double final_curr = this.val$arrayPrices[final_index];
        double output = final_curr / start_curr * Double.parseDouble(this.val$textFieldPrice.getText());
        this.val$outputCurr.setText("price: " + MainFrame.df.format(output));
    }
}
