package wlgx.com.kikis.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import wlgx.com.kikis.listener.GetFileListener;

/**
 * Created by lian on 2017/11/30.
 */
public class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {
    private Context context;
    private GetFileListener listener;
    private int tag;

    public getImageCacheAsyncTask(Context context, GetFileListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(String... params) {
        String imgUrl = params[0];
        this.tag = Integer.parseInt(params[1]);
        try {
            return Glide.with(context)
                    .load(imgUrl)
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        if (result == null) {
            return;
        }
        listener.onFile(result, tag);
    }
}
