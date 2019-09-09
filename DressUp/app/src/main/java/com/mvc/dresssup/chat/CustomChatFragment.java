package com.mvc.dresssup.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.chat.Message;
import com.hyphenate.helpdesk.easeui.provider.CustomChatRowProvider;
import com.hyphenate.helpdesk.easeui.recorder.MediaManager;
import com.hyphenate.helpdesk.easeui.ui.ChatFragment;
import com.hyphenate.helpdesk.easeui.util.CommonUtils;
import com.hyphenate.helpdesk.easeui.widget.AlertDialogFragment;
import com.hyphenate.helpdesk.easeui.widget.chatrow.ChatRow;
import com.hyphenate.helpdesk.model.MessageHelper;
import com.mvc.dresssup.MainActivity;
import com.mvc.dresssup.R;

public class CustomChatFragment extends ChatFragment implements ChatFragment.EaseChatFragmentListener {

    //避免和基类定义的常量可能发生冲突,常量从11开始定义
    private static final int ITEM_MAP = 11;
    private static final int ITEM_LEAVE_MSG = 12;//ITEM_SHORTCUT = 12;
    private static final int ITEM_VIDEO = 13;

    private static final int REQUEST_CODE_SELECT_MAP = 11;
    private static final int REQUEST_CODE_SHORTCUT = 12;

    public static final int REQUEST_CODE_CONTEXT_MENU = 13;

    //message type 需要从1开始
    public static final int MESSAGE_TYPE_SENT_MAP = 1;
    public static final int MESSAGE_TYPE_RECV_MAP = 2;
    public static final int MESSAGE_TYPE_SENT_ORDER = 3;
    public static final int MESSAGE_TYPE_RECV_ORDER = 4;
    public static final int MESSAGE_TYPE_SENT_EVAL = 5;
    public static final int MESSAGE_TYPE_RECV_EVAL = 6;
    public static final int MESSAGE_TYPE_SENT_TRACK = 7;
    public static final int MESSAGE_TYPE_RECV_TRACK = 8;
    public static final int MESSAGE_TYPE_SENT_FORM = 9;
    public static final int MESSAGE_TYPE_RECV_FORM = 10;
    //message type 最大值
    public static final int MESSAGE_TYPE_COUNT = 11;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    protected void setUpView() {
        //这是新添加的扩展点击事件
        setChatFragmentListener(this);
        super.setUpView();
        //可以在此处设置titleBar(标题栏)的属性
//        titleBar.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
        titleBar.setLeftImageResource(R.drawable.hd_icon_title_back);
        titleBar.setLeftLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                getActivity().finish();
            }
        });
        titleBar.setRightImageResource(R.drawable.hd_chat_delete_icon);
        titleBar.setRightLayoutClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showAlertDialog();
            }
        });
    }



    private void showAlertDialog() {
        FragmentTransaction mFragTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        String fragmentTag = "dialogFragment";
        Fragment fragment =  getActivity().getSupportFragmentManager().findFragmentByTag(fragmentTag);
        if(fragment!=null){
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mFragTransaction.remove(fragment);
        }
        final AlertDialogFragment dialogFragment = new AlertDialogFragment();
        dialogFragment.setTitleText(getString(R.string.prompt));
        dialogFragment.setContentText(getString(R.string.Whether_to_empty_all_chats));
        dialogFragment.setupLeftButton(null, null);
        dialogFragment.setupRightBtn(null, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatClient.getInstance().chatManager().clearConversation(toChatUsername);
                messageList.refresh();
                dialogFragment.dismiss();
                MediaManager.release();
            }
        });
        dialogFragment.show(mFragTransaction, fragmentTag);
    }

    @Override
    public void onAvatarClick(String username) {
        //头像点击事情
//        startActivity(new Intent(getActivity(), ...class));
    }

    @Override
    public boolean onMessageBubbleClick(Message message) {
        //消息框点击事件,return true
        if (message.getType() == Message.Type.LOCATION) {
            return true;
        }
        return false;
    }

    @Override
    public void onMessageBubbleLongClick(Message message) {
        //消息框长按
        startActivityForResult(new Intent(getActivity(), ContextMenuActivity.class).putExtra("message", message), REQUEST_CODE_CONTEXT_MENU);
    }




    @Override
    public CustomChatRowProvider onSetCustomChatRowProvider() {
        return new DemoCustomChatRowProvider();
    }

    @Override
    protected void registerExtendMenuItem() {
        //demo 这里不覆盖基类已经注册的item, item点击listener沿用基类的
        super.registerExtendMenuItem();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // 复制消息
                    String string = ((EMTextMessageBody) contextMenuMessage.body()).getMessage();
                    clipboard.setText(string);
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // 删除消息
                    if (contextMenuMessage.getType() == Message.Type.VOICE){
                        EMVoiceMessageBody voiceBody = (EMVoiceMessageBody) contextMenuMessage.body();
                        String voicePath = voiceBody.getLocalUrl();
                        MediaManager.release(voicePath);
                    }
                    conversation.removeMessage(contextMenuMessage.messageId());
                    messageList.refresh();
                    break;
                default:
                    break;
            }
        }

        if (resultCode == Activity.RESULT_OK) {
           if (requestCode == REQUEST_CODE_SHORTCUT) {
                String content = data.getStringExtra("content");
                if (!TextUtils.isEmpty(content)) {
                    inputMenu.setInputMessage(content);
                }
            } else if (requestCode == REQUEST_CODE_EVAL) {
                messageList.refresh();
            }
        }

    }

    @Override
    public void onMessageSent() {
        messageList.refreshSelectLast();
    }

    /**
     * chat row provider
     */
    private final class DemoCustomChatRowProvider implements CustomChatRowProvider {

        @Override
        public int getCustomChatRowTypeCount() {
            //地图 和 满意度 发送接收 共4种
            //订单 和 轨迹 发送接收共4种
            // form 发送接收2种
            return MESSAGE_TYPE_COUNT;
        }

        @Override
        public int getCustomChatRowType(Message message) {
            //此处内部有用到,必须写否则可能会出现错位
            if (message.getType() == Message.Type.LOCATION){
                return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_MAP : MESSAGE_TYPE_SENT_MAP;
            }else if (message.getType() == Message.Type.TXT){
                switch (MessageHelper.getMessageExtType(message)) {
                    case EvaluationMsg:
                        return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_EVAL : MESSAGE_TYPE_SENT_EVAL;
                    case OrderMsg:
                        return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_ORDER : MESSAGE_TYPE_SENT_ORDER;
                    case TrackMsg:
                        return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TRACK : MESSAGE_TYPE_SENT_TRACK;
                    case FormMsg:
                        return message.direct() == Message.Direct.RECEIVE ? MESSAGE_TYPE_RECV_FORM : MESSAGE_TYPE_SENT_FORM;
                }
            }

            return -1;
        }

        @Override
        public ChatRow getCustomChatRow(Message message, int position, BaseAdapter adapter) {
            if (message.getType() == Message.Type.TXT) {
                switch (MessageHelper.getMessageExtType(message)) {
//                    case EvaluationMsg:
//                        return new ChatRowEvaluation(getActivity(), message, position, adapter);
                    case OrderMsg:
                        return new ChatRowOrder(getActivity(), message, position, adapter);
                    case TrackMsg:
                        return new ChatRowTrack(getActivity(), message, position, adapter);
//                    case FormMsg:
//                        return new ChatRowForm(getActivity(), message, position, adapter);
                }
            }
            return null;
        }
    }

}
