package wlgx.com.kikis.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.CouponBean;
import wlgx.com.kikis.listener.ItemClickListener;
import wlgx.com.kikis.utils.DeviceUtils;

import static wlgx.com.kikis.utils.DateUtils.getDate;


/**
 * Created by wiky on 2015/12/25.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    private static final String TAG = "CouponAdapter";
    private Context context;
    private List<CouponBean> list;
    private ItemClickListener listener;

    public CouponAdapter(Context context, List<CouponBean> list, ItemClickListener listener) {
        this.context = context.getApplicationContext();
        this.list = list;
        this.listener = listener;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, anda
    // you provide access to all the views for a data item in a view holder
    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView condition_tv, type_tv, end_time_tv, status_tv, money_tv;
        private RelativeLayout coupon_layout;
        private ImageView delete;
        private CardView card_layout;

        private ViewHolder(View v) {
            super(v);
            condition_tv = (TextView) v.findViewById(R.id.condition_tv);
            type_tv = (TextView) v.findViewById(R.id.type_tv);
            status_tv = (TextView) v.findViewById(R.id.status_tv);
            end_time_tv = (TextView) v.findViewById(R.id.end_time_tv);
            money_tv = (TextView) v.findViewById(R.id.money_tv);
            delete = (ImageView) v.findViewById(R.id.delete_img);
            card_layout = (CardView) v.findViewById(R.id.card_layout);
            coupon_layout = (RelativeLayout) v.findViewById(R.id.coupon_layout);

        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.coupon_item, parent, false);
        parent.setBackgroundColor(Color.TRANSPARENT);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        //低版本Cardview 适配;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            int w = (int) (DeviceUtils.getWindowWidth(context) * 5 / 10);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(w, w);
            holder.card_layout.setLayoutParams(lp);
        }

        holder.condition_tv.setText("购满" + list.get(position).getTriggerMoney() + "元使用");
        String type = "全部";
        if (list.get(position).getGoodsType() != null)
            holder.type_tv.setText("可使用分类:" + list.get(position).getGoodsType().getTitle());
        else
            holder.type_tv.setText("可使用分类:" + type);

        holder.end_time_tv.setText("截止日期：" + getDate(list.get(position).getEndTime()));

        switch (list.get(position).getStatus()) {
            case 0:
                holder.status_tv.setText("未上架");
                break;
            case 1:
                holder.status_tv.setText("已上架");
                break;
            case -1:
                holder.status_tv.setText("已下架");
                break;
        }
//        if((int) list.get(position).getPrice()>100)
//            holder.money_tv.setTextSize();
        holder.money_tv.setText(String.valueOf((int) list.get(position).getPrice()));

        if (position % 2 == 0)
            holder.coupon_layout.setBackgroundResource(R.drawable.coupon_shape_blue);
        else
            holder.coupon_layout.setBackgroundResource(R.drawable.coupon_shape_violet);

        holder.coupon_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position, list.get(position).getId(), "");
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(10086, list.get(position).getId(), "");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

}
