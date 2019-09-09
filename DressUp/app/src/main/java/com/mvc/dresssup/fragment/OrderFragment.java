package com.mvc.dresssup.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mvc.dresssup.R;
import com.mvc.dresssup.activity.AddNewLocationActivity;
import com.mvc.dresssup.activity.MakeSureOrderActivity;
import com.mvc.dresssup.adapter.ShopcatAdapter;
import com.mvc.dresssup.base.BaseFragment;
import com.mvc.dresssup.base.LoadingPager;
import com.mvc.dresssup.base.MyApplication;
import com.mvc.dresssup.domain.AddCar;
import com.mvc.dresssup.domain.AddressBean;
import com.mvc.dresssup.domain.GoodsInfo;
import com.mvc.dresssup.domain.LoginRefresh;
import com.mvc.dresssup.domain.RefreshCar;
import com.mvc.dresssup.domain.StoreInfo;
import com.mvc.dresssup.procotol.DefaultAddressProcotol;
import com.mvc.dresssup.procotol.EditShopCartProcotol;
import com.mvc.dresssup.procotol.GetShopCarProcotol;
import com.mvc.dresssup.procotol.RemoveShopCarProcotol;
import com.mvc.dresssup.util.PrefUtils;
import com.mvc.dresssup.util.UIUtils;
import com.mvc.dresssup.util.UtilTool;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by zy2 on 2017/9/6.
 */

public class OrderFragment extends BaseFragment implements View.OnClickListener, ShopcatAdapter.CheckInterface, ShopcatAdapter.ModifyCountInterface, ShopcatAdapter.GroupEditorListener {

    @BindView(R.id.shoppingcat_num)
    TextView shoppingcatNum;
    @BindView(R.id.actionBar_edit)
    Button actionBarEdit;
    @BindView(R.id.rl)
    RelativeLayout rl;
    @BindView(R.id.listView)
    ExpandableListView listView;
    @BindView(R.id.all_checkBox)
    CheckBox allCheckBox;
    @BindView(R.id.total_price)
    TextView totalPrice;
    @BindView(R.id.go_pay)
    TextView goPay;
    @BindView(R.id.order_info)
    LinearLayout orderInfo;
    @BindView(R.id.del_goods)
    TextView delGoods;
    @BindView(R.id.share_info)
    LinearLayout shareInfo;
    @BindView(R.id.ll_cart)
    LinearLayout llCart;
    @BindView(R.id.loadView)
    ProgressBar loadView;
    Unbinder unbinder;
    private Context mcontext;
    private double mtotalPrice = 0.00;
    private int mtotalCount = 0;
    //false就是编辑，ture就是完成
    private boolean flag = false;
    private ShopcatAdapter adapter;


    private List<StoreInfo> groups; //组元素的列表
    private Map<String, List<GoodsInfo.CartsBean.ProductsBean>> childs; //子元素的列表
    private GoodsInfo goodsInfo;


    private ArrayList<GoodsInfo.CartsBean.ProductsBean> isSelectedProduct = new ArrayList<>();

    @Override
    public LoadingPager.LoadedResult initData() {
        EventBus.getDefault().register(this);
        if (!PrefUtils.getBoolean(UIUtils.getContext(), "isLogin", false)) {
            return LoadingPager.LoadedResult.NOLOAD;
        }
        goodsInfo = GetShopCarProcotol.getShopCar();
        return checkResult(goodsInfo);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_shopcar, null);
        ButterKnife.bind(this, view);
        groups = new ArrayList<StoreInfo>();
        childs = new HashMap<String, List<GoodsInfo.CartsBean.ProductsBean>>();
        init();

        initEvents();

