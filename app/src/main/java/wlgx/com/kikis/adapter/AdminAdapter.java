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

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.AdminBean;
import wlgx.com.kikis.listener.addImgListener;
import wlgx.com.kikis.utils.OverallViewHolder;

/**
 * Created by Administrator on 2017/9/17.
 */

public class AdminAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private Context context;
    private addImgListener add;
    private List<AdminBean> data;

    public AdminAdapter(Context context, List<AdminBean> list, addImgListener add) {
        this.data = list;
        this.context = context.getApplicationContext();
        this.inflater = LayoutInflater.from(context);
        this.add = add;
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
            root = inflater.inflate(R.layout.admin_item, null);

        ImageView icon_img = OverallViewHolder.ViewHolder.get(root, R.id.icon_img);
        TextView name = OverallViewHolder.ViewHolder.get(root, R.id.name_tv);
        TextView phone = OverallViewHolder.ViewHolder.get(root, R.id.phone_tv);


        name.setText(data.get(position).getNickname());
        phone.setText(data.get(position).getPhone());

        Glide.with(context).load(R.mipmap.test_icon).bitmapTransform(new CropCircleTransformation(context)).into(icon_img);

        root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                add.onDelete(position);
                return false;
            }
        });
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.onChange(position);
            }
        });

        return root;
    }
}
