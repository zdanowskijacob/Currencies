import java.util.TreeMap;

public class CurrencyData {
    private String base = "base";
    private String date = "date";
    private String base_info;
    private String date_info;
    TreeMap<String, Object> currencyPriceMap;

    public CurrencyData(String base_info, String date_info, TreeMap<String, Object> currencyPriceMap) {
        this.base_info = base_info;
        this.date_info = date_info;
        this.currencyPriceMap = currencyPriceMap;
    }

    public String getBase_info() {
        return this.base_info;
    }

    public String getDate_info() {
        return this.date_info;
    }

    public TreeMap<String, Object> getCurrencyPriceMap() {
        return this.currencyPriceMap;
    }
}
