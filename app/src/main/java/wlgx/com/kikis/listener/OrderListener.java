package wlgx.com.kikis.listener;

/**
 * Created by lian on 2017/9/11.
 */
public interface OrderListener {
    void onDetails(String id);
    void onPayment(String id);
    void onRefund(String id);
}
