package wlgx.com.kikis.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.listener.CheckListener;
import wlgx.com.kikis.listener.onPhotoCutListener;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.ActionSheetDialog;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.CheckUtils.CheckShopName;
import static wlgx.com.kikis.utils.IntentUtils.goLocationPage;
import static wlgx.com.kikis.utils.IntentUtils.goShopTypePage;
import static wlgx.com.kikis.utils.IntentUtils.goUploadPhotoPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonSring;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/9/16.
 */
public class ApplicationInActivity extends InitActivity implements View.OnClickListener, onPhotoCutListener, CheckListener {
    private static final String TAG = "ApplicationInActivity";
    private LinearLayout shop_type_layout, shop_address_layout;
    private EditText shop_type_ed, shop_address_ed, shop_call_ed, shop_name_ed, shop_phone_ed, shop_pass_ed;
    private FrameLayout upload_img_layout, logo_img_layout, upload_img_layout2;
    private RippleView commit_shop_info, check_shop_name;

    private int page = 20;
    private Handler handler;
    private boolean sflag;
    private Runnable regisRunnable;
    private ImageView add_img, shop_img, logo_img, shop_img2;
    private TextView upload_tv;
    public static onPhotoCutListener listener;
    private String oldimgUrl = "";

    private String latitude = "";
    private String longitude = "";
    private String typeId = "";

    private String adcode = "";
    private String cityId = "";
    private String provinceId = "";
    private File logoFile = null;
    private File shop2File = null;
    private Map<String, File> fileMap;
    private long time = 0;

    @Override
    protected void click() {
        shop_address_layout.setOnClickListener(this);
        shop_type_layout.setOnClickListener(this);
        shop_type_ed.setOnClickListener(this);
        shop_address_ed.setOnClickListener(this);
        upload_img_layout.setOnClickListener(this);
        upload_img_layout2.setOnClickListener(this);
        shop_img.setOnClickListener(this);
        commit_shop_info.setOnClickListener(this);
        logo_img_layout.setOnClickListener(this);
        check_shop_name.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.appin_layout);
        setTitles(this, "申请入驻");
        fileMap = new HashMap<>();
        shop_type_ed = f(R.id.shop_type_ed);
        listener = this;
        shop_address_ed = f(R.id.shop_address_ed);
        shop_call_ed = f(R.id.shop_call_ed);
        shop_type_layout = f(R.id.shop_type_layout);
        shop_pass_ed = f(R.id.shop_pass_ed);
        upload_img_layout = f(R.id.upload_img_layout);
        upload_img_layout2 = f(R.id.upload_img_layout2);
        logo_img_layout = f(R.id.logo_img_layout);
        shop_phone_ed = f(R.id.shop_phone_ed);
        shop_name_ed = f(R.id.shop_name_ed);
        check_shop_name = f(R.id.check_shop_name);
//        shop_code_ed = f(R.id.shop_code_ed);
        commit_shop_info = f(R.id.commit_shop_info);

        shop_img = f(R.id.shop_img);
        shop_img2 = f(R.id.shop_img2);

        logo_img = f(R.id.logo_img);
        add_img = f(R.id.add_img);
        upload_tv = f(R.id.upload_tv);

        shop_address_layout = f(R.id.shop_address_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    protected void initData() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_type_layout:
                goShopTypePage(this);
                break;
            case R.id.shop_type_ed:
                goShopTypePage(this);
                break;
            case R.id.upload_img_layout2:
//                getMULTIPLEPhotoTag(this, 1, 4411);
                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(ApplicationInActivity.this, "0", "11");
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ApplicationInActivity.this, "1", "11");
                    }
                }).show();
                break;
            case R.id.shop_address_layout:
                goLocationPage(this);
                break;
            case R.id.shop_address_ed:
                goLocationPage(this);
                break;
            case R.id.check_shop_name:
                if (!shop_name_ed.getText().toString().isEmpty())
                    CheckShopName(MzFinal.URL + MzFinal.CHECKSHOPNAME, shop_name_ed.getText().toString(), this);
                else
                    ToastUtil.toast2_bottom(this, "店铺名称不能为空..");
                break;
            case R.id.commit_shop_info:
                fileMap.clear();

                if (time == 0)
                    com();
                else {
                    if (System.currentTimeMillis() - time > 1000) ;
                    com();
                }
                break;
            case R.id.logo_img_layout:
//                getMULTIPLEPhotoTag(this, 1, 445);
                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(ApplicationInActivity.this, "0", "5");
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ApplicationInActivity.this, "1", "5");
                    }
                }).show();
                break;
            case R.id.upload_img_layout:
//                getMULTIPLEPhotoTag(this, 1, 440);
                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(ApplicationInActivity.this, "0", "0");
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ApplicationInActivity.this, "1", "0");
                    }
                }).show();
                break;
            case R.id.shop_img:
