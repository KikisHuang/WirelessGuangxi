package wlgx.com.kikis.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.PhotoUtils.getMULTIPLEPhoto;
import static wlgx.com.kikis.utils.PhotoUtils.getMULTIPLEPhotoTag;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/10/25.
 */
public class ShopCheckActivity extends InitActivity implements View.OnClickListener {
    private static final String TAG = "ShopCheckActivity";
    private FrameLayout add_layout;
    private File file = null;
    private File front_file = null;
    private File verso_file = null;
    private ImageView check_img, add_img, id_front_img, id_verso_img;
    private RippleView add_bt;

    @Override
    protected void click() {
        add_layout.setOnClickListener(this);
        id_front_img.setOnClickListener(this);
        id_verso_img.setOnClickListener(this);
        add_bt.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.shop_check_layout);
        setTitles(this, "资质验证");
        add_layout = f(R.id.add_license_layout);
        check_img = f(R.id.license_img);
        add_img = f(R.id.license_add_img);
        add_bt = f(R.id.add_bt);
        id_front_img = f(R.id.id_front_img);
        id_verso_img = f(R.id.id_verso_img);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_license_layout:
                getMULTIPLEPhoto(this, 1);
                break;
            case R.id.add_bt:
                if (file != null && front_file != null && verso_file != null)
                    Commit();
                else if (file == null)
                    ToastUtil.toast2_bottom(this, "营业执照图片不能为空！！！");
                else if (front_file == null)
                    ToastUtil.toast2_bottom(this, "身份证正面不能为空！！！");
                else if (verso_file == null)
                    ToastUtil.toast2_bottom(this, "身份证反面不能为空！！！");
                break;

            case R.id.id_front_img:
                getMULTIPLEPhotoTag(this, 1, 487);
                break;
            case R.id.id_verso_img:
                getMULTIPLEPhotoTag(this, 1, 488);
                break;
        }
    }

    private void Commit() {
        Show(ShopCheckActivity.this, "提交中..", true, null);
        /**
         * 上传资质认证接口;
         */
        OkHttpUtils
                .post()
                .url(MzFinal.URL + MzFinal.SHOPAUTHENTICATION)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addFile("file1", file.getName(), file)
                .addFile("file2", front_file.getName(), front_file)
                .addFile("file3", verso_file.getName(), verso_file)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "Error ===" + e);
                        ToastUtil.toast2_bottom(ShopCheckActivity.this, "网络不顺畅...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            Log.i(TAG, " response  ===" + response);
                            if (code == 1) {
                                ToastUtil.toast2_bottom(ShopCheckActivity.this, "已提交资质认证！！");
                                finish();
                            } else
                                ToastUtil.ToastErrorMsg(ShopCheckActivity.this, response, code);
                        } catch (Exception e) {
                            Cancle();
                        }
                        Cancle();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    ArrayList<String> path = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Compress(path, 1);
                    break;
                case 487:
                    ArrayList<String> f = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Compress(f, 2);
                    break;
                case 488:
                    ArrayList<String> v = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    Compress(v, 3);
                    break;
            }
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
                    public void onSuccess(File f) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                        subscriber.onNext(file.getAbsolutePath());
                        Log.i(TAG, "onSuccess" + f.getAbsolutePath());
                        switch (i) {
                            case 1:
                                file = f;
                                Glide.with(ShopCheckActivity.this).load(file).centerCrop().into(check_img);
                                break;
                            case 2:
                                front_file = f;
                                Glide.with(ShopCheckActivity.this).load(front_file).centerCrop().into(id_front_img);
                                break;
                            case 3:
                                verso_file = f;
                                Glide.with(ShopCheckActivity.this).load(verso_file).centerCrop().into(id_verso_img);
                                break;

                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.toast2_bottom(ShopCheckActivity.this, "图片压缩异常...");
                    }
                }).launch();    //启动压缩
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
    }
}
