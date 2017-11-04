package wlgx.com.kikis.fragment.son;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import wlgx.com.kikis.adapter.GoodsListAdapter;
import wlgx.com.kikis.bean.GoodsBean;
import wlgx.com.kikis.fragment.BaseFragment;
import wlgx.com.kikis.listener.SpringListener;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.SpringUtils;
import wlgx.com.kikis.utils.ToastUtil;

import static wlgx.com.kikis.utils.IntentUtils.goGoodsUpLoadPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;

/**
 * Created by lian on 2017/10/18.
 */
public class GoodsListFragment extends BaseFragment implements SpringListener, UpDataListener {
    private static final String TAG = "GoodsListFragment";
    private ListView listView;
    private SpringView springview;
    private SpringListener listener;
    private int tag;
    private int page = 0;
    private GoodsListAdapter adapter;
    private List<GoodsBean> list;
    public static UpDataListener upDataListener;

    public void setTag(int i) {
        this.tag = i;
    }

    @Override
    protected int initContentView() {
        return R.layout.goodslist_fragment;
    }

    @Override
    protected void click() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (list.size() > 0)
                    goGoodsUpLoadPage(getActivity(), 1, list.get(position).getId());
            }
        });
    }

    @Override
    protected void init() {
        listener = this;
        upDataListener = this;
        listView = f(R.id.listView);
        list = new ArrayList<>();
        springview = f(R.id.springview);
        SpringUtils.SpringViewInit(springview, getActivity(), listener, getResources().getColor(R.color.white));
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        /**
         * 商品列表;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETBYPAGE)
                .addParams("page", String.valueOf(page))
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("pageSize", String.valueOf(page + 20))
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
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                if (b)
                                    list.clear();
                                if (ar.length() == 0 && !b) {
                                    ToastUtil.toast2_bottom(getActivity(), "这已经是全部了");
                                } else {
                                    for (int i = 0; i < ar.length(); i++) {
                                        GoodsBean gb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), GoodsBean.class);
                                        if (tag == 0 && gb.getStatus() == 1)
                                            list.add(gb);
                                        if (tag == 1 && gb.getStatus() == 0)
                                            list.add(gb);
                                    }
                                    if (adapter == null) {
                                        adapter = new GoodsListAdapter(getActivity(), list, tag);
                                        listView.setAdapter(adapter);
                                    } else
                                        adapter.notifyDataSetChanged();
                                }
                            } else
                                ToastUtil.ToastErrorMsg(getActivity(), response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "Error ==== " + e);
                        }
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode){
                case 3656:
                    getData(true);
                break;
            }
        }
    }

    @Override
    public void IsonRefresh(int i) {
        page = i;
        getData(true);
    }

    @Override
    public void IsonLoadmore(int b) {
        page += b;
        getData(false);
    }

    @Override
    public void onUpData() {
        page = 0;
        getData(true);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        upDataListener = null;
    }

}
