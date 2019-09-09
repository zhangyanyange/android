package com.mvc.dresssup.chat;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.ui.BaseActivity;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.util.Config;
import com.mvc.dresssup.MainActivity;
import com.mvc.dresssup.R;
import com.mvc.dresssup.domain.ProductDetail;

public class ChatActivity extends BaseActivity {

    public static ChatActivity instance = null;

    private ChatFragment chatFragment;

    String toChatUsername;
    private ProductDetail productDetail;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.hd_activity_chat);
        instance = this;
        //IM服务号
        toChatUsername ="kefuchannelimid_691144";
        //可以直接new ChatFragment使用
        String chatFragmentTAG = "chatFragment";
        chatFragment = (ChatFragment) getSupportFragmentManager().findFragmentByTag(chatFragmentTAG);
        if (chatFragment == null){
            chatFragment = new CustomChatFragment();
            //传入参数
            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment, chatFragmentTAG).commit();
            sendOrderOrTrack();
        }
    }


    /**
     * 发送订单或轨迹消息
     */
    private void sendOrderOrTrack() {
        Bundle bundle = getIntent().getBundleExtra(Config.EXTRA_BUNDLE);
        if (bundle!= null) {
            //检查是否是从某个商品详情进来
            productDetail = bundle.getParcelable("productDetail");
            sendTrackMessage(productDetail.getName());
        }
    }

    /**
     * 发送轨迹消息
     */
    private void sendTrackMessage(String productName) {
        Message message = Message.createTxtSendMessage(productName, toChatUsername);
        message.addContent(DemoMessageHelper.createVisitorTrack(this, productDetail));
        ChatClient.getInstance().chatManager().sendMessage(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MediaManager.release();
        instance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        // 点击notification bar进入聊天页面，保证只有一个聊天页面
        String username = intent.getStringExtra(Config.EXTRA_SERVICE_IM_NUMBER);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }

    @Override
    public void onBackPressed() {
        if (chatFragment != null) {
            chatFragment.onBackPressed();
        }
        if (CommonUtils.isSingleActivity(this)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        super.onBackPressed();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
