package wlgx.com.kikis.activity;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;
import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.BrowseBean;
import wlgx.com.kikis.bean.ChartsBean;
import wlgx.com.kikis.bean.GoodsRateBean;
import wlgx.com.kikis.bean.MakeBean;
import wlgx.com.kikis.bean.PieChartBean;
import wlgx.com.kikis.bean.RegisterBean;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RoundProgressBar;

import static wlgx.com.kikis.utils.ChartsUtils.generateColumnData;
import static wlgx.com.kikis.utils.ChartsUtils.generateInitialLineData;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/11.
 */
public class StatisActivity extends InitActivity {
    private LinearLayout user_layout, evaluate_layout, turnover_layout, progress_layout;
    private TextView goods_page, shop_page;
    private PieChartView pieChart;
    private PieChartData pieChardata;
    private List<PieChartBean> plist;
    private List<SliceValue> values;

    private List<ChartsBean> cblist;
    public final static String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug",
            "Sep", "Oct", "Nov", "Dec",};

    private LineChartView chartTop;
    private ColumnChartView chartBottom;

    private LineChartData lineData;
    private ColumnChartData columnData;
    private ScrollView scrollView;
    private double all;

    private View.OnTouchListener touchListener;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.statis_layout);
        setTitles(this, "数据统计");
        plist = new ArrayList<>();
        cblist = new ArrayList<>();
        values = new ArrayList<SliceValue>();
        evaluate_layout = f(R.id.evaluate_layout);
        user_layout = f(R.id.user_layout);
        turnover_layout = f(R.id.turnover_layout);
        progress_layout = f(R.id.progress_layout);
        pieChart = f(R.id.pie_chart);

        chartTop = f(R.id.chart_top);
        chartBottom = f(R.id.chart_bottom);

        scrollView = f(R.id.scrollView);
        goods_page = f(R.id.goods_page);
        shop_page = f(R.id.shop_page);
        user_layout.setVisibility(View.GONE);
        setImag(user_layout, 0);
        setImag(evaluate_layout, 1);
        setImag(turnover_layout, 2);
        touchInit();
    }

    private void initPieChart() {
        pieChardata = new PieChartData();
//        pieChardata.setHasLabels(true);//显示表情
        pieChardata.setHasLabelsOnlyForSelected(true);//不用点击显示占的百分比
        pieChardata.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChardata.setHasCenterCircle(true);//是否是环形显示
        pieChardata.setValues(values);//填充数据
        pieChardata.setCenterCircleColor(getResources().getColor(R.color.black2));//设置环形中间的颜色
        pieChardata.setCenterCircleScale(0.6f);//设置环形的大小级别
        pieChardata.setCenterText1(String.valueOf(all));//环形中间的文字1
        pieChardata.setCenterText1Color(Color.WHITE);//文字颜色
        pieChardata.setCenterText1FontSize(11);//文字大小

        pieChardata.setCenterText2("消费总量");
        pieChardata.setCenterText2Color(Color.WHITE);
        pieChardata.setCenterText2FontSize(12);
        /**这里也可以自定义你的字体   Roboto-Italic.ttf这个就是你的字体库*/
//      Typeface tf = Typeface.createFromAsset(this.getAssets(), "Roboto-Italic.ttf");
//      data.setCenterText1Typeface(tf);

        pieChart.setPieChartData(pieChardata);
        pieChart.setValueSelectionEnabled(true);//选择饼图某一块变大
        pieChart.setAlpha(1f);//设置透明度
        pieChart.setCircleFillRatio(1f);//设置饼图大小

    }


    @Override
    protected void initData() {
        getShopData(MzFinal.URL + MzFinal.REGISTER, 0);
        getShopData(MzFinal.URL + MzFinal.BROWSE, 1);
        getShopData(MzFinal.URL + MzFinal.EVALUATE, 2);
        getShopData(MzFinal.URL + MzFinal.ORDERRATIO, 3);

        getGraph(MzFinal.URL + MzFinal.ORDERTOTALFEE, 4);
        getGraph(MzFinal.URL + MzFinal.TRANSFORMATIONRATE, 5);

        pieChart.setOnTouchListener(touchListener);
        chartTop.setOnTouchListener(touchListener);
        chartBottom.setOnTouchListener(touchListener);

    }

    private void touchInit() {
        touchListener = new View.OnTouchListener() {
            float ratio = 1.8f; //水平和竖直方向滑动的灵敏度,偏大是水平方向灵敏
            float x0 = 0f;
            float y0 = 0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        x0 = event.getX();
                        y0 = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float dx = Math.abs(event.getX() - x0);
                        float dy = Math.abs(event.getY() - y0);
                        x0 = event.getX();
                        y0 = event.getY();
                        scrollView.requestDisallowInterceptTouchEvent(dx * ratio > dy);
                        break;
                }
                return false;
            }
        };
    }


    private void getGraph(String url, final int tag) {

        /**
         * 获取图形数据;
         */
        OkHttpUtils
                .get()
                .url(url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(StatisActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONArray ar = getJsonAr(response);
                                switch (tag) {
                                    case 4:

                                        all = 0;
                                        for (int i = 0; i < ar.length(); i++) {

                                            if (i < 5) {
                                                PieChartBean pb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), PieChartBean.class);
                                                plist.add(pb);
                                                all += pb.getPrice();
                                            }
                                        }
                                        for (int j = 0; j < plist.size(); j++) {
                                            View view = LayoutInflater.from(StatisActivity.this).inflate(R.layout.statis_progress_include, null);
                                            TextView tv1 = (TextView) view.findViewById(R.id.tv1);
                                            ImageView im = (ImageView) view.findViewById(R.id.img);

                                            im.setImageResource(MzFinal.piechartDrawable[j]);
                                            tv1.setText(plist.get(j).getName());

                                            TextView tv2 = (TextView) view.findViewById(R.id.tv2);
                                            ProgressBar pb1 = (ProgressBar) view.findViewById(R.id.progressbar);
                                            int p = (int) ((plist.get(j).getPrice() / all) * 100);
                                            tv2.setText(p + "%");
                                            pb1.setProgress(p);

                                            progress_layout.addView(view);
                                        }
                                        setPieChartData();
                                        break;
                                    case 5:

                                        for (int i = 0; i < ar.length(); i++) {
                                            ChartsBean cb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), ChartsBean.class);
                                            cblist.add(cb);
                                        }
                                        generateInitialLineData(lineData, chartTop, cblist);
                                        generateColumnData(columnData, chartBottom, cblist, getResources().getColor(R.color.yellow2));
                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(StatisActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void getShopData(String url, final int tag) {

        OkHttpUtils
                .get()
                .url(url)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(StatisActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);


                                switch (tag) {
                                    case 0:
                                        RegisterBean rb = new Gson().fromJson(String.valueOf(ob), RegisterBean.class);
                                        setData(user_layout, "用户注册\n " + String.valueOf(rb.getSum()), (int) (rb.getMonthly_growth_rate() * 100));
                                        break;
                                    case 1:
                                        BrowseBean bb = new Gson().fromJson(String.valueOf(ob), BrowseBean.class);
                                        goods_page.setText("商品浏览量\n " + bb.getBrowseGoods());
                                        shop_page.setText("店铺浏览量\n " + bb.getBrowseShop());
                                        break;
                                    case 2:
                                        GoodsRateBean gb = new Gson().fromJson(String.valueOf(ob), GoodsRateBean.class);
                                        setData(evaluate_layout, "商品评级\n " + String.valueOf(gb.getAvgEvaluate()), (int) (gb.getRanking() * 100));
                                        break;
                                    case 3:
                                        MakeBean mb = new Gson().fromJson(String.valueOf(ob), MakeBean.class);
                                        setData(turnover_layout, "成交量\n " + String.valueOf(mb.getSum() / 100), (int) (mb.getMonthly_growth_rate() * 100));
                                        break;
                                    case 5:

                                        break;
                                }
                            } else
                                ToastUtil.ToastErrorMsg(StatisActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    /**
     * 获取数据
     */
    private void setPieChartData() {

        for (int i = 0; i < plist.size(); ++i) {
            SliceValue sliceValue = new SliceValue((float) (int) ((plist.get(i).getPrice() / all) * 100), getResources().getColor(MzFinal.piechartColors[i]));//这里的颜色是我写了一个工具类 是随机选择颜色的
            sliceValue.setLabel(plist.get(i).getName() + "：" + plist.get(i).getPrice());
            values.add(sliceValue);
        }

        initPieChart();
    }

    private void setImag(LinearLayout user_layout, int att) {
        ImageView im = (ImageView) user_layout.findViewById(R.id.imgView);
        switch (att) {
            case 0:
                im.setImageResource(R.mipmap.register_icon);
                break;
            case 1:
                im.setImageResource(R.mipmap.evaluate_icon);
                break;
            case 2:
                im.setImageResource(R.mipmap.money_icon);
                break;
        }
    }

    private void setData(View view, String data, int rate) {
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(String.valueOf(data));
        RoundProgressBar r1 = (RoundProgressBar) view.findViewById(R.id.progress);
        r1.setProgress(rate);
    }
}
