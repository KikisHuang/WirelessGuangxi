package wlgx.com.kikis.listener;

import android.widget.TextView;

/**
 * Created by lian on 2017/9/18.
 */
public interface ShopTypeListener {
    void onMainMenuClick(String id, TextView title, int position);
    void onSonMenuClick(String title, String typeId);
}
