package wlgx.com.kikis.activity;

import android.content.Intent;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.ShopTypeAdapter;
import wlgx.com.kikis.bean.shopTypeBean;
import wlgx.com.kikis.listener.ShopTypeListener;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/18.
 */
public class ShopTypeActivity extends InitActivity implements ShopTypeListener {

    private static final String TAG = "ShopTypeActivity";
    private ListView left_listView, right_listView;
    private ShopTypeAdapter ladapter;
    private ShopTypeAdapter radapter;
    private List<shopTypeBean> llist;
    private List<shopTypeBean> rlist;
    private ShopTypeListener listener;
    private int last_item = -1;
    private String type = "";
    private TextView oldView;

    @Override
    protected void click() {

    }

    @Override
    protected void init() {
        setContentView(R.layout.shop_type_layout);
        setTitles(this, "行业类型");
        listener = this;
        left_listView = f(R.id.left_listView);
        right_listView = f(R.id.right_listView);
        llist = new ArrayList<>();
        rlist = new ArrayList<>();
    }

    @Override
    protected void initData() {
        getData("");
    }

    private void getData(final String shopTypeId) {
        /**
         * 查询分类;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETALL)
                .addParams("shopTypeId", shopTypeId)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ShopTypeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                if (shopTypeId.isEmpty()) {
                                    JSONArray ar = getJsonAr(response);
                                    for (int i = 0; i < ar.length(); i++) {
                                        shopTypeBean stb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), shopTypeBean.class);
                                        llist.add(stb);
                                    }
                                    ladapter = new ShopTypeAdapter(ShopTypeActivity.this, llist, listener, true);
                                    left_listView.setAdapter(ladapter);

                                } else {
                                    rlist.clear();
                                    JSONArray ar = getJsonAr(response);
                                    for (int i = 0; i < ar.length(); i++) {
                                        shopTypeBean stb = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), shopTypeBean.class);
                                        rlist.add(stb);
                                    }
                                    if (radapter != null) {
                                        radapter.notifyDataSetChanged();
                                    } else {
                                        radapter = new ShopTypeAdapter(ShopTypeActivity.this, rlist, listener, false);
                                        right_listView.setAdapter(radapter);
                                    }
                                }
                            } else
                                ToastUtil.ToastErrorMsg(ShopTypeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }


    @Override
    public void onMainMenuClick(String id, TextView title, int position) {

        Log.i(TAG, "setOnItemClickListener");
        title.setBackgroundResource(R.color.gray10);//把当前选中的条目加上选中效果
        if (last_item != -1 && last_item != position) //如果已经单击过条目并且上次保存的item位置和当前位置不同
            oldView.setBackgroundResource(R.color.white);//把上次选中的样式去掉

        type = title.getText().toString()+" ";
        oldView = title;//把当前的条目保存下来
        last_item = position;//把当前的位置保存下来
        getData(id);
    }

    @Override
    public void onSonMenuClick(String title, String id) {
        Log.i(TAG,""+id);
        Intent intent = new Intent();
        intent.putExtra("shop_type_name", type +=title);
        intent.putExtra("shop_type_id", id);
        setResult(10, intent);
        finish();
    }
}
