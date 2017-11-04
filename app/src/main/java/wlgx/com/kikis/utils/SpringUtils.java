package wlgx.com.kikis.utils;

import android.app.Activity;
import android.os.Handler;

import com.liaoinstan.springview.container.MeituanFooter;
import com.liaoinstan.springview.container.MeituanHeader;
import com.liaoinstan.springview.widget.SpringView;

import wlgx.com.kikis.listener.SpringListener;


/**
 * Created by lian on 2017/5/26.
 * 刷新控件初始化;
 */
public class SpringUtils {

    public static void SpringViewInit(final SpringView springView, final Activity ac, final SpringListener springListener, int color) {
        springView.setType(SpringView.Type.FOLLOW);
        springView.setHeader(new MeituanHeader(ac, MzFinal.pullAnimSrcs, MzFinal.refreshAnimSrcs));
        springView.setFooter(new MeituanFooter(ac, MzFinal.loadingAnimSrcs));
        springView.setBackgroundColor(color);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonRefresh(0);
                    }
                }, 500);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //结束刷新动画;
                        springView.onFinishFreshAndLoad();
                        springListener.IsonLoadmore(20);
                    }
                }, 500);
            }
        });
    }
}
