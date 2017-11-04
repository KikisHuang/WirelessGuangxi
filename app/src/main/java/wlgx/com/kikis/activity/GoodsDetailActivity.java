package wlgx.com.kikis.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sendtion.xrichtext.RichTextEditor;
import com.sendtion.xrichtext.SDCardUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import okhttp3.Call;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.listener.CompressListener;
import wlgx.com.kikis.listener.addImgListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.StringUtils;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.PhotoUtils.getMULTIPLEPhoto;

/**
 * Created by lian on 2017/10/18.
 */
public class GoodsDetailActivity extends InitActivity implements View.OnClickListener, CompressListener, addImgListener {

    private static final String TAG = "GoodsDetailActivity";
    private String content;
    private RippleView check_shop_name;
    private TextView add_tv;

    private RichTextEditor et_new_content;
    private ProgressDialog loadingDialog;
    private ProgressDialog insertDialog;

    private Subscription subsLoading;
    private Subscription subsInsert;
    private int num = 0;
    private List<String> photos;
    public static addImgListener listener;
    private List<String> imgList;
    private int flag = 9;

    protected void click() {
        check_shop_name.setOnClickListener(this);
        add_tv.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.goods_detail_layout);
        check_shop_name = f(R.id.check_shop_name);
        add_tv = f(R.id.add_tv);
        listener = this;
        et_new_content = (RichTextEditor) findViewById(R.id.et_new_content);

        flag = Integer.parseInt(getIntent().getStringExtra("goods_content_flag"));
        content = getIntent().getStringExtra("goods_content");

        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

        loadingDialog = new ProgressDialog(this);
        loadingDialog.setMessage("图片解析中...");
        loadingDialog.setCanceledOnTouchOutside(false);
        photos = new ArrayList<>();
        imgList = new ArrayList<>();

    }

    @Override
    protected void initData() {
        if (flag == 1) {
            et_new_content.post(new Runnable() {
                @Override
                public void run() {
                    //showEditData(note.getContent());
                    et_new_content.clearAllLayout();
                    showDataSync(content);
                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PhotoPicker.REQUEST_CODE:
                    photos.clear();
                    imgList.clear();
                    ArrayList<String> p = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                    num = 0;
                    num = p.size();
                    Log.i(TAG, "size ====" + num);
                    Compress(p);
                    break;
            }
        }
    }

    /**
     * 异步方式插入图片
     *
     * @param data
     */
    private void insertImagesSync(final List<String> data) {
        insertDialog.show();

        subsInsert = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    et_new_content.measure(0, 0);
                    //可以同时插入多张图片
                    for (String imagePath : data) {
                        subscriber.onNext(imagePath);
                    }
                    subscriber.onCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        insertDialog.dismiss();
                        et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), " ");
                        ToastUtil.toast2_bottom(GoodsDetailActivity.this, "图片插入成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        insertDialog.dismiss();
                        ToastUtil.toast2_bottom(GoodsDetailActivity.this, "图片插入失败:" + e.getMessage());
                    }

                    @Override
                    public void onNext(String imagePath) {
                        et_new_content.insertImage(imagePath, et_new_content.getMeasuredWidth());
                    }
                });
    }


    /**
     * 异步方式显示数据
     */
    private void showDataSync(final String html) {

        Log.i(TAG, "html ======" + html);
        loadingDialog.show();

        subsLoading = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                showEditData(subscriber, html);
            }
        })
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadingDialog.dismiss();
                        ToastUtil.toast2_bottom(GoodsDetailActivity.this, "解析错误：图片不存在或已损坏");
                    }

                    @Override
                    public void onNext(String text) {
                        if (text.contains(SDCardUtil.getPictureDir())) {
                            et_new_content.addImageViewAtIndex(et_new_content.getLastIndex(), text);
                        } else {
                            et_new_content.addEditTextAtIndex(et_new_content.getLastIndex(), text);
                        }
                    }
                });
    }

    /**
     * 显示数据
     */
    protected void showEditData(Subscriber<? super String> subscriber, String html) {
        try {
            List<String> textList = StringUtils.cutStringByImgTag(html);
            for (int i = 0; i < textList.size(); i++) {
                String text = textList.get(i);
                if (text.contains("<img")) {
                    String imagePath = StringUtils.getImgSrc(text);
                    if (new File(imagePath).exists()) {
                        subscriber.onNext(imagePath);
                    } else {
                        ToastUtil.toast2_bottom(GoodsDetailActivity.this, "图片" + i + "已丢失，请重新插入！");
                    }
                } else {
                    subscriber.onNext(text);
                }

            }
            subscriber.onCompleted();
        } catch (Exception e) {
            e.printStackTrace();
            subscriber.onError(e);
        }
    }

    /**
     * 压缩
     */
    private void Compress(List<String> list) {
        Luban.with(this)
                .load(list)                                   // 传人要压缩的图片列表
                .ignoreBy(500)                               // 忽略不压缩图片的大小
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
                        photos.add(file.getAbsolutePath());
                        num--;
                        if (num == 0)
                            onComSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        onComonError();
                    }
                }).launch();    //启动压缩
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.check_shop_name:
                Log.i(TAG, " Content =====" + getEditData());
                Intent intent = new Intent();
                intent.putExtra("goods_content", getEditData());
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.add_tv:
                getMULTIPLEPhoto(GoodsDetailActivity.this, 5);
                break;
        }
    }

    /**
     * 负责处理编辑数据提交等事宜，请自行实现
     */
    private String getEditData() {
        List<RichTextEditor.EditData> editList = et_new_content.buildEditData();
        StringBuffer content = new StringBuffer();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null && !itemData.inputStr.isEmpty()) {
                content.append("<p>" + itemData.inputStr + "</p>");
                //Log.d("RichEditor", "commit inputStr=" + itemData.inputStr);
            } else if (itemData.imagePath != null) {
                content.append("<img src=\"").append(itemData.imagePath).append("\"/>");
                //Log.d("RichEditor", "commit imgePath=" + itemData.imagePath);
                //imageList.add(itemData.imagePath);
            }
        }
        return content.toString();
    }

    @Override
    public void onComSuccess() {
        for (int i = 0; i < photos.size(); i++) {

            File file = new File(photos.get(i));
            /**
             * 上传图片;
             */
            final int finalI = i;
            OkHttpUtils
                    .post()
                    .url(MzFinal.URL + MzFinal.UPLOADIMG)
                    .addParams(MzFinal.KEY, SPreferences.getUserToken())
                    .addFile("file", file.getName(), file)
                    .tag(this)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            ToastUtil.toast2_bottom(GoodsDetailActivity.this, "网络不顺畅...");
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            try {
                                JSONObject ob = new JSONObject(response);
                                imgList.add(ob.optString("location"));
                                Log.i(TAG, "response ====" + response);
                                if (finalI + 1 == photos.size())
                                    insertImagesSync(imgList);

                            } catch (Exception e) {

                            }
                        }
                    });
        }
    }

    @Override
    public void onComonError() {
        ToastUtil.toast2_bottom(this, "发生了未知错误...");
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onChange(int pos) {

    }

    @Override
    public void onDelete(int pos) {

    }
}
