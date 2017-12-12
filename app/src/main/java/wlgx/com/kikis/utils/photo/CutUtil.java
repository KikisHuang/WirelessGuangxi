package wlgx.com.kikis.utils.photo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;

import wlgx.com.kikis.R;
import wlgx.com.kikis.activity.IconCutActivity;

import static android.support.v4.content.FileProvider.getUriForFile;
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

        Intent intent = new Intent(ac, IconCutActivity.class);
        intent.putExtra("Bitmap_key", urlSTR);
        intent.putExtra("flag", flag);
        ac.startActivity(intent);
        Log.i(TAG, "URL LOG ===========" + urlSTR);

    }

    public void CutBackBitmap(String urlSTR, Activity ac, String flag) {
        Uri sourceUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File imagePath = new File(urlSTR);
            sourceUri = getUriForFile(ac, ac.getPackageName() + ".fileprovider", imagePath);
        } else
            sourceUri = Uri.parse("file://" + urlSTR);

        Log.i(TAG, "uri ===== " + sourceUri);
        //裁剪后保存到文件中
        Uri destinationUri = Uri.fromFile(new File(FileManager.getSaveFilePath() + "copy/", System.currentTimeMillis() + ".jpeg"));
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();

        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.SCALE, UCropActivity.SCALE);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(ac, R.color.black99));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        //设置裁剪质量;
        options.setCompressionQuality(80);
        //toolbar标题;
        options.setToolbarTitle("门店图裁剪");

        UCrop.of(sourceUri, destinationUri).withOptions(options).withAspectRatio(1f, 0.66f).start(ac);

    }
}
