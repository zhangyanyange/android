package project.mvp.com.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity  {

    private AlertDialog.Builder mNormalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CallBack back=new CallBack();
        back.setOnAddCartListener(new CallBack.OnAddCartListener() {
            @Override
            public void addCart(String position) {
                TextView textView=(TextView) findViewById(R.id.tv);
                textView.setText(position);
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //创建并显示
                mNormalDialog.create().show();
            }
        });

        mNormalDialog = new AlertDialog.Builder(this);
        //设置title
        mNormalDialog.setTitle("对话框");
        //设置icon
        mNormalDialog.setIcon(R.mipmap.ic_launcher_round);
        //设置内容
        mNormalDialog.setMessage("对话框");
        //设置按钮
        mNormalDialog.setPositiveButton("确认"
                , new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        back.onAddCartListener.addCart("我是谁");
                        dialog.dismiss();
                    }
                });


    }


}
