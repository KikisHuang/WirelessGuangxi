package wlgx.com.kikis.listener;


import wlgx.com.kikis.bean.VersionBean;

/**
 * Created by lian on 2017/7/7.
 */
public interface VersionCheckListener {
    void onVersion(VersionBean vb);
    void onFail();
}