        setCartNum();
        return view;
    }


    private void init() {
        mcontext = getActivity();
        groups.clear();
        childs.clear();
        for (int i = 0; i < goodsInfo.getCarts().size(); i++) {
            groups.add(new StoreInfo("" + i, goodsInfo.getCarts().get(i).getBrand()));

            childs.put(groups.get(i).getId(), goodsInfo.getCarts().get(i).getProducts());
        }
    }

    private void initEvents() {
        actionBarEdit.setOnClickListener(this);
        adapter = new ShopcatAdapter(groups, childs, mcontext);
        adapter.setCheckInterface(this);//关键步骤1：设置复选框的接口
        adapter.setModifyCountInterface(this); //关键步骤2:设置增删减的接口
        adapter.setGroupEditorListener(this);//关键步骤3:监听组列表的编辑状态
        listView.setGroupIndicator(null); //设置属性 GroupIndicator 去掉向下箭头
        listView.setAdapter(adapter);
        for (int i = 0; i < adapter.getGroupCount(); i++) {
            listView.expandGroup(i); //关键步骤4:初始化，将ExpandableListView以展开的方式显示
        }
    }


    /**
     * 设置购物车的数量
     */
    private void setCartNum() {
        int count = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<GoodsInfo.CartsBean.ProductsBean> Childs = childs.get(group.getId());
            for (GoodsInfo.CartsBean.ProductsBean childs : Childs) {
                count++;
            }
        }

        //购物车已经清空
        if (count == 0) {
            clearCart();
        } else {
//            shoppingcatNum.setText("购物车(" + count + ")");
        }

    }

    private void clearCart() {
//        shoppingcatNum.setText("购物车(0)");
        actionBarEdit.setVisibility(View.GONE);
        llCart.setVisibility(View.GONE);
//        empty_shopcart.setVisibility(View.VISIBLE);//这里发生过错误
    }

    /**
     * 删除操作
     * 1.不要边遍历边删除,容易出现数组越界的情况
     * 2.把将要删除的对象放进相应的容器中，待遍历完，用removeAll的方式进行删除
     */
    private void doDelete() {
        final ArrayList<AddCar> list = new ArrayList<>();
        List<StoreInfo> toBeDeleteGroups = new ArrayList<StoreInfo>(); //待删除的组元素
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isChoosed()) {
                toBeDeleteGroups.add(group);
            }
            List<GoodsInfo.CartsBean.ProductsBean> toBeDeleteChilds = new ArrayList<GoodsInfo.CartsBean.ProductsBean>();//待删除的子元素
            List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                if (child.get(j).isChoosed()) {
                    toBeDeleteChilds.add(child.get(j));
                    AddCar car = new AddCar();
                    car.setId(child.get(j).getId());
                    car.setNum(child.get(j).getNum());
                    list.add(car);
                }
            }
            child.removeAll(toBeDeleteChilds);
        }
        groups.removeAll(toBeDeleteGroups);
        //重新设置购物车
        setCartNum();
        adapter.notifyDataSetChanged();

        gotoNetDelete(list);

    }

    private void gotoNetDelete(final ArrayList<AddCar> list) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                RemoveShopCarProcotol.removeShopCar(list);
            }
        }).start();
    }

    /**
     * @param groupPosition 组元素的位置
     * @param isChecked     组元素的选中与否
     *                      思路:组元素被选中了，那么下辖全部的子元素也被选中
     */
    @Override
    public void checkGroup(int groupPosition, boolean isChecked) {
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            child.get(i).setChoosed(isChecked);
        }
        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * @return 判断组元素是否全选
     */
    private boolean isCheckAll() {
        for (StoreInfo group : groups) {
            if (!group.isChoosed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param groupPosition 组元素的位置
     * @param childPosition 子元素的位置
     * @param isChecked     子元素的选中与否
     */
    @Override
    public void checkChild(int groupPosition, int childPosition, boolean isChecked) {
        boolean allChildSameState = true; //判断该组下面的所有子元素是否处于同一状态
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());
        for (int i = 0; i < child.size(); i++) {
            //不选全中
            if (child.get(i).isChoosed() != isChecked) {
                allChildSameState = false;
                break;
            }
        }

        if (allChildSameState) {
            group.setChoosed(isChecked);//如果子元素状态相同，那么对应的组元素也设置成这一种的同一状态
        } else {
            group.setChoosed(false);//否则一律视为未选中
        }

        if (isCheckAll()) {
            allCheckBox.setChecked(true);//全选
        } else {
            allCheckBox.setChecked(false);//反选
        }

        adapter.notifyDataSetChanged();
        calulate();

    }

    @Override
    public void doIncrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        AddCar car = new AddCar();
        GoodsInfo.CartsBean.ProductsBean good = (GoodsInfo.CartsBean.ProductsBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getNum();
        count++;
        good.setNum(count);
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
        car.setNum(good.getNum());
        car.setId(good.getId());
        String s = new Gson().toJson(car);
        doInDo(s);

    }

    /**
     * @param groupPosition
     * @param childPosition
     * @param showCountView
     * @param isChecked
     */
    @Override
    public void doDecrease(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        AddCar car = new AddCar();
        GoodsInfo.CartsBean.ProductsBean good = (GoodsInfo.CartsBean.ProductsBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getNum();
        if (count == 1) {
            return;
        }
        count--;
        good.setNum(count);
        ((TextView) showCountView).setText("" + count);
        adapter.notifyDataSetChanged();
        calulate();

        car.setNum(good.getNum());
        car.setId(good.getId());
        String s = new Gson().toJson(car);
        doInDo(s);
    }

    /**
     * @param groupPosition
     * @param childPosition 思路:当子元素=0，那么组元素也要删除
     */
    @Override
    public void childDelete(int groupPosition, int childPosition) {
        ArrayList<AddCar> list = new ArrayList<>();
        StoreInfo group = groups.get(groupPosition);
        List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());

        AddCar car = new AddCar();
        car.setId(child.get(childPosition).getId());
        car.setNum(child.get(childPosition).getNum());
        list.add(car);

        child.remove(childPosition);
        if (child.size() == 0) {
            groups.remove(groupPosition);
        }
        adapter.notifyDataSetChanged();
        calulate();

        gotoNetDelete(list);
    }

    public void doUpdate(int groupPosition, int childPosition, View showCountView, boolean isChecked) {
        GoodsInfo.CartsBean.ProductsBean good = (GoodsInfo.CartsBean.ProductsBean) adapter.getChild(groupPosition, childPosition);
        int count = good.getNum();
        ((TextView) showCountView).setText(String.valueOf(count));
        adapter.notifyDataSetChanged();
        calulate();
    }

    @Override
    public void groupEditor(int groupPosition) {

    }

    @OnClick({R.id.all_checkBox, R.id.go_pay, R.id.del_goods})
    public void onClick(View view) {
        AlertDialog dialog;
        switch (view.getId()) {
            case R.id.all_checkBox:
                doCheckAll();
                break;
            case R.id.go_pay:
                if (mtotalCount == 0) {
                    UtilTool.toast(mcontext, "请选择要支付的商品");
                    return;
                }
//                dialog = new AlertDialog.Builder(mcontext).create();
//                dialog.setMessage("总计:" + mtotalCount + "种商品，" + mtotalPrice + "元");
//                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "支付", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        return;
//                    }
//                });
//                dialog.show();

                loadView.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final AddressBean defaultAddress = DefaultAddressProcotol.getDefaultAddress();
                        MyApplication.getHandler().post(new Runnable() {
                            @Override
                            public void run() {
                                loadView.setVisibility(View.GONE);
                                if (defaultAddress != null) {
                                    if (defaultAddress.isIsDefault() == 1) {
                                        Intent intent = new Intent(UIUtils.getContext(), MakeSureOrderActivity.class);
                                        intent.putExtra("address", defaultAddress);
                                        intent.putParcelableArrayListExtra("isSelectedProduct", isSelectedProduct);
                                        startActivity(intent);

                                    } else {
                                        goLocationActivity();
                                    }
                                } else {
                                    goLocationActivity();
                                }
                            }
                        });
                    }
                }).start();


                break;

            case R.id.del_goods:
                if (mtotalCount == 0) {
                    UtilTool.toast(mcontext, "请选择要删除的商品");
                    return;
                }
                dialog = new AlertDialog.Builder(mcontext).create();
                dialog.setMessage("确认要删除该商品吗?");
                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doDelete();
                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                dialog.show();
                break;
            case R.id.actionBar_edit:
                flag = !flag;
                setActionBarEditor();
                setVisiable();
                break;
        }
    }

    private void goLocationActivity() {
        Intent intent = new Intent(UIUtils.getContext(), AddNewLocationActivity.class);
        intent.putExtra("flag", "1");
        startActivity(intent);
    }

    /**
     * ActionBar标题上点编辑的时候，只显示每一个店铺的商品修改界面
     * ActionBar标题上点完成的时候，只显示每一个店铺的商品信息界面
     */
    private void setActionBarEditor() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            if (group.isActionBarEditor()) {
                group.setActionBarEditor(false);
            } else {
                group.setActionBarEditor(true);
            }
        }
        adapter.notifyDataSetChanged();
    }


    /**
     * 全选和反选
     * 错误标记：在这里出现过错误
     */
    private void doCheckAll() {
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            group.setChoosed(allCheckBox.isChecked());
            List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                child.get(j).setChoosed(allCheckBox.isChecked());//这里出现过错误
            }
        }
        adapter.notifyDataSetChanged();
        calulate();
    }

    /**
     * 计算商品总价格，操作步骤
     * 1.先清空全局计价,计数
     * 2.遍历所有的子元素，只要是被选中的，就进行相关的计算操作
     * 3.给textView填充数据
     */
    private void calulate() {

        isSelectedProduct.clear();
        mtotalPrice = 0.00;
        mtotalCount = 0;
        for (int i = 0; i < groups.size(); i++) {
            StoreInfo group = groups.get(i);
            List<GoodsInfo.CartsBean.ProductsBean> child = childs.get(group.getId());
            for (int j = 0; j < child.size(); j++) {
                GoodsInfo.CartsBean.ProductsBean good = child.get(j);
                if (good.isChoosed()) {
                    mtotalCount++;
                    mtotalPrice += Double.parseDouble(good.getSalePrice()) * good.getNum();
                    isSelectedProduct.add(good);
                }
            }
        }
        totalPrice.setText("￥" + mtotalPrice + "");
        goPay.setText("去支付(" + mtotalCount + ")");
        if (mtotalCount == 0) {
            setCartNum();
        } else {
//            shoppingcatNum.setText("购物车(" + mtotalCount + ")");
        }


    }

    private void setVisiable() {
        if (flag) {
            orderInfo.setVisibility(View.GONE);
            shareInfo.setVisibility(View.VISIBLE);
            actionBarEdit.setText("完成");
        } else {
            orderInfo.setVisibility(View.VISIBLE);
            shareInfo.setVisibility(View.GONE);
            actionBarEdit.setText("编辑");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//    adapter = null;
//    childs.clear();
//    groups.clear();
//    mtotalPrice = 0.00;
//    mtotalCount = 0;
    }

    public void doInDo(final String s) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                EditShopCartProcotol.editshopcar(s);
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshUI(RefreshCar car) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                goodsInfo = GetShopCarProcotol.getShopCar();
                MyApplication.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        init();
                        adapter.notifyDataSetChanged();

                        setCartNum();
                    }
                });
            }
        }).start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void resetData(LoginRefresh loginRefresh) {
        EventBus.getDefault().unregister(this);
        LoadingPager loadingPager = getLoadingPager();
        loadingPager.triggerLoadData();
    }


}
