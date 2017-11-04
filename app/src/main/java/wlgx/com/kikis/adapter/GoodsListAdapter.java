package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.GoodsBean;
import wlgx.com.kikis.utils.OverallViewHolder;

import static wlgx.com.kikis.utils.DateUtils.getDate;


/**
 * Created by Administrator on 2017/9/17.
 */

public class GoodsListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private List<GoodsBean> data;
    private int tag = 0;

    public GoodsListAdapter(Context context, List<GoodsBean> list, int tag) {
        this.data = list;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.tag = tag;
    }

    @Override
    public int getCount() {
        return data.size();
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

        //自定义侧滑删除view
        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.goods_list_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(root, R.id.icon_img);

        TextView title_tv = OverallViewHolder.ViewHolder.get(root, R.id.title_tv);

        TextView inventory_tv = OverallViewHolder.ViewHolder.get(root, R.id.inventory_tv);
        TextView price_tv = OverallViewHolder.ViewHolder.get(root, R.id.price_tv);
        TextView sell_tv = OverallViewHolder.ViewHolder.get(root, R.id.sell_tv);
        TextView date_tv = OverallViewHolder.ViewHolder.get(root, R.id.date_tv);

        Glide.with(context).load(data.get(position).getLogoUrl()).centerCrop().into(icon_img);

        title_tv.setText(data.get(position).getTitle());
        price_tv.setText("￥" + String.valueOf(data.get(position).getPrice()));
        sell_tv.setText("销售：" + data.get(position).getSellCount());
        inventory_tv.setText("库存：" + data.get(position).getStock());
        date_tv.setText("日期：" + getDate(data.get(position).getCreateTime()));
        root.setVisibility(View.VISIBLE);

        return root;
    }
}
