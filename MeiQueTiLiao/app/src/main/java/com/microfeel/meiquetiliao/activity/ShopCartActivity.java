package com.microfeel.meiquetiliao.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.adapter.ShopCartAdapter;
import com.microfeel.meiquetiliao.db.ShopCartManager;
import com.microfeel.meiquetiliao.domain.ShopCat;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShopCartActivity extends Activity  implements OnItemClickListener, OnDismissListener {

    @Bind(R.id.back)
    LinearLayout back;
    @Bind(R.id.lv_shopcart)
    ListView lvShopcart;
    @Bind(R.id.tv_price)
    TextView tvPrice;
    @Bind(R.id.make_sure)
    TextView makeSure;
    @Bind(R.id.limit_money)
    TextView limitMoney;
    private ArrayList<ShopCat> shopCats;
    private double total = 0;
    private ShopCartManager manager;
    private DecimalFormat df;
    private ShopCartAdapter adapter;
    private String limitMoney1;

    private AlertView mAlertViewExt;
    private EditText etCount;
    private ViewGroup extView;
    private InputMethodManager imm;

    private int tag=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_cart);
        ButterKnife.bind(this);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mAlertViewExt = new AlertView("提示", "请输入你想要的材料数量！", "取消", null, new String[]{"完成"}, this, AlertView.Style.Alert, this);
        extView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.alert_edittext,null);
        etCount = (EditText) extView.findViewById(R.id.etCount);
        etCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 280 :0);
            }
        });

        mAlertViewExt.addExtView(extView);


        Intent intent = getIntent();
        limitMoney1 = intent.getStringExtra("limitMoney");

        df = new DecimalFormat("0.00");
        Double limit = Double.parseDouble(limitMoney1);
        final String format1 = df.format(limit);
        limitMoney.setText( format1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(total<=Double.parseDouble(format1)){
                    startActivity(new Intent(ShopCartActivity.this, OrderInfoActivity.class));
                }else{
                   ShowToastTime.showTextToast("你所提材料，超过你的限额,请减少材料数量,进行提料");
                }

            }
        });
        manager = new ShopCartManager(this);
        shopCats = manager.query(PrefUtils.getString(this, "projectId", ""));
        for (int i = 0; i < shopCats.size(); i++) {
            total += shopCats.get(i).getPrice() * shopCats.get(i).getCount();
        }
        String format = df.format(total);
        tvPrice.setText(format);
        adapter = new ShopCartAdapter(this, shopCats, manager);
        lvShopcart.setAdapter(adapter);

        /**
         * 弹出输入框
         */
        adapter.setEditTextListener(new ShopCartAdapter.EditTextListener() {
            @Override
            public void edittext(int position) {
                tag=position;
                etCount.setText(""+shopCats.get(position).getCount());
                mAlertViewExt.show();
            }
        });

        adapter.setAmountChangeListener(new ShopCartAdapter.AmountChangeListener() {
            @Override
            public void amountchange(int position) {
                getPrice();
            }
        });

        adapter.setDeleteListener(new ShopCartAdapter.DeleteListener() {
            @Override
            public void delete(int position) {
                boolean projectId = manager.delete(shopCats.get(position).getMaterialid(), PrefUtils.getString(ShopCartActivity.this, "projectId", ""));
                if (projectId) {
                    shopCats.clear();
                    ArrayList<ShopCat> shopCats1 = manager.query(PrefUtils.getString(ShopCartActivity.this, "projectId", ""));
                    shopCats.addAll(shopCats1);
                    adapter.notifyDataSetChanged();

                    ShowToastTime.showTextToast("删除成功");
                    total = 0;
                    for (int i = 0; i < shopCats.size(); i++) {
                        total += shopCats.get(i).getPrice() * shopCats.get(i).getCount();
                    }
                    String format = df.format(total);
                    tvPrice.setText(format);
                } else {
                    ShowToastTime.showTextToast("删除失败");
                }
            }
        });


    }


    private void getPrice() {
        total = 0;
        shopCats.clear();

        shopCats.addAll(manager.query(PrefUtils.getString(ShopCartActivity.this, "projectId", "")));
        for (int i = 0; i < shopCats.size(); i++) {
            total += shopCats.get(i).getPrice() * shopCats.get(i).getCount();
        }
        String format = df.format(total);
        tvPrice.setText(format);
    }

    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(etCount.getWindowToken(),0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }
    @Override
    public void onItemClick(Object o,int position) {
        closeKeyboard();
        //判断是否是拓展窗口View，而且点击的是非取消按钮
        if(o == mAlertViewExt && position != AlertView.CANCELPOSITION){
            String count = etCount.getText().toString();
            if(count.isEmpty()){
                Toast.makeText(this, "请输入材料数量", Toast.LENGTH_SHORT).show();
            }
            else{
                if(Integer.valueOf(count)<=0){
                    shopCats.get(tag).setCount(1);
                    manager.update("1", PrefUtils.getString(ShopCartActivity.this, "projectId", ""), shopCats.get(tag).getMaterialid());
                }else {
                    shopCats.get(tag).setCount(Integer.valueOf(count));
                    manager.update(count, PrefUtils.getString(ShopCartActivity.this, "projectId", ""), shopCats.get(tag).getMaterialid());
                }
                updateItem(tag);
              getPrice();
            }

            return;
        }
    }

    @Override
    public void onDismiss(Object o) {
        closeKeyboard();
        Toast.makeText(this, "消失了", Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
        }

        return super.onKeyDown(keyCode, event);
    }


    /**
     * 第三种方法 调用一次getView()方法；Google推荐的做法
     *
     * @param position 要更新的位置
     */
    private void updateItem(int position) {
        /**第一个可见的位置**/
        int firstVisiblePosition = lvShopcart.getFirstVisiblePosition();
        /**最后一个可见的位置**/
        int lastVisiblePosition = lvShopcart.getLastVisiblePosition();

        /**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
        if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
            /**获取指定位置view对象**/
            View view = lvShopcart.getChildAt(position - firstVisiblePosition);
            adapter.getView(position, view, lvShopcart);
        }

    }
}
