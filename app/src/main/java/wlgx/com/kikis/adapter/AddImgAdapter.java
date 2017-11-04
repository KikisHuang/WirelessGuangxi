package wlgx.com.kikis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import wlgx.com.kikis.R;
import wlgx.com.kikis.bean.shopImgs;
import wlgx.com.kikis.listener.addImgListener;
import wlgx.com.kikis.utils.OverallViewHolder;

/**
 * Created by Administrator on 2017/9/17.
 */

public class AddImgAdapter extends BaseAdapter {
    private static final String TAG = "AddImgAdapter";
    private LayoutInflater inflater;
    private Context context;
    private addImgListener add;
    private List<shopImgs> data;

    public AddImgAdapter(Context context, List<shopImgs> list, addImgListener add) {
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

        View root = convertView;
        if (root == null)
            root = inflater.inflate(R.layout.add_img_item, null);
        ImageView delete = OverallViewHolder.ViewHolder.get(root, R.id.delete_img);
        ImageView img = OverallViewHolder.ViewHolder.get(root, R.id.img);


        if (position == 0) {
            delete.setVisibility(View.GONE);
            Glide.with(context).load(R.mipmap.add_icon).into(img);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onAdd();
                }
            });

        } else {
            if (data.get(position).getId().equals("gongdelete")){
                Glide.with(context).load(R.mipmap.null_icon).into(img);
                delete.setVisibility(View.GONE);
            } else{
                Glide.with(context).load(data.get(position).getImgUrl()).into(img);
                delete.setVisibility(View.VISIBLE);
            }

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onDelete(position);
                }
            });

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    add.onChange(position);
                }
            });
        }

        return root;
    }

}
