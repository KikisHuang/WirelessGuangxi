package wlgx.com.kikis.fragment.son;

import android.widget.ListView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.OrderAdapter;
import wlgx.com.kikis.bean.OrderBean;
import wlgx.com.kikis.fragment.BaseFragment;
import wlgx.com.kikis.listener.OrderListener;
import wlgx.com.kikis.listener.SpringListener;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.SpringUtils;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.IntentUtils.goGoodsDetailsPage;
import static wlgx.com.kikis.utils.IntentUtils.goRefundOrderPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;

/**
 * Created by lian on 2017/9/11.
 */
public class AllOrderFragment extends BaseFragment implements SpringListener, OrderListener, UpDataListener {
    private static final String TAG = "MyOrderFragment";
    private int tag;
    private SpringView springview;
    private ListView listView;
    private SpringListener listener;
    private OrderListener olistener;
    private int page = 0;
    private OrderAdapter adapter;
    private List<OrderBean> list;
    public static UpDataListener updata;

    public void setTag(int i) {
        this.tag = i;
    }

    @Override
    protected int initContentView() {
        return R.layout.my_order_fragment;
    }

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        listener = this;
        olistener = this;
        list = new ArrayList<>();
        updata = this;
        springview = f(R.id.springview);
        listView = f(R.id.listView);
        SpringUtils.SpringViewInit(springview, getActivity(), listener, getActivity().getResources().getColor(R.color.white));
        adapter = new OrderAdapter(getActivity(), list, tag, olistener);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        getOrder(true);
    }

    private void getOrder(final boolean b) {
        /**
         * 订单获取;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.ORDER)
                .addParams("model", String.valueOf(tag))
                .addParams("page", String.valueOf(page))
                .addParams("pageSize", String.valueOf(page + 20))
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(getActivity(), "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (b)
                                    list.clear();
                                JSONArray ar = getJsonAr(response);
                                for (int i = 0; i < ar.length(); i++) {
                                    OrderBean ob = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), OrderBean.class);
                                    list.add(ob);
                                }

                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new OrderAdapter(getActivity(), list, tag, olistener);
                                    listView.setAdapter(adapter);
                                }


                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    @Override
    public void IsonRefresh(int i) {
        page = i;
        getOrder(true);
    }

    @Override
    public void IsonLoadmore(int b) {
        page += b;
        getOrder(false);
    }

    @Override
    public void onDetails(String id) {
        goGoodsDetailsPage(getActivity(), id);
    }

    @Override
    public void onPayment(String id) {
//        OrderPayAndRefund(MzFinal.URL + MzFinal.ORDERPAY, id);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updata = null;
    }

    @Override
    public void onRefund(String id) {
        goRefundOrderPage(getActivity(), id);
    }

    @Override
    public void onUpData() {
        page = 0;
        getOrder(true);
    }
}
