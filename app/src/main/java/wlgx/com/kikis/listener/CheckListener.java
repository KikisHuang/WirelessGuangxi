package wlgx.com.kikis.listener;

/**
 * Created by lian on 2017/9/30.
 */
public interface CheckListener {
    void onSucceed();
    void onFailed();
    void onError();
    void onElse(String content,int code);
}
