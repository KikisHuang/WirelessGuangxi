package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.McOrderGoodsMappingsBean;
import wlgx.com.kikis.bean.OrderBean;
import wlgx.com.kikis.listener.OrderListener;
import wlgx.com.kikis.utils.OverallViewHolder;

import static wlgx.com.kikis.utils.StringUtil.checkNull;


/**
 * Created by lian on 2017/5/18.
 */
public class OrderAdapter extends BaseAdapter {
    private static final String TAG = "OrderAdapter";
    private Context context;
    private List<OrderBean> blist;
    private LayoutInflater inflater;
    private int tag;
    private OrderListener olistener;

    public OrderAdapter(Context context, List<OrderBean> blist, int tag, OrderListener olistener) {
        this.blist = blist;
        this.tag = tag;
        this.olistener = olistener;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return blist.size();
    }

    @Override
    public Object getItem(int position) {
        return blist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.order_item, null);

        LinearLayout goods_info_ll = OverallViewHolder.ViewHolder.get(root, R.id.goods_info_ll);
        TextView goods_title = OverallViewHolder.ViewHolder.get(root, R.id.goods_title);
        TextView goods_status = OverallViewHolder.ViewHolder.get(root, R.id.goods_status);
        Button goods_button = OverallViewHolder.ViewHolder.get(root, R.id.goods_button);
        TextView user_name = OverallViewHolder.ViewHolder.get(root, R.id.user_name);
        TextView user_contact = OverallViewHolder.ViewHolder.get(root, R.id.user_contact);
        TextView user_address = OverallViewHolder.ViewHolder.get(root, R.id.user_address);
        TextView order_time = OverallViewHolder.ViewHolder.get(root, R.id.order_time);

        TextView order_tv = OverallViewHolder.ViewHolder.get(root, R.id.order_tv);

        order_tv.setText("订单编号：" + checkNull(blist.get(position).getId()));
        user_name.setText("姓名：" + checkNull(blist.get(position).getAddressInfoName()));
        user_contact.setText("联系方式：" + checkNull(blist.get(position).getAddressInfoPhone()));
        user_address.setText("详细地址：" + checkNull(blist.get(position).getAddressInfo()));
        order_time.setText("预约时间：" + checkNull(blist.get(position).getDeliveryTime()));

        List<McOrderGoodsMappingsBean> mc = blist.get(position).getMcOrderGoodsMappings();

        goods_title.setText(mc.get(0).getGoods().getShop().getName());
        switch (blist.get(position).getStatus()) {
            case 0:
                goods_button.setText("查看");
                goods_status.setText("未支付");
                goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 1);
                break;
            case 1:
                goods_button.setText("查看");
                if(tag==3){
                    goods_status.setText("待退款");
                    goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
                }else {
                    goods_status.setText("已支付");
                    goods_status.setTextColor(context.getResources().getColor(R.color.green5));
                }
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
            case 2:
                goods_button.setText("查看");
                goods_status.setText("申请退款");
//                goGoodsDetails(goods_button, blist.get(position).getId(), 2);
                break;
            case 10:
                goods_button.setText("查看");
                goods_status.setText("已接单");
                goods_status.setTextColor(context.getResources().getColor(R.color.red3));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
            case -20:
                goods_button.setText("查看");
                goods_status.setText("已退款");
                goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
            case -10:
                goods_button.setText("查看");
                goods_status.setText("已取消");
                goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
            case -1:
                goods_button.setText("查看");
                goods_status.setText("支付失败");
                goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
            case -2:
                goods_button.setText("查看");
                goods_status.setText("校验失败");
                goods_status.setTextColor(context.getResources().getColor(R.color.gray4));
//                goGoodsDetails(goods_button, blist.get(position).getId(), 0);
                break;
        }

        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 3)
                    olistener.onRefund(blist.get(position).getId());
                else
                    olistener.onDetails(blist.get(position).getId());
            }
        });
        goods_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tag == 3)
                    olistener.onRefund(blist.get(position).getId());
                else
                    olistener.onDetails(blist.get(position).getId());
            }
        });
        return root;
    }


    /**
     * 跳转订单详情、退款、付款;
     *
     * @param goods_button
     * @param id
     */
    private void goGoodsDetails(Button goods_button, final String id, final int type) {
        goods_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (type) {
                    case 0:
                        olistener.onDetails(id);
                        break;
                    case 1:
                        olistener.onDetails(id);
                        break;
                    case 2:
                        olistener.onDetails(id);
                        break;
                }

            }
        });

    }
}
