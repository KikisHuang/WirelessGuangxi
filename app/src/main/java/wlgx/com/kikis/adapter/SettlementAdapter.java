package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.SettlementBean;
import wlgx.com.kikis.utils.OverallViewHolder;

/**
 * Created by lian on 2017/9/16.
 */
public class SettlementAdapter extends BaseAdapter {
    private List<SettlementBean> data;
    private LayoutInflater li;
    private Context context;
    //0结算、1对账;
    private int tag;

    public SettlementAdapter(Context context, List<SettlementBean> list, int tag) {
        this.context = context;
        li = LayoutInflater.from(context);
        this.tag = tag;
        this.data = list;
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
            root = li.inflate(R.layout.settlement_item, null);

        TextView title = OverallViewHolder.ViewHolder.get(root, R.id.item_title);
        TextView time = OverallViewHolder.ViewHolder.get(root, R.id.time_tv);
        TextView expend_tv = OverallViewHolder.ViewHolder.get(root, R.id.expend_tv);

        switch (tag) {
            case 0:
                title.setText(data.get(position).getAdminName());
                expend_tv.setText("+" + data.get(position).getSettlementPrice());
                expend_tv.setTextColor(context.getResources().getColor(R.color.green5));
                time.setText(data.get(position).getCreateTime());
                break;
            case 1:
                switch (data.get(position).getPayType()) {
                    case 0:
                        title.setText("微信");
                        break;
                    case 1:
                        title.setText("支付宝");
                        break;
                    case 2:
                        title.setText("小程序");
                        break;
                }

                time.setText(data.get(position).getPayTime());
                expend_tv.setText("+" + data.get(position).getTotalFee());
                expend_tv.setTextColor(context.getResources().getColor(R.color.green5));

                break;

        }


        return root;
    }
}

