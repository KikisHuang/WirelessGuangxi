package wlgx.com.kikis.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import wlgx.com.kikis.R;

/**
 * Created by lian on 2017/10/20.
 */
public class TestActivity extends InitActivity {
    private static final String TAG = "TestActivity";

    private Button button;

    @Override
    protected void click() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Document doc = Jsoup.parse("<p>&nbsp;</p>\n" +
                        "<p>护肤用品</p>\n" +
                        "<p><img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i2/2029314557/TB2vsBCwl0lpuFjSszdXXcdxFXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" />" + "<p>测试文字</p>\n" + "<img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i4/2029314557/TB2pkjgwmFjpuFjSszhXXaBuVXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" /><img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i2/2029314557/TB2ayKcwgFkpuFjSspnXXb4qFXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" /><img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i2/2029314557/TB2m5KcwgFkpuFjSspnXXb4qFXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" /><img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i1/2029314557/TB2FHmGwgJlpuFjSspjXXcT.pXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" /><img class=\"img-ks-lazyload\" \n" +
                        "src=\"https://img.alicdn.com/imgextra/i4/2029314557/TB24bbdbbH9F1JjSZFAXXba3VXa_!!2029314557.jpg\" \n" +
                        "align=\"absmiddle\" /></p>");

                Log.i(TAG, "text ==" + doc.text());
                Log.i(TAG, "body ==" + doc.select("img[src]"));
            }
        });
    }

    @Override
    protected void init() {
        setContentView(R.layout.test_layout);
        button = f(R.id.button);
    }

    @Override
    protected void initData() {

    }
}
