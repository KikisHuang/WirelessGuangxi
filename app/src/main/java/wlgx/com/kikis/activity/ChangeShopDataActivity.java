package wlgx.com.kikis.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.http.okhttp.request.RequestCall;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
import wlgx.com.kikis.R;
import wlgx.com.kikis.adapter.AddImgAdapter;
import wlgx.com.kikis.bean.BankBean;
import wlgx.com.kikis.bean.shopImgs;
import wlgx.com.kikis.listener.CheckListener;
import wlgx.com.kikis.listener.addImgListener;
import wlgx.com.kikis.listener.onPhotoCutListener;
import wlgx.com.kikis.save.SPreferences;
import wlgx.com.kikis.utils.MzFinal;
import wlgx.com.kikis.utils.ToastUtil;
import wlgx.com.kikis.view.ActionSheetDialog;
import wlgx.com.kikis.view.CustomGridView;
import wlgx.com.kikis.view.RippleView;

import static wlgx.com.kikis.utils.CheckUtils.CheckShopName;
import static wlgx.com.kikis.utils.IntentUtils.goLocationPage;
import static wlgx.com.kikis.utils.IntentUtils.goShopTypePage;
import static wlgx.com.kikis.utils.IntentUtils.goUploadPhotoPage;
import static wlgx.com.kikis.utils.JsonUtils.getCode;
import static wlgx.com.kikis.utils.JsonUtils.getJsonOb;
import static wlgx.com.kikis.utils.StringUtil.checkNull;
import static wlgx.com.kikis.utils.SynUtils.Finish;
import static wlgx.com.kikis.utils.SynUtils.setTitles;
import static wlgx.com.kikis.view.CustomProgress.Cancle;
import static wlgx.com.kikis.view.CustomProgress.Show;

/**
 * Created by lian on 2017/9/16.
 */
public class ChangeShopDataActivity extends InitActivity implements View.OnClickListener, onPhotoCutListener, addImgListener, CheckListener {
    private static final String TAG = "ChangeShopDataActivity";

    private Spinner start_sp, end_sp;
    private List<String> time_list;
    private ArrayAdapter<String> arr_adapter;

    private String startTime = "";
    private String endTime = "";

    private LinearLayout shop_name_layout, shop_type_layout, shop_localtion_layout, shop_address_layout1, shop_time_average_layout, shop_phone_layout, shop_info_layout, shop_average_layout, shop_describe_layout, shop_time_layout;
    private LinearLayout succeed_layout;
    private ScrollView scrollView;
    private TextView name_tv, type_tv, info_tv, address_tv, address_tv1, time_average_tv, phone_tv, average_tv, describe_tv;
    private EditText name_ed, info_ed, type_ed, address_ed, address_ed1, time_average_ed, phone_ed, average_ed, describe_ed;
    private String typeId = "";
    private RippleView commit, check_shop_name;
    public static onPhotoCutListener listener;
    private FrameLayout logo_layout;
    //    private FrameLayout  back_layout;
    private String adcode = "";
    private String cityId = "";
    private String provinceId = "";
    private String latitude = "";
    private String longitude = "";
    private ImageView back_img, location_img;
    private File logoFile = null;
    private CircleImageView logo_img;
    private CustomGridView gridView;
    private List<shopImgs> list;
    private List<shopImgs> oldImg;
    private AddImgAdapter adapter;
    private addImgListener add;

    private int OldPos = 0;

    @Override
    protected void click() {
        shop_localtion_layout.setOnClickListener(this);
        shop_type_layout.setOnClickListener(this);
        address_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goLocationPage(ChangeShopDataActivity.this);
            }
        });
        type_ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goShopTypePage(ChangeShopDataActivity.this);
            }
        });
