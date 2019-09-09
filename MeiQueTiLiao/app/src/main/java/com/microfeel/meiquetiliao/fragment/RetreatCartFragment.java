package com.microfeel.meiquetiliao.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnDismissListener;
import com.bigkoo.alertview.OnItemClickListener;
import com.microfeel.meiquetiliao.R;
import com.microfeel.meiquetiliao.activity.RecordPhotoActivity;
import com.microfeel.meiquetiliao.adapter.RetreactMaterialAdapter;
import com.microfeel.meiquetiliao.adapter.ShopCartAdapter;
import com.microfeel.meiquetiliao.base.BaseFragment;
import com.microfeel.meiquetiliao.base.LoadingPager;
import com.microfeel.meiquetiliao.domain.RetreactMaterial;
import com.microfeel.meiquetiliao.procotol.GetRetreatMaterialProcotol;
import com.microfeel.meiquetiliao.util.PrefUtils;
import com.microfeel.meiquetiliao.util.ShowToastTime;
import com.microfeel.meiquetiliao.util.UIUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by zy2 on 2017/12/15.
 */
public class RetreatCartFragment extends BaseFragment implements OnItemClickListener, OnDismissListener {

    @Bind(R.id.lv_shopcart)
    ListView lvShopcart;
    @Bind(R.id.make_sure)
    TextView makeSure;
    private RetreactMaterial retreactMaterial;
    int total=0;

    private InputMethodManager imm;
    private AlertView mAlertViewExt;
    private EditText etCount;
    private ViewGroup extView;

    private int tag=-1;
    private RetreactMaterialAdapter adapter;

    @Override
    public LoadingPager.LoadedResult initData() {
        retreactMaterial = GetRetreatMaterialProcotol.getMaterial(PrefUtils.getString(UIUtils.getContext(), "location", ""));
        return checkResult(retreactMaterial);
    }

    @Override
    public View initSuccessView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.fragment_retreat_cart, null);
        ButterKnife.bind(this, view);

        adapter = new RetreactMaterialAdapter(getActivity(), retreactMaterial.getListContent());
        lvShopcart.setAdapter(adapter);
        adapter.setAmountChangeListener(new RetreactMaterialAdapter.AmountChangeListener() {
            @Override
            public void amountchange(int position) {
                getCount();
            }
        });

        //退料数量
        makeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<RetreactMaterial.ListContentBean>makesureMaterialList=new ArrayList<RetreactMaterial.ListContentBean>();
                ArrayList<RetreactMaterial.ListContentBean> retreactMaterialList = retreactMaterial.getListContent();
                for (int i = 0; i < retreactMaterialList.size(); i++) {
                    if(retreactMaterialList.get(i).getNumber()!=0){
                        makesureMaterialList.add(retreactMaterialList.get(i));
                    }
                }
                if(makesureMaterialList.size()==0){
                    ShowToastTime.showTextToast("被选中的材料数量不能为0");
                    return;
                }

                Intent intent=new Intent(UIUtils.getContext(),RecordPhotoActivity.class);
                intent.putParcelableArrayListExtra("retreatMaterial",makesureMaterialList);
                startActivity(intent);
            }
        });

        imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        mAlertViewExt = new AlertView("提示", "请输入你想要的材料数量！", "取消", null, new String[]{"完成"}, getActivity(), AlertView.Style.Alert, this);
        extView = (ViewGroup) LayoutInflater.from(getActivity()).inflate(R.layout.alert_edittext,null);
        etCount = (EditText) extView.findViewById(R.id.etCount);
        etCount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                //输入框出来则往上移动
                boolean isOpen=imm.isActive();
                mAlertViewExt.setMarginBottom(isOpen&&focus ? 120 :0);
            }
        });

        mAlertViewExt.addExtView(extView);

        adapter.setEditTextListener(new ShopCartAdapter.EditTextListener() {
            @Override
            public void edittext(int position) {
                tag=position;
                etCount.setText(""+retreactMaterial.getListContent().get(position).getNumber());
                mAlertViewExt.show();
            }
        });
        return view;
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
                Toast.makeText(UIUtils.getContext(), "请输入材料数量", Toast.LENGTH_SHORT).show();
            }
            else{
                if(Integer.valueOf(count)>= retreactMaterial.getListContent().get(tag).getCount()){
                    retreactMaterial.getListContent().get(tag).setNumber((int)retreactMaterial.getListContent().get(tag).getCount());
                }else if(0>=Integer.valueOf(count)){
                    retreactMaterial.getListContent().get(tag).setNumber(0);
                }else{
                    retreactMaterial.getListContent().get(tag).setNumber(Integer.valueOf(count));
                }
                updateItem(tag);

                getCount();
            }

            return;
        }
    }

    private void getCount() {
        total=0;
        for (int i = 0; i <retreactMaterial.getListContent().size() ; i++) {
            total+=retreactMaterial.getListContent().get(i).getNumber();
        }
        makeSure.setText("确认材料("+total+")");
    }

    @Override
    public void onDismiss(Object o) {
        closeKeyboard();
        Toast.makeText(UIUtils.getContext(), "消失了", Toast.LENGTH_SHORT).show();
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
