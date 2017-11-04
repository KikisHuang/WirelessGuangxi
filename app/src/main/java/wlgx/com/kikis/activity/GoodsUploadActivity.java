package wlgx.com.kikis.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.GoodsBean;
import wlgx.com.kikis.fragment.son.GoodsListFragment;
import wlgx.com.kikis.fragment.son.GoodsListThreeFragment;
import wlgx.com.kikis.fragment.son.GoodsListTwoFragment;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.ActionSheetDialog;

import static wlgx.com.kikis.utils.IntentUtils.goGoodsContentPage;
import static wlgx.com.kikis.utils.IntentUtils.goGoodsTypePage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.PhotoUtils.getMULTIPLEPhoto;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/10/18.
 */
public class GoodsUploadActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "GoodsUploadActivity";

    private ImageView camera_img, cover_img;
    private EditText goods_titles, goods_type, goods_keyword, goods_price, goods_freight, goods_inventory, goods_status, goods_details;
    private TextView goods_name, commit_tv;
    private FrameLayout cover_fl;
    private int putAwayFlag = 99;
    private File coverFile = null;
    private int tag = 99;
    private String id = "";
    private String Content = "";
    private String filePath = "";
    private String typeId = "";
    private LinearLayout goods_detail_layout;

    @Override
    protected void click() {
        cover_img.setOnClickListener(this);
        commit_tv.setOnClickListener(this);
        goods_status.setOnClickListener(this);
        goods_type.setOnClickListener(this);
        goods_details.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.goods_upload_layout);
        //第一次进入不弹出软键盘;
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setTitles(this, "上传商品");
        cover_img = f(R.id.cover_img);
        camera_img = f(R.id.camera_img);
        goods_detail_layout = f(R.id.goods_detail_layout);

        cover_fl = f(R.id.cover_fl);
        goods_titles = f(R.id.goods_titles);
        goods_keyword = f(R.id.goods_keyword);
        goods_price = f(R.id.goods_price);
        goods_freight = f(R.id.goods_freight);
        goods_inventory = f(R.id.goods_inventory);
        goods_status = f(R.id.goods_status);
        goods_details = f(R.id.goods_details);
        goods_type = f(R.id.goods_type);
        goods_name = f(R.id.goods_name);
        commit_tv = f(R.id.commit_tv);

    }

    @Override
    protected void initData() {
        tag = Integer.parseInt(getIntent().getStringExtra("status_tag"));
        if (tag == 1)
            getData();
        else
            cover_fl.setVisibility(View.VISIBLE);
    }

    private void getData() {
        id = getIntent().getStringExtra("goods_id");
        goods_detail_layout.setVisibility(View.GONE);
        /**
         * 商品信息;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETGOODSDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GoodsUploadActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            JSONObject ob = getJsonOb(response);
                            if (code == 1) {

                                GoodsBean gb = new Gson().fromJson(String.valueOf(ob), GoodsBean.class);
                                goods_titles.setText(gb.getTitle());
                                goods_type.setText(gb.getGoodsType().getTitle());
                                goods_keyword.setText(gb.getKeyword());
                                goods_price.setText(String.valueOf(gb.getPrice()));
                                goods_freight.setText(String.valueOf(gb.getFreight()));
                                goods_inventory.setText(String.valueOf(gb.getStock()));

                                if (gb.getStatus() == 1)
                                    goods_status.setText("已上架");
                                if (gb.getStatus() == 0)
                                    goods_status.setText("未上架");
                                if (gb.getStatus() == -1)
                                    goods_status.setText("已下架");
                                if (!gb.getGoodsType().getShop().getLogoUrl().isEmpty()) {
                                    Glide.with(GoodsUploadActivity.this).load(gb.getLogoUrl()).centerCrop().into(cover_img);
                                    camera_img.setVisibility(View.GONE);
                                    goods_name.setVisibility(View.GONE);
                                }
                                cover_fl.setVisibility(View.VISIBLE);
                                putAwayFlag = gb.getStatus();
                                Content = gb.getContent();
                                filePath = gb.getLogoUrl();
                                coverFile = null;
                                typeId = gb.getGoodsTypeId();
                            } else
                                ToastUtil.ToastErrorMsg(GoodsUploadActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    camera_img.setVisibility(View.GONE);
                    goods_name.setVisibility(View.GONE);

                    ArrayList<String> photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
//                    if (getFileOrFilesSize(photos.get(0), 3) > 2) {
                    Compress(photos.get(0));
//                    } else
                    break;
                case 1213:
                    Bundle bundle1 = data.getExtras();
                    goods_type.setText(bundle1.getString("Goods_typeName"));
                    typeId = bundle1.getString("Goods_typeId");
                    break;
                case 1214:
                    Bundle bundle = data.getExtras();
                    Content = bundle.getString("goods_content");
                    Log.i(TAG, "Content ===" + Content);
                    break;
//
            }
        }
    }

    /**
     * 压缩
     *
     * @param path
     */
    private void Compress(String path) {

        Luban.with(this)
                .load(path)                                   // 传人要压缩的图片列表
                .ignoreBy(1500)                                  // 忽略不压缩图片的大小
//                .setTargetDir(FileManager.getSaveFilePath() + "gxLuban")                        // 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Glide.with(GoodsUploadActivity.this).load(file).centerCrop().into(cover_img);
                        filePath = file.getAbsolutePath();
                        coverFile = new File(file.getAbsolutePath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Log.i(TAG, "Comp Error ==" + e);
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cover_img:
                getMULTIPLEPhoto(this, 1);
                break;
            case R.id.goods_status:
                GoodsStautsChange();

                break;
            case R.id.goods_type:
                goGoodsTypePage(this, tag);
                break;
            //提交;
            case R.id.commit_tv:
                if (!goods_titles.getText().toString().isEmpty() && putAwayFlag != 99 && !goods_price.getText().toString().isEmpty() && !goods_freight.getText().toString().isEmpty() && !goods_inventory.getText().toString().isEmpty() && !filePath.isEmpty())
                    if (tag == 0)
                        CommitGoods();
                if (tag == 1)
                    ChangeGoods();
                else if (goods_titles.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品标题不能为空！！");
                else if (putAwayFlag == 99)
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品状态不能为空！！");
                else if (goods_price.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品价格不能为空！！");
                else if (goods_freight.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品运费不能为空！！");
                else if (goods_inventory.getText().toString().isEmpty())
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品库存不能为空！！");
                else if (filePath.isEmpty())
                    ToastUtil.toast2_bottom(GoodsUploadActivity.this, "商品预览图不能为空！！");
                break;
            //富文本编辑;
            case R.id.goods_details:
                goGoodsContentPage(GoodsUploadActivity.this, Content, tag);
                break;
        }
    }

    private void CommitGoods() {
        Show(GoodsUploadActivity.this, "提交中..", true, null);
        Log.i(TAG, "filePath =====" + filePath);

        /**
         * 添加商品;
         */
        coverFile = new File(filePath);
        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.ADDGOODS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("title", goods_titles.getText().toString())
                .addParams("goodsTypeId", typeId)
                .addParams("keyword", goods_keyword.getText().toString())
                .addParams("price", goods_price.getText().toString())
                .addParams("freight", goods_freight.getText().toString())
                .addParams("stock", goods_inventory.getText().toString())
                .addParams("status", putAwayFlag + "")
                .addParams("content", Content)
                .addFile("file", coverFile.getName(), coverFile)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GoodsUploadActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, "response ====" + response);
                            if (code == 1) {
                                if (GoodsListFragment.upDataListener != null)
                                    GoodsListFragment.upDataListener.onUpData();
                                if (GoodsListTwoFragment.upDataListener != null)
                                    GoodsListTwoFragment.upDataListener.onUpData();
                                if (GoodsListThreeFragment.upDataListener != null)
                                    GoodsListThreeFragment.upDataListener.onUpData();

                                ToastUtil.toast2_bottom(GoodsUploadActivity.this, "添加成功...");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(GoodsUploadActivity.this, response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });

    }

    private void ChangeGoods() {
        Show(GoodsUploadActivity.this, "提交中..", true, null);
        Log.i(TAG, "filePath =====" + filePath);
        /**
         * 修改商品;
         */
//        coverFile = new File(filePath);

        Map<String, String> params = new HashMap<>();
        params.put("title", goods_titles.getText().toString());
        params.put(MzFinal.KEY, SPreferences.getUserToken());
        params.put("goodsTypeId", typeId);
        params.put("id", id);
        params.put("keyword", goods_keyword.getText().toString());
        params.put("price", goods_price.getText().toString());
        params.put("freight", goods_freight.getText().toString());
        params.put("stock", goods_inventory.getText().toString());
        params.put("status", putAwayFlag + "");

        PostFormBuilder post = OkHttpUtils.post();
        post.url(MzFinal.URL + MzFinal.CHANGEGOODS);
        post.params(params);
        if (coverFile != null)
            post.addFile("file", coverFile.getName(), coverFile);

        post.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(GoodsUploadActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, "response ====" + response);
                            if (code == 1) {
                                if (GoodsListFragment.upDataListener != null)
                                    GoodsListFragment.upDataListener.onUpData();
                                if (GoodsListTwoFragment.upDataListener != null)
                                    GoodsListTwoFragment.upDataListener.onUpData();
                                if (GoodsListThreeFragment.upDataListener != null)
                                    GoodsListThreeFragment.upDataListener.onUpData();

                                ToastUtil.toast2_bottom(GoodsUploadActivity.this, "修改成功...");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(GoodsUploadActivity.this, response, code);
                        } catch (Exception e) {

                        }
                        Cancle();
                    }
                });

    }

    private void GoodsStautsChange() {
        new ActionSheetDialog(this).builder().
                addSheetItem("已上架", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        putAwayFlag = 1;
                        goods_status.setText("已上架");
                    }
                }).addSheetItem("未上架", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                goods_status.setText("未上架");
                putAwayFlag = 0;
            }
        }).addSheetItem("已下架", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                goods_status.setText("已下架");
                putAwayFlag = -1;
            }
        }).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }
}
