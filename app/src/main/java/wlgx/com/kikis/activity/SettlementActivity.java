package wlgx.com.kikis.activity;

import android.util.Log;
import android.widget.ListView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.SettlementAdapter;
import wlgx.com.kikis.bean.SettlementBean;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/11/30.
 */
public class SettlementActivity extends InitActivity {
    private static final String TAG = "SettlementActivity";

    private ListView listView;
    private SettlementAdapter adapter;
    private int tag;
    private List<SettlementBean> list;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.settlement_of_checking_layout);
        listView = f(R.id.listView);
        list = new ArrayList<>();
        tag = Integer.parseInt(getIntent().getStringExtra("page_tag"));
        if (tag == 0)
            setTitles(this, "结算记录");
        else
            setTitles(this, "对账");

        adapter = new SettlementAdapter(getApplicationContext(), list, tag);
        listView.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        switch (tag) {
            case 0:
                getData(MzFinal.SETTLEMENTRECORD);
                break;
            case 1:
                getData(MzFinal.ORDERBILL);
                break;
        }
    }

    private void getData(String url) {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + url)
                .addParams("key", SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(SettlementActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            list.clear();
                            int code = getCode(response);
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                for (int i = 0; i < ar.length(); i++) {
                                    SettlementBean sb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), SettlementBean.class);
                                    list.add(sb);
                                }
                                adapter.notifyDataSetChanged();
                            } else {
                                ToastUtil.ToastErrorMsg(SettlementActivity.this, response, code);
                            }
                        } catch (Exception e) {
                            Log.i(TAG, "Json Paser Error === " + e);
                        }
                    }
                });
    }
}