//        logo_layout.setOnClickListener(this);
        logo_img.setOnClickListener(this);
        commit.setOnClickListener(this);
        check_shop_name.setOnClickListener(this);
    }

    @Override
    protected void init() {
        setContentView(R.layout.change_shop_layout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        time_list = new ArrayList<>();
        oldImg = new ArrayList<>();

        listener = this;
        start_sp = f(R.id.start_time);
        end_sp = f(R.id.end_time);
        logo_img = f(R.id.logo_img);
        shop_time_layout = f(R.id.shop_time_layout);
        succeed_layout = f(R.id.succeed_layout);
        succeed_layout.setVisibility(View.GONE);
        scrollView = f(R.id.scrollView);
        check_shop_name = f(R.id.check_shop_name);

        TextView tv = (TextView) succeed_layout.findViewById(R.id.details_tv);
        tv.setText("商铺资料修改无需审核。");
        shop_name_layout = f(R.id.shop_name_layout);
        commit = f(R.id.submit_info);
        shop_type_layout = f(R.id.shop_type_layout);
        shop_localtion_layout = f(R.id.shop_localtion_layout);
        shop_address_layout1 = f(R.id.shop_address_layout1);
        shop_info_layout = f(R.id.shop_info_layout);
        shop_time_average_layout = f(R.id.shop_time_average_layout);
        shop_phone_layout = f(R.id.shop_phone_layout);
        shop_average_layout = f(R.id.shop_average_layout);
        shop_describe_layout = f(R.id.shop_describe_layout);

//        logo_layout = f(R.id.logo_layout);
//        logo_layout.setVisibility(View.GONE);
        name_tv = (TextView) shop_name_layout.findViewById(R.id.name_tv);
        type_tv = (TextView) shop_type_layout.findViewById(R.id.name_tv);
        address_tv = (TextView) shop_localtion_layout.findViewById(R.id.name_tv);
        address_tv1 = (TextView) shop_address_layout1.findViewById(R.id.name_tv);
        time_average_tv = (TextView) shop_time_average_layout.findViewById(R.id.name_tv);
        phone_tv = (TextView) shop_phone_layout.findViewById(R.id.name_tv);
        average_tv = (TextView) shop_average_layout.findViewById(R.id.name_tv);
        describe_tv = (TextView) shop_describe_layout.findViewById(R.id.name_tv);
        info_tv = (TextView) shop_info_layout.findViewById(R.id.name_tv);

        location_img = (ImageView) shop_localtion_layout.findViewById(R.id.location_img);

        name_tv.setText("门店名称");
        type_tv.setText("行业类型");
        address_tv.setText("GPS定位");
        address_tv1.setText("门店地址");
        time_average_tv.setText("营业时间描述");
        phone_tv.setText("门店电话");
        average_tv.setText("均价");
        describe_tv.setText("门店描述");
        info_tv.setText("店铺简介");


        name_ed = (EditText) shop_name_layout.findViewById(R.id.edit);
        info_ed = (EditText) shop_info_layout.findViewById(R.id.edit);
        type_ed = (EditText) shop_type_layout.findViewById(R.id.edit);
        address_ed = (EditText) shop_localtion_layout.findViewById(R.id.edit);
        address_ed1 = (EditText) shop_address_layout1.findViewById(R.id.edit);
        time_average_ed = (EditText) shop_time_average_layout.findViewById(R.id.edit);
        phone_ed = (EditText) shop_phone_layout.findViewById(R.id.edit);
        phone_ed.setInputType(InputType.TYPE_CLASS_PHONE);
        average_ed = (EditText) shop_average_layout.findViewById(R.id.edit);
        describe_ed = (EditText) shop_describe_layout.findViewById(R.id.edit);

        name_ed.setHint("输入门店名称");
        info_ed.setHint("输入门店简介");
        type_ed.setHint("选择门店经营分类");
        address_ed.setHint("选择门店GPS定位地址");
        address_ed1.setHint("手动填写门店地址");
        time_average_ed.setHint("门店营业时间描述");
        phone_ed.setHint("门店电话");
        average_ed.setHint("门店均价");
        describe_ed.setHint("门店描述");
        hideLayout();
        GridInit();
        setSpinner();

        setTitles(this, "修改商铺资料");

    }

    private void hideLayout() {

        shop_time_average_layout.setVisibility(View.GONE);
        shop_average_layout.setVisibility(View.GONE);
        shop_describe_layout.setVisibility(View.GONE);
        shop_time_layout.setVisibility(View.GONE);
    }

    private void GridInit() {
        gridView = f(R.id.gridView);
        list = new ArrayList<>();
        add = this;
    }


    private void setSpinner() {
        for (int i = 0; i < 25; i++) {
            time_list.add(i + "");
        }
        //适配器
//        arr_adapter = new ArrayAdapter<String>(this, R.layout.adapter_mytopactionbar_spinner, time_list);

        arr_adapter = new ArrayAdapter<String>(this, R.layout.adapter_mytopactionbar_spinner, time_list) {
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
//                    设置spinner展开的Item布局
                    convertView = getLayoutInflater().inflate(R.layout.adapter_mytopactionbar_spinner_item, parent, false);
                }
                TextView spinnerText = (TextView) convertView.findViewById(R.id.spinner_textView);
                spinnerText.setText(getItem(position));


                return convertView;
            }
        };
        //加载适配器
        start_sp.setAdapter(arr_adapter);

        start_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //拿到被选择项的值
                startTime = (String) start_sp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
        end_sp.setAdapter(arr_adapter);

        end_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                endTime = (String) end_sp.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });
    }

    @Override
    protected void initData() {
        getShopData();
    }

    private void getShopData() {

        OkHttpUtils
                .get()
                .url(MzFinal.URL + MzFinal.GETDETAILS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "网络不顺畅...");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            int code = getCode(response);
                            if (code == 1) {
                                JSONObject ob = getJsonOb(response);
                                BankBean bb = new Gson().fromJson(String.valueOf(ob), BankBean.class);

                                name_ed.setText(checkNull(bb.getName()));
                                type_ed.setText(checkNull(bb.getShopType().getTitle()));
                                info_ed.setText(checkNull(bb.getInfo()));

                                if (bb.getLatitude() != 0 && bb.getLongitude() != 0) {
                                    longitude = String.valueOf(bb.getLongitude());
                                    latitude = String.valueOf(bb.getLatitude());
                                    address_ed1.setText(bb.getAddress());
                                    address_ed.setHint("已定位");
                                    Glide.with(ChangeShopDataActivity.this).load(R.mipmap.location_red_icon).into(location_img);
                                }

                                address_ed1.setText(checkNull(bb.getAddress()));
                                time_average_ed.setText(checkNull(bb.getWorkingTimeDescription()));
                                phone_ed.setText(checkNull(bb.getPhone()));
                                average_ed.setText(bb.getAvgPrice() + "");
                                describe_ed.setText(bb.getInfo());

                                start_sp.setSelection(bb.getWorkingTimeStart(), true);
                                end_sp.setSelection(bb.getWorkingTimeEnd(), true);


                                Glide.with(getApplicationContext()).load(bb.getLogoUrl()).into(logo_img);
                                list.clear();
                                oldImg.clear();
                                shopImgs si = new shopImgs();
                                si.setId("");
                                list.add(si);

//                                if (bb.getShopImgs().size() > 0) {
                                oldImg = bb.getShopImgs();

                                for (int i = 0; i < bb.getShopImgs().size(); i++) {
                                    list.add(bb.getShopImgs().get(i));
                                }
                                if (adapter != null)
                                    adapter.notifyDataSetChanged();
                                else {
                                    adapter = new AddImgAdapter(ChangeShopDataActivity.this, list, add);
                                    gridView.setAdapter(adapter);
                                }
//                                }

                            } else
                                ToastUtil.ToastErrorMsg(ChangeShopDataActivity.this, response, code);
                        } catch (Exception e) {
                            Log.i(TAG, "Exception" + e);
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shop_localtion_layout:
                goLocationPage(this);
                break;
            case R.id.shop_type_layout:
                goShopTypePage(this);
                break;
            case R.id.logo_img:
                LoadPhoto();
                break;
            case R.id.check_shop_name:
                if (!name_ed.getText().toString().isEmpty())
                    CheckShopName(MzFinal.URL + MzFinal.CHECKSHOPNAME, name_ed.getText().toString(), this);
                break;
            case R.id.submit_info:
                if (commit.getText().toString().equals("返回首页"))
                    Finish(this);
                else
                    CommitShopData();
                break;
        }
    }

    private void CommitShopData() {

        PostFormBuilder post = OkHttpUtils.post();
        post.url(MzFinal.URL + MzFinal.MODIFYENTITY);
        if (logoFile != null)
            post.addFile("file1", logoFile.getName(), logoFile);
        if (!typeId.isEmpty())
            post.addParams("typeId", typeId);

        post.addParams("info", describe_ed.getText().toString());
        post.addParams("avgPrice", average_ed.getText().toString());
        post.addParams("address", address_ed1.getText().toString());
        post.addParams("name", name_ed.getText().toString());
        post.addParams("info", info_ed.getText().toString());
        post.addParams("workingTimeDescription", time_average_ed.getText().toString());
        post.addParams("workingTimeStart", startTime);
        post.addParams("workingTimeEnd", endTime);
        post.addParams("longitude", longitude);
        post.addParams("latitude", latitude);
        post.addParams("phone", phone_ed.getText().toString());
        post.addParams(MzFinal.KEY, SPreferences.getUserToken());


        RequestCall build = post.build();
        build.execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "网络不顺畅上传失败...");
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    int code = getCode(response);
                    if (code == 1) {
//                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "修改成功!!");
//                        finish();
                        succeed_layout.setVisibility(View.VISIBLE);
                        scrollView.setVisibility(View.GONE);
                        commit.setText("返回首页");
                    } else
                        ToastUtil.ToastErrorMsg(ChangeShopDataActivity.this, response, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void LoadPhoto() {
        new ActionSheetDialog(this).builder().
                addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ChangeShopDataActivity.this, "0", "0");
                    }
                }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                goUploadPhotoPage(ChangeShopDataActivity.this, "1", "0");
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 720 && resultCode == 10) {
            Bundle bundle = data.getExtras();
            String str = bundle.getString("shop_type_name");
            typeId = bundle.getString("shop_type_id");
            type_ed.setText(str);
            Log.i(TAG, "shop_type_name ===" + str);
        }

        if (requestCode == 719 && resultCode == 1008) {
            Bundle bundle = data.getExtras();
            latitude = bundle.getString("Map_latitude");
            longitude = bundle.getString("Map_longitude");
            adcode = bundle.getString("Map_adcode");
            cityId = bundle.getString("Map_cityId");
            provinceId = bundle.getString("Map_provinceId");

//            address_ed.setText(bundle.getString("Map_address"));
            address_ed.setHint("已定位");
            Glide.with(ChangeShopDataActivity.this).load(R.mipmap.location_red_icon).into(location_img);
            Log.i(TAG, "latitude ===" + latitude + "     longitude ===" + longitude + "   adcode ===" + adcode + "cityId ===" + cityId + " provinceId ===" + provinceId);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        listener = null;
    }

    @Override
    public void PhotoLBitmapistener(String path, Bitmap bitmap, int page) {
        //logo回调
        if (page == 0) {
//            if (oldImg.size() > 0) {
//                ChangeImgs(MzFinal.URL + MzFinal.MODIFYSHOPIMG, new File(path), oldImg.get(0).getId());
//            } else
//                NewlyNImgs(MzFinal.URL + MzFinal.ADDSHOPIMG, new File(path));
            logoFile = new File(path);
            Glide.with(getApplicationContext()).load(path).into(logo_img);

        }
        //图组回调
        if (page == 11) {
            Compress(path.replace("file:",""));
           /* if (OldPos <= oldImg.size()) {

                //修改;
                ChangeImgs(MzFinal.URL + MzFinal.MODIFYSHOPIMG, new File(path), oldImg.get(OldPos).getId());
            } else {
                //新增;
                NewlyNImgs(MzFinal.URL + MzFinal.ADDSHOPIMG, new File(path));
            }*/
        }

    }


    /**
     * 压缩
     */
    private void Compress(final String str) {
        Luban.with(this)
                .load(str)                                   // 传人要压缩的图片列表
                .ignoreBy(300)                               // 忽略不压缩图片的大小
//                .setTargetDir(FileManager.getSaveFilePath() + "gxLuban")// 设置压缩后文件存储位置
                .setCompressListener(new OnCompressListener() { //设置回调
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
//                        subscriber.onNext(file.getAbsolutePath());
                        Log.i(TAG, "onSuccess" + file.getAbsolutePath());
                        if (OldPos <= oldImg.size()) {
                            //修改;
                            ChangeImgs(MzFinal.URL + MzFinal.MODIFYSHOPIMG, file, oldImg.get(OldPos).getId());
                        } else {
                            //新增;
                            NewlyNImgs(MzFinal.URL + MzFinal.ADDSHOPIMG, file);
                        }
                        Cancle();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "图片压缩异常");
                        Cancle();
                    }
                }).launch();    //启动压缩
    }

    /**
     * 新增图组图片;
     *
     * @param url
     * @param file
     */
    private void NewlyNImgs(String url, File file) {
        Show(ChangeShopDataActivity.this, "", true, null);
        OkHttpUtils.post()
                .url(url)
                .addFile("file", file.getName(), file)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "网络不顺畅导致上传图片失败...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "Add response ======== " + response);
                        getShopData();
                        Cancle();
                    }
                });
    }

    /**
     * 图组修改;
     */
    private void ChangeImgs(String url, File file, String id) {
        Show(ChangeShopDataActivity.this, "", true, null);
        OkHttpUtils.post()
                .url(url)
                .addFile("file", file.getName(), file)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("id", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.i(TAG, "Exception ===" + e);
                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "网络不顺畅导致上传图片失败...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "Change response ======== " + response);
                        Cancle();
                        getShopData();
                    }
                });
    }

    /**
     * 图组删除;
     */
    private void DeleteImgs(String id, final int pos) {
        Show(ChangeShopDataActivity.this, "", true, null);
        OkHttpUtils.get()
                .url(MzFinal.URL + MzFinal.DELETESHOPIMGBYIDS)
                .addParams(MzFinal.KEY, SPreferences.getUserToken())
                .addParams("ids", id)
                .tag(this)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtil.toast2_bottom(ChangeShopDataActivity.this, "网络不顺畅导致上传图片失败...");
                        Cancle();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.i(TAG, "Delete response ======== " + response);
                        Cancle();
                        list.remove(pos);
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    public void onAdd() {
        shopImgs si = new shopImgs();
        si.setId("gongdelete");
        si.setImgUrl("");
        si.setShopId("");
        list.add(si);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onChange(int pos) {

        if (pos <= oldImg.size()) {
            OldPos = pos - 1;
        } else
            OldPos = pos;

        new ActionSheetDialog(this).builder().
                addSheetItem("相册", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                    @Override
                    public void onClick(int which) {
                        goUploadPhotoPage(ChangeShopDataActivity.this, "0", "11");
                    }
                }).addSheetItem("相机", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                goUploadPhotoPage(ChangeShopDataActivity.this, "1", "11");
            }
        }).show();
    }


    @Override
    public void onDelete(int pos) {
        if (pos != 0 && pos <= oldImg.size()) {
            DeleteImgs(oldImg.get(pos - 1).getId(), pos);
        }
    }

    @Override
    public void onSucceed() {
        ToastUtil.toast2_bottom(this, "店铺名称可用！！");
    }

    @Override
    public void onFailed() {
        ToastUtil.toast2_bottom(this, "店铺名称已被占用！！");
    }

    @Override
    public void onError() {
        ToastUtil.toast2_bottom(this, "网络异常！！");
    }

    @Override
    public void onElse(String content, int code) {
        try {
            ToastUtil.ToastErrorMsg(this, content, code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
