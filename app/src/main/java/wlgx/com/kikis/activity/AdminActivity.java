package wlgx.com.kikis.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.liaoinstan.springview.widget.SpringView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.AdminAdapter;
import wlgx.com.kikis.bean.AdminBean;
import wlgx.com.kikis.listener.MyFragmetnListener;
import wlgx.com.kikis.listener.SpringListener;
import wlgx.com.kikis.listener.addImgListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.SpringUtils;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.AdminInfoPopupWindow;
import wlgx.com.kikis.view.AlertDialog;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonAr;
import static wlgx.com.kikis.utils.SynUtils.setTitles;

/**
 * Created by lian on 2017/9/11.
 * 店员管理页面;
 */
public class AdminActivity extends InitActivity implements SpringListener, View.OnClickListener, addImgListener, MyFragmetnListener {
    private ListView listView;
    private SpringView springview;
    private SpringListener listener;
    private addImgListener addlistener;
    private MyFragmetnListener mylistener;
    private AdminAdapter adapter;
    private TextView right_tv;
    private List<AdminBean> list;
    private int page = 0;

    @Override
    protected void click() {
        right_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.admin_layout);
        setTitles(this, "店员管理");
        listener = this;
        mylistener = this;
        addlistener = this;
        list = new ArrayList<>();
        springview = f(R.id.springview);
        right_tv = f(R.id.right_tv);
        listView = f(R.id.listView);
        SpringUtils.SpringViewInit(springview, AdminActivity.this, listener, getResources().getColor(R.color.black99));
    }

    @Override
    protected void initData() {
        getData(true);
    }

    private void getData(final boolean b) {
        /**
         * 查询店员资料;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETADMINBYPAGE)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("page", page + "")
                .addParams("pageSize", String.valueOf(page + 20))
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AdminActivity.this, "网络不顺畅...");
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
                                    AdminBean ab = new Gson().fromJson(String.valueOf(ar.optJSONObject(i)), AdminBean.class);
                                    list.add(ab);
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new AdminAdapter(AdminActivity.this, list, addlistener);
                                    listView.setAdapter(adapter);
                                }
                            } else
                                ToastUtil.ToastErrorMsg(AdminActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_tv:
                AdminInfoPopupWindow ap = new AdminInfoPopupWindow(AdminActivity.this, 0, mylistener);
                ap.ScreenPopupWindow();
                break;
        }
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onChange(int pos) {
        if (pos <= list.size()) {
            AdminInfoPopupWindow ap = new AdminInfoPopupWindow(AdminActivity.this, 1, list.get(pos).getNickname(), list.get(pos).getStatus(), list.get(pos).getId(), mylistener);
            ap.ScreenPopupWindow();
        }
    }

    @Override
    public void onDelete(final int pos) {

        new AlertDialog(this).builder().setTitle("提示").setCancelable(true).setMsg("是否删除改员工信息？").setNegativeButton("取消", null).setPositiveButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteHe(pos);
            }
        }).show();
    }

    private void DeleteHe(int pos) {
        /**
         * 删除店员信息;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.DELETEBYIDS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("ids", list.get(pos).getId())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(AdminActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(AdminActivity.this, "成功删除员工信息..");
                                getData(true);
                            } else
                                ToastUtil.ToastErrorMsg(AdminActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        mylistener = null;
    }

    @Override
    public void onUpdata() {
        getData(true);
    }

    @Override
    public void onHide(int f) {

    }
}