//                getMULTIPLEPhotoTag(this, 1, 440);
                new ActionSheetDialog(this).builder().
                        addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                goUploadPhotoPage(ApplicationInActivity.this, "0", "0");
                            }
                        }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ApplicationInActivity.this, "1", "0");
                    }
                }).show();
                break;
        }
    }


    private void com() {
        if (logoFile != null) {
            fileMap.put(logoFile.getName(), logoFile);
            if (shop2File != null)
                fileMap.put(shop2File.getName(), shop2File);

            CommitShopInfo();
        } else
            ToastUtil.toast2_bottom(this, "请至少上传一张门店图片...");
    }

    private void CommitShopInfo() {
        OkHttpUtils.post()
                .url(MzFinal.URL + MzFinal.APPLYSHOP)
                .addParams("title", shop_name_ed.getText().toString())
                .addParams("address", shop_address_ed.getText().toString())
                .addParams("shopTypeId", typeId)
                .addParams("shopPhone", shop_call_ed.getText().toString())
                .addParams("longitude", longitude)
                .addParams("imgUrl", oldimgUrl)
                .addParams("latitude", latitude)
                .addParams("provinceId", provinceId)
                .addParams("phone", shop_phone_ed.getText().toString())
                .addParams("cityId", cityId)
                .addParams("password", shop_pass_ed.getText().toString())
                .addParams("areaId", adcode)
                .files("file", fileMap)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ApplicationInActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(ApplicationInActivity.this, "注册成功，请登录！！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(ApplicationInActivity.this, response, code);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (handler != null) {
            handler.removeCallbacks(regisRunnable);
            handler = null;
            sflag = false;
        }
        listener = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 440:
                    Show(ApplicationInActivity.this, "", true, null);
                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Compress(p, 0);
                    shop_img.setVisibility(View.VISIBLE);
                    break;

                case 4411:
                    Show(ApplicationInActivity.this, "", true, null);
                    ArrayList<String> path = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Compress(path, 11);
                    shop_img2.setVisibility(View.VISIBLE);
                    break;

            }
        }

        if (requestCode == 720 && resultCode == 10) {
            Bundle bundle = data.getExtras();
            String str = bundle.getString("shop_type_name");
            typeId = bundle.getString("shop_type_id");
            shop_type_ed.setText(str);
            Log.i(TAG, "shop_type_name ===" + str);
        }

        if (requestCode == 719 && resultCode == 1008) {

            Bundle bundle = data.getExtras();
            latitude = bundle.getString("Map_latitude");
            longitude = bundle.getString("Map_longitude");
            adcode = bundle.getString("Map_adcode");
            cityId = bundle.getString("Map_cityId");
            provinceId = bundle.getString("Map_provinceId");

            shop_address_ed.setText(bundle.getString("Map_address"));
            Log.i(TAG, "latitude ===" + latitude + "     longitude ===" + longitude + "   adcode ===" + adcode + "cityId ===" + cityId + " provinceId ===" + provinceId);
        }
    }

    /**
     * 压缩
     */
    private void Compress(List<String> list, final int i) {
        Luban.with(this)
                .load(list)                                   // 传人要压缩的图片列表
                .ignoreBy(300)                               // 忽略不压缩图片的大小
//                .setTargetDir(FileManager.getSaveFilePath() + "gxLuban")// 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                        subscriber.onNext(file.getAbsolutePath());
                        Log.i(TAG, "onSuccess" + file.getAbsolutePath());

                        if (i == 0) {
                            logoFile = file;
                            Glide.with(ApplicationInActivity.this).load(file).centerCrop().into(shop_img);
                            shop_img.setVisibility(View.VISIBLE);
                        }
                        if (i == 11) {
                            shop2File = file;
                            Glide.with(ApplicationInActivity.this).load(file).centerCrop().into(shop_img2);
                            shop_img2.setVisibility(View.VISIBLE);
                        }
                        Cancle();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.toast2_bottom(ApplicationInActivity.this, "图片压缩异常");
                        Cancle();
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        if (page == 0) {
            List<String> list = new ArrayList<>();
            list.add(path.replace("file:",""));
            Compress(list, 0);
//            logoFile = new File(path);
//            Glide.with(ApplicationInActivity.this).load(path).apply(options).into(shop_img);
        }
        if (page == 5) {
            UploadIcon(path);
        }
        if (page == 11) {
            List<String> list = new ArrayList<>();
            list.add(path.replace("file:",""));
            Compress(list, 11);
//            shop2File = new File(path);
//            Glide.with(ApplicationInActivity.this).load(path).apply(options).into(shop_img2);

        }

    }

    private void UploadIcon(final String path) {
        Show(ApplicationInActivity.this, "", true, null);
        if (!oldimgUrl.isEmpty() && shop_img.getVisibility() == View.VISIBLE) {
            DeleteImg(path);
        } else
            UpLoadImg(path);

    }

    private void DeleteImg(final String path) {

        /**
         * 删除旧图片后再上传店铺图片;
         */
        OkHttpUtils.get()
                .url(MzFinal.URL + MzFinal.APPDELETEAPPLYIMG)
                .addParams("url", oldimgUrl)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ApplicationInActivity.this, "网络不顺畅导致上传图片失败...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        UpLoadImg(path);
                    }
                });
    }


    private void UpLoadImg(final String path) {
        /**
         * 上传logo图片;
         */
        File file = new File(path);
        OkHttpUtils.post()
                .addFile("file", file.getName(), file)
                .url(MzFinal.URL + MzFinal.APPAPPLYIMG)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ApplicationInActivity.this, "网络不顺畅导致上传图片失败,...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {

                                oldimgUrl = getJsonSring(response);
                                Log.i(TAG, "oldimgUrloldimgUrl ========= " + oldimgUrl);
                                logo_img.setVisibility(View.VISIBLE);
                                Glide.with(ApplicationInActivity.this).load(path).centerCrop().into(logo_img);
                                Cancle();
                            } else
                                ToastUtil.ToastErrorMsg(ApplicationInActivity.this, response, code);
                        } catch (JSONException e) {
                            Cancle();
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(this, "店铺名称可用！！");
    }

    @Override
    public void onFailed() {
        ToastUtil.toast2_bottom(this, "店铺名称已被占用！！");
    }

    @Override
    public void onError() {
        ToastUtil.toast2_bottom(this, "网络异常！！");
    }

    @Override
    public void onElse(String content, int code) {
        try {
            ToastUtil.ToastErrorMsg(ApplicationInActivity.this, content, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
