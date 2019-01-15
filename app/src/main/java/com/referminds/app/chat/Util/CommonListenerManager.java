package com.referminds.app.chat.Util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import com.google.gson.Gson;
import com.referminds.app.chat.Activity.MainActivity;
import com.referminds.app.chat.Model.Message;
import com.referminds.app.chat.Model.ServerMessage;
import com.referminds.app.chat.Model.User;
import com.referminds.app.chat.R;
import io.socket.client.Socket;

import java.util.ArrayList;
import java.util.List;

public class CommonListenerManager {
    private RecyclerView.Adapter mAdapter;
    private RecyclerView mMessagesView;
    private List<Message> mMessages;
    private Context context;

    public CommonListenerManager() {

    }

    public CommonListenerManager(Context mContext, List<Message> mMessages, RecyclerView.Adapter mAdapter, RecyclerView mMessagesView) {
        this();
        this.context = mContext;
        this.mAdapter = mAdapter;
        this.mMessages = mMessages;
        this.mMessagesView = mMessagesView;

    }

    public void addMessage(String username, String message, Boolean isMyMessage) {
        if (isMyMessage) {
            mMessages.add(new Message.Builder(Message.TYPE_MESSAGE)
                    .username(username).message(message).build());
            mAdapter.notifyItemInserted(mMessages.size() - 1);
        } else if (!isMyMessage) {
            mMessages.add(new Message.Builder(Message.TYPE_OTHER_MESSGE)
                    .username(username).message(message).build());
            mAdapter.notifyItemInserted(mMessages.size() - 1);
        }

        scrollToBottom(mMessagesView);
    }

    private void scrollToBottom(RecyclerView mMessagesView) {
        mMessagesView.scrollToPosition(mAdapter.getItemCount() - 1);
    }

    public void UpdateUserlist(ArrayList<User> userlist, AppCompatActivity mContext, Object... response) {
        userlist.clear();
        try {
            if (response[1].toString() != null && response[0].toString() != null) {
                String prefSocketId = ((MainActivity) mContext).getSession().getSoketId();
                String prefUserName = ((MainActivity) mContext).getSession().getUsername();
                String mSocketId = prefSocketId == null ? response[1].toString() : prefSocketId;
                String usersJSON = response[0].toString();
                Gson gson = new Gson();
                User users[] = gson.fromJson(usersJSON, User[].class);
                for (User user : users) {
                    if (!user.getSoketId().equals(mSocketId)) {
                        userlist.add(user);
                    }
                }
                ((MainActivity) mContext).createSession(prefUserName, mSocketId);
                Log.e(mContext.getString(R.string.socket_id), mSocketId);
            }


        } catch (Exception e) {
            Log.e("exception", e.getMessage());
        }

    }

    public void addTyping(String username, RecyclerView mMessagesView) {
        mMessages.add(new Message.Builder(Message.TYPE_ACTION)
                .username(username).build());
        mAdapter.notifyItemInserted(mMessages.size() - 1);
        // scrollToBottom(mMessagesView);
    }


    public void removeTyping(String username, List<Message> mMessages) {
        for (int i = mMessages.size() - 1; i >= 0; i--) {
            Message message = mMessages.get(i);
            if (message.getType() == Message.TYPE_ACTION && message.getUsername().equals(username)) {
                mMessages.remove(i);
                mAdapter.notifyItemRemoved(i);
            }
        }
    }

    public void sendMsgToOtherUser(String fromId, String toId, String msg, Socket mSocket) {
        ServerMessage userMessage = new ServerMessage(fromId, toId, msg);
        Gson gson = new Gson();
        mSocket.emit(context.getString(R.string.send_message), gson.toJson(userMessage));
        addMessage(((MainActivity) context).getSession().getUsername(), msg, true);
    }
}
