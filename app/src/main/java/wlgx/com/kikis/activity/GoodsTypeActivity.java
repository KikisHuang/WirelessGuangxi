package wlgx.com.kikis.activity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.GoodsType;
import wlgx.com.kikis.listener.UpDataListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.DeviceUtils;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.AddGoodsTypePopupWindow;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/10/18.
 */
public class GoodsTypeActivity extends InitActivity implements View.OnClickListener, UpDataListener {

    private static final String TAG = "GoodsTypeActivity";
    private EditText input_ed;
    private LinearLayout goods_layout;
    private RippleView add_bt;
    private int tag;
    private int page = 0;
    private List<View> views;
    private String typeName = "";
    private List<String> idlist;
    private String typeId = "";
    private TextView add_tv;

    @Override
    protected void click() {
        add_bt.setOnClickListener(this);
        add_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.goods_type_layout);

        input_ed = f(R.id.input_ed);
        goods_layout = f(R.id.goods_layout);
        add_tv = f(R.id.add_tv);
        add_bt = f(R.id.add_bt);
        views = new ArrayList<>();
        idlist = new ArrayList<>();
    }

    @Override
    protected void initData() {
        tag = Integer.parseInt(getIntent().getStringExtra("type_tag"));
        setTitles(this, "选择商品分类");
        add_bt.setText("选择");
        getData();

    }

    private void getData() {
        /**
         * 商品列表;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GOODSTYPE)
                .addParams("page", String.valueOf(page))
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("pageSize", String.valueOf(999))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GoodsTypeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONArray ar = getJsonAr(response);
                            if (code == 1) {
                                idlist.clear();
                                views.clear();
                                goods_layout.removeAllViews();
                                for (int i = 0; i < ar.length(); i++) {
                                    GoodsType gb = new Gson().fromJson(String.valueOf(ar.getJSONObject(i)), GoodsType.class);
                                    AddType(gb, i);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(GoodsTypeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void AddType(GoodsType gb, final int i) {
        idlist.add(gb.getId());
        View v = LayoutInflater.from(GoodsTypeActivity.this).inflate(R.layout.goods_type_include, null);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.topMargin = DeviceUtils.dip2px(this, 5);
        v.setLayoutParams(lp);
        EditText ed = (EditText) v.findViewById(R.id.input_ed);
        ImageView icon = (ImageView) v.findViewById(R.id.tag_img);
        ImageView delete_img = (ImageView) v.findViewById(R.id.delete_img);
        delete_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                DeleteType(idlist.get(i));
            }
        });
        ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideOfShow(i);
            }
        });
        if (i == 0) {
            icon.setVisibility(View.VISIBLE);
            typeName = gb.getTitle();
            typeId = idlist.get(i);
        }
        ed.setText(gb.getTitle());
        goods_layout.addView(v);
        views.add(v);
    }

    private void DeleteType(String id) {

        /**
         * 删除商品类型;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.DELETETYPEBYIDS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GoodsTypeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(GoodsTypeActivity.this, "删除成功!");
                                getData();
                            } else
                                ToastUtil.ToastErrorMsg(GoodsTypeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    private void hideOfShow(int i) {

        for (int j = 0; j < views.size(); j++) {
            if (j == i) {
                views.get(j).findViewById(R.id.tag_img).setVisibility(View.VISIBLE);
                typeName = ((EditText) views.get(j).findViewById(R.id.input_ed)).getText().toString();
                typeId = idlist.get(i);
            } else
                views.get(j).findViewById(R.id.tag_img).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_bt:
                Intent intent = new Intent();
                intent.putExtra("Goods_typeName", typeName);
                intent.putExtra("Goods_typeId", typeId);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.add_tv:
                AddGoodsTypePopupWindow atp = new AddGoodsTypePopupWindow(GoodsTypeActivity.this, this);
                atp.ScreenPopupWindow();
                break;
        }
    }


    @Override
    public void onUpData() {
        getData();
    }
}
