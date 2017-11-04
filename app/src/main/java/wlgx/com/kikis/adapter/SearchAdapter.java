package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.AddressBean;
import wlgx.com.kikis.listener.moveListener;
import wlgx.com.kikis.utils.OverallViewHolder;

/**
 * Created by lian on 2017/9/16.
 */
public class SearchAdapter extends BaseAdapter {
    private List<AddressBean> data;
    private LayoutInflater li;
    private moveListener move;

    public SearchAdapter(Context context, moveListener move) {
        this.move = move;
        li = LayoutInflater.from(context);
    }

    /**
     * 设置数据集
     *
     * @param data
     */
    public void setData(List<AddressBean> data) {
        this.data = data;
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
            root = li.inflate(R.layout.address_item, null);

        LinearLayout move_layout = OverallViewHolder.ViewHolder.get(root, R.id.move_layout);
        TextView title = OverallViewHolder.ViewHolder.get(root, R.id.item_title);
        TextView text = OverallViewHolder.ViewHolder.get(root, R.id.item_text);

        title.setText(data.get(position).getTitle());
        text.setText(data.get(position).getText());
        move_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move.onMove(data.get(position).getLatitude(), data.get(position).getLongitude(), data.get(position).getTitle());
            }
        });
        return root;
    }
}

