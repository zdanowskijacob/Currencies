import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import javax.json.JsonException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.JSONObject;

public class ApiCurrency {
    private OkHttpClient client;
    private Request request;
    private CurrencyData mainCurrencyData;
    private TreeMap<String, Object> mainCurrencyMap;
    private TreeMap<String, Object> currencyPriceMap = new TreeMap();
    List<TreeMap<String, Object>> treeMapList;

    public TreeMap<String, Object> getCurrencyPriceMap() throws IOException {
        if (this.mainCurrencyData == null) {
            this.mainCurrencyData = this.latestPrices();
        }

        return this.currencyPriceMap;
    }

    public CurrencyData getMainCurrencyData() throws IOException {
        if (this.mainCurrencyData == null) {
            this.mainCurrencyData = this.latestPrices();
        }

        return this.mainCurrencyData;
    }

    public TreeMap<String, Object> getMainCurrencyMap() throws IOException {
        if (this.mainCurrencyMap == null) {
            this.mainCurrencyMap = this.symbolsApi();
        }

        return this.mainCurrencyMap;
    }

    public List<TreeMap<String, Object>> getTreeMapList(String basecurr, String finalcurr) throws ParseException, IOException {
        this.treeMapList = this.timeseriesChart(basecurr, finalcurr);
        return this.treeMapList;
    }

    public ApiCurrency() throws IOException {
    }

    public CurrencyData latestPrices() throws IOException {
        this.client = (new OkHttpClient()).newBuilder().build();
        Request request = (new Request.Builder()).url("https://api.apilayer.com/exchangerates_data/latest?symbols=&base=EUR").addHeader("apikey", "UjR4HUm9yAWpDhC6DiW3hv1DeQi7X5DU").method("GET", (RequestBody)null).build();
        Response response = this.client.newCall(request).execute();

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            String base = (String)jsonObject.get("base");
            String date = (String)jsonObject.get("date");
            JSONObject currencyObject = jsonObject.getJSONObject("rates");
            currencyObject.keySet().forEach((keyStr) -> {
                Object keyvalue = currencyObject.getDouble(keyStr);
                this.currencyPriceMap.put(keyStr, keyvalue);
            });
            return new CurrencyData(base, date, this.currencyPriceMap);
        } catch (JsonException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public TreeMap<String, Object> symbolsApi() throws IOException {
        this.client = (new OkHttpClient()).newBuilder().build();
        this.request = (new Request.Builder()).url("https://api.apilayer.com/exchangerates_data/symbols").addHeader("apikey", "UjR4HUm9yAWpDhC6DiW3hv1DeQi7X5DU").method("GET", (RequestBody)null).build();
        Response response = this.client.newCall(this.request).execute();
        TreeMap<String, Object> currencyMap = new TreeMap();

        try {
            JSONObject jsonObject = new JSONObject(response.body().string());
            JSONObject currencyObject = jsonObject.getJSONObject("symbols");
            currencyObject.keySet().forEach((keyStr) -> {
                Object keyvalue = currencyObject.get(keyStr);
                currencyMap.put(keyStr, keyvalue);
            });
        } catch (JsonException var5) {
            var5.printStackTrace();
        }

        return currencyMap;
    }

    public List<TreeMap<String, Object>> timeseriesChart(String baseCurr, String finalCurr) throws ParseException, IOException {
        LocalDate todayLd = LocalDate.now();
        String today = todayLd.toString();
        LocalDate year_agoLd = todayLd.minusDays(365L);
        String year_ago = year_agoLd.toString();
        this.client = (new OkHttpClient()).newBuilder().build();
        Request request = (new Request.Builder()).url("https://api.apilayer.com/exchangerates_data/timeseries?start_date=" + year_ago + "&end_date=" + today + "&symbols=" + finalCurr + "&base=" + baseCurr).addHeader("apikey", "UjR4HUm9yAWpDhC6DiW3hv1DeQi7X5DU").method("GET", (RequestBody)null).build();
        Response response = this.client.newCall(request).execute();
        AtomicInteger every5days = new AtomicInteger(1);
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONObject currencyObject = jsonObject.getJSONObject("rates");
        this.treeMapList = new LinkedList();
        currencyObject.keySet().forEach((keyStr) -> {
            TreeMap<String, Object> treeMap = new TreeMap();
            every5days.addAndGet(1);
            if (every5days.get() % 5 == 0) {
                JSONObject dateObject = currencyObject.getJSONObject(keyStr);
                dateObject.keySet().forEach((newKeyStr) -> {
                    treeMap.put(newKeyStr, dateObject.get(newKeyStr));
                });
                this.treeMapList.add(treeMap);
            }

        });
        return this.treeMapList;
    }
}
