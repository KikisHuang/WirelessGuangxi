package wlgx.com.kikis.activity;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import wlgx.com.kikis.R;
import wlgx.com.kikis.utils.cut.ClipImageLayout;
import wlgx.com.kikis.utils.photo.CompressIamge;
import wlgx.com.kikis.utils.photo.LruCacheUtils;

import static wlgx.com.kikis.utils.SynUtils.Finish;


/**
 * Created by lian on 2017/6/12.
 */
public class IconCutActivity extends InitActivity {
    private static final String TAG = "IconCutActivity";
    private ClipImageLayout mClipImageLayout;
    private TextView Determine, Cancle;
    private String flag = "";

    private void BitmapSetting() {
        try {
            mClipImageLayout.SetBitmap(getBitmap());
        } catch (Exception e) {
            Log.e(TAG, "异常抛出.." + e);

        }
    }

    @Override
    protected void click() {
        Determine.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Bitmap bitmap = mClipImageLayout.clip();
                String url = CompressIamge.saveToFile(bitmap);

                if (!url.isEmpty() && bitmap != null) {
                    Log.i(TAG, "裁剪的图片保存至本地路径 ===" + url);
                    if (flag.equals("info")) {
                        UploadPhotoActivity.listener.onSucceed(url, bitmap);
                    }
                } else {
                    UploadPhotoActivity.listener.onFail();
                }

                Finish(IconCutActivity.this);
            }
        });

        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Finish(IconCutActivity.this);
            }
        });

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (UploadPhotoActivity.dlistener != null)
            UploadPhotoActivity.dlistener.onMyDestroy(0);
    }
    @Override
    protected void init() {
        setContentView(R.layout.iconcut_activity);
        mClipImageLayout = f(R.id.id_clipImageLayout);
        Determine = f(R.id.Determine);
        Cancle = f(R.id.Cancle);
        flag = getFlag();
    }

    @Override
    protected void initData() {
        BitmapSetting();
    }

    private Bitmap getBitmap() throws Exception {
        LruCacheUtils lcu = new LruCacheUtils();
        Log.i(TAG, "Bitmap_key ========" + getIntent().getStringExtra("Bitmap_key"));
        return lcu.getBitmapFromMemCache(getIntent().getStringExtra("Bitmap_key"));
    }

    public String getFlag() {
        return getIntent().getStringExtra("flag");
    }
}
