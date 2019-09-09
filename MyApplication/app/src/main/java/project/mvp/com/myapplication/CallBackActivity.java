package project.mvp.com.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class CallBackActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_back);
        final CallBack back=new CallBack();
        back.setOnAddCartListener(new CallBack.OnAddCartListener() {
            @Override
            public void addCart(String position) {
                System.out.println(position);
            }
        });
        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              back.onAddCartListener.addCart("我是谁");
            }
        });
    }

}
