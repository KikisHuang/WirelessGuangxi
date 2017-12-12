package wlgx.com.kikis.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.listener.GetFileListener;
import wlgx.com.kikis.listener.onPhotoCutListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.utils.getImageCacheAsyncTask;

import static wlgx.com.kikis.utils.IntentUtils.goUploadPhotoPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.JsonUtils.getKeyMap;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/11/30.
 */
public class QrCodeActivity extends InitActivity implements View.OnClickListener, onPhotoCutListener, GetFileListener {


    private static final String TAG = "QrCodeActivity";
    private ImageView alipay_img, wechatpay_img;
    private File WechatFile, AliFile;
    public static onPhotoCutListener listener;
    private GetFileListener fileListener;
    private TextView wechat_layout, alila_layout;

    @Override
    protected void click() {
        wechatpay_img.setOnClickListener(this);
        alipay_img.setOnClickListener(this);

        wechat_layout.setOnClickListener(this);
        alila_layout.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.qr_code_layout);
        wechatpay_img = f(R.id.wechatpay_img);
        alipay_img = f(R.id.alipay_img);
        wechat_layout = f(R.id.wechat_layout);
        alila_layout = f(R.id.alila_layout);
        setTitles(this, "二维码");
        listener = this;
        fileListener = this;
        CodeSelector(0);
    }

    private void CodeSelector(int i) {
        if (i == 0) {
            wechat_layout.setBackgroundResource(R.color.black994);
            wechat_layout.setTextColor(getResources().getColor(R.color.white));

            alila_layout.setBackgroundResource(R.color.black99);
            alila_layout.setTextColor(getResources().getColor(R.color.white));
            wechatpay_img.setVisibility(View.VISIBLE);
            alipay_img.setVisibility(View.GONE);
        } else {
            wechat_layout.setBackgroundResource(R.color.black99);
            wechat_layout.setTextColor(getResources().getColor(R.color.white));

            alila_layout.setBackgroundResource(R.color.black994);
            alila_layout.setTextColor(getResources().getColor(R.color.white));
            wechatpay_img.setVisibility(View.GONE);
            alipay_img.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        /**
         * 获取店详情;
         */
        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(QrCodeActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);

                                Log.i(TAG, " aliQr Code ==" + ob.optString("aliPayImg"));
                                Log.i(TAG, " wxQr Code ==" + ob.optString("wxPayImg"));

                                if (ob.isNull("aliPayImg") || ob.optString("aliPayImg").isEmpty())
                                    Glide.with(QrCodeActivity.this).load(R.mipmap.qe_code_add_icon).centerCrop().into(alipay_img);
                                else {
                                    Glide.with(QrCodeActivity.this).load(ob.optString("aliPayImg")).centerCrop().into(alipay_img);
                                    new getImageCacheAsyncTask(getApplicationContext(), fileListener).execute(ob.optString("aliPayImg"), "0");

                                }

                                if (ob.isNull("wxPayImg") || ob.optString("wxPayImg").isEmpty())
                                    Glide.with(QrCodeActivity.this).load(R.mipmap.qe_code_add_icon).centerCrop().into(wechatpay_img);
                                else {
                                    Glide.with(QrCodeActivity.this).load(ob.optString("wxPayImg")).centerCrop().into(wechatpay_img);
                                    new getImageCacheAsyncTask(getApplicationContext(), fileListener).execute(ob.optString("wxPayImg"), "1");
                                }


                            } else
                                ToastUtil.ToastErrorMsg(QrCodeActivity.this, response, code);
                        } catch (Exception e) {

                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alipay_img:
                goUploadPhotoPage(QrCodeActivity.this, "0", "998");
                break;
            case R.id.wechatpay_img:
                goUploadPhotoPage(QrCodeActivity.this, "0", "997");
                break;
            case R.id.wechat_layout:
                CodeSelector(0);
                break;
            case R.id.alila_layout:
                CodeSelector(1);
                break;
        }
    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        Compress(path, page);
    }

    /**
     * 压缩
     */
    private void Compress(String list, final int i) {
        Show(QrCodeActivity.this, "", true, null);
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

                        if (i == 998) {
                            AliFile = file;
                            Glide.with(QrCodeActivity.this).load(AliFile).centerCrop().into(alipay_img);
                            UpLoadQRCode();
                        }
                        if (i == 997) {
                            WechatFile = file;
                            Glide.with(QrCodeActivity.this).load(WechatFile).centerCrop().into(wechatpay_img);
                            UpLoadQRCode();
                        }
                        Cancle();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.toast2_bottom(QrCodeActivity.this, "图片压缩异常");
                        Cancle();
                    }
                }).launch();    //启动压缩
    }

    /**
     * 上传 微信、支付宝二维码;
     */
    private void UpLoadQRCode() {
        Map<String, String> map = getKeyMap();
        PostFormBuilder post = OkHttpUtils.post();
        post.url(MzFinal.URL + MzFinal.SHOPPAYQCIMG);
        post.params(map);
        if(WechatFile!=null)
        post.addFile("wxPayImg", WechatFile.getName(), WechatFile);
        if(AliFile!=null)
            post.addFile("aliPayImg", AliFile.getName(), AliFile);

        RequestCall build1 = post.build();
        build1.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.toast2_bottom(QrCodeActivity.this, "二维码上传失败，请重新上传。");
                Cancle();
            }

            @Override
            public void onResponse(String response, int id) {
                Log.i(TAG, "上传成功");
                Cancle();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Cancle();
        listener = null;
    }

    @Override
    public void onFile(File f, int tag) {
        if (tag == 0)
            AliFile = f;
        else
            WechatFile = f;
    }
}
