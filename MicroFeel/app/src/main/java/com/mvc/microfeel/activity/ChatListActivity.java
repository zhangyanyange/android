package com.mvc.microfeel.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroupManager;
import com.hyphenate.chat.EMGroupOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.hyphenate.exceptions.HyphenateException;
import com.mvc.microfeel.R;

import java.util.List;

public class ChatListActivity extends FragmentActivity {
    private EaseConversationListFragment conversationListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        EMClient.getInstance().chatManager().addMessageListener(messageListener);

        EMClient.getInstance().login("123", "123", new EMCallBack() {
            @Override
            public void onSuccess() {
                EMGroupOptions option = new EMGroupOptions();
                option.maxUsers = 200;
                option.style = EMGroupManager.EMGroupStyle.EMGroupStylePrivateMemberCanInvite;
                String[]s={"18624438893","18624438892"};
                try {
                    EMClient.getInstance().groupManager().createGroup("共同奋斗", "123", s, "da", option);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, conversationListFragment).show(conversationListFragment).commit();
            }

            @Override
            public void onError(int i, String s) {
                System.out.println(s);
                System.out.println("shibai");
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });
        conversationListFragment = new EaseConversationListFragment();

        conversationListFragment.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {

            @Override
            public void onListItemClicked(EMConversation conversation) {
                EMConversation.EMConversationType type = conversation.getType();
                int chatType;
                if(type== EMConversation.EMConversationType.Chat){
                    chatType=1;
                }else{
                    chatType=2;
                }
                startActivity(new Intent(ChatListActivity.this, ChatActivity.class).putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId()).putExtra(EaseConstant.EXTRA_CHAT_TYPE,chatType));
            }
        });



    }

    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {}
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                if (conversationListFragment != null) {
                    conversationListFragment.refresh();
                }
            }
        });
    }
}
