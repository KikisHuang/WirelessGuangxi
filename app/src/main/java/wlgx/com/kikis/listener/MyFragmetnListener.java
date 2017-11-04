package wlgx.com.kikis.listener;

/**
 * Created by lian on 2017/9/11.
 */
public interface MyFragmetnListener {
    void onUpdata();

    /**
     * int 0 (登录过期重新登录);
     *
     * @param f
     */
    void onHide(int f);
}
