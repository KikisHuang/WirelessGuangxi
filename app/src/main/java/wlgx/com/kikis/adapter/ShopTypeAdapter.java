package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.shopTypeBean;
import wlgx.com.kikis.listener.ShopTypeListener;
import wlgx.com.kikis.utils.OverallViewHolder;

/**
 * Created by lian on 2017/9/16.
 */
public class ShopTypeAdapter extends BaseAdapter {
    private List<shopTypeBean> data;
    private LayoutInflater li;
    private ShopTypeListener listener;
    private boolean flag;

    public ShopTypeAdapter(Context context, List<shopTypeBean> data, ShopTypeListener listener, boolean flag) {
        this.data = data;
        this.flag = flag;
        this.listener = listener;
        li = LayoutInflater.from(context);

    }


    @Override
    public int getCount() {
        return (data == null) ? 0 : data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View root = convertView;
        if (root == null)
            root = li.inflate(R.layout.shop_type_item, null);

        final TextView title = OverallViewHolder.ViewHolder.get(root, R.id.server_tv);
        title.setText(data.get(position).getTitle());

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag)
                    listener.onMainMenuClick(data.get(position).getId(),title,position);
                else
                    listener.onSonMenuClick(data.get(position).getTitle(),data.get(position).getId());
            }
        });
        return root;
    }

}
