package wlgx.com.kikis.utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;

import wlgx.com.kikis.activity.IconCutActivity;

import static wlgx.com.kikis.utils.DeviceUtils.getAutoFileOrFilesSize;


/**
 * Created by lian on 2017/4/10.
 */
public class CutUtil {

    private static final String TAG = "CutUtil";

    public void CutBitmap(String urlSTR, Activity ac, String flag) {

        Bitmap bitmap = CompressIamge.getBitmapFromUri2(urlSTR, ac);
        LruCacheUtils lcu = new LruCacheUtils();
        lcu.addBitmapToMemoryCache(urlSTR, bitmap);

        Log.i(TAG, "未压缩的图片大小 ===" + getAutoFileOrFilesSize(urlSTR));

        Intent intent = new Intent(ac,IconCutActivity.class);
        intent.putExtra("Bitmap_key", urlSTR);
        intent.putExtra("flag", flag);
        ac.startActivity(intent);
        Log.i(TAG,"URL LOG ==========="+urlSTR);

    }
}
