package com.example.huynhvannhan.game2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.huynhvannhan.game2.Adapter.ChatAdapter;
import com.example.huynhvannhan.game2.Object.ChatMessage;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityChat extends AppCompatActivity {

    private EditText messageET;
    private ListView messagesContainer;
    private Button sendBtn;
    private ChatAdapter adapter;
    private ArrayList<ChatMessage> chatHistory;
    private String ten="";
    private String id = "";
    private MyService mm = new MyService();
    private ChatMessage msgnew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = this.getIntent();
        ten = intent.getStringExtra("ten");
        id= intent.getStringExtra("id");
        initControls();

        mm.mSocket.emit("client-lay-dstinnhan",id);
        mm.mSocket.on("server-gui-dstinnhan",DanhSachTinNhan);
        mm.mSocket.on("server-gui-ndchat",NoiDungTinNhan);
        mm.mSocket.on("server-gui-ndchatx",NoiDungTinNhanX);
    }
    private void initControls() {
        messagesContainer = (ListView) findViewById(R.id.messagesContainer);
        messageET = (EditText) findViewById(R.id.messageEdit);
        sendBtn = (Button) findViewById(R.id.chatSendButton);

        TextView meLabel = (TextView) findViewById(R.id.meLbl);
        TextView companionLabel = (TextView) findViewById(R.id.friendLabel);
        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        companionLabel.setText(ten);
        loadDummyHistory();
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = messageET.getText().toString();
                JSONObject thongtin = new JSONObject();
                if (TextUtils.isEmpty(messageText)) {
                    return;
                }
                messageET.setText("");

                try {
                    msgnew = new ChatMessage();
                    thongtin.put("messagesBODY",messageText);
                    thongtin.put("messagesRECEIVER",id);
                    thongtin.put("messagesDATE",DateFormat.getDateTimeInstance().format(new Date()));

                    msgnew.setMe(true);
                    msgnew.setMessage(messageText);
                    msgnew.setDateTime(DateFormat.getDateTimeInstance().format(new Date()));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mm.mSocket.emit("client-gui-chat",thongtin);
            }
        });
    }
    public void displayMessage(ChatMessage message) {
        adapter.add(message);
        adapter.notifyDataSetChanged();
        scroll();
    }
    private void scroll() {
        messagesContainer.setSelection(messagesContainer.getCount() - 1);
    }
    private void loadDummyHistory(){

        chatHistory = new ArrayList<ChatMessage>();

        ChatMessage msg = new ChatMessage();
        msg.setId(1);
        msg.setMe(false);
        msg.setMessage("Hi");
        msg.setDateTime(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg);
        ChatMessage msg1 = new ChatMessage();
        msg1.setId(2);
        msg1.setMe(false);
        msg1.setMessage("How r u doing???");
        msg1.setDateTime(DateFormat.getDateTimeInstance().format(new Date()));
        chatHistory.add(msg1);

        adapter = new ChatAdapter(ActivityChat.this, new ArrayList<ChatMessage>());
        messagesContainer.setAdapter(adapter);

        for(int i=0; i<chatHistory.size(); i++) {
            ChatMessage message = chatHistory.get(i);
            displayMessage(message);
        }
    }
    ChatMessage msg ;
    private Emitter.Listener DanhSachTinNhan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONArray data = new JSONArray(args[0].toString());
                        if (data.length()!=0) {
                            for (int i = 0; i < data.length(); i++) {
                                JSONObject tv = new JSONObject(data.get(i).toString());
                                msg = new ChatMessage();
                                msg.setId(Integer.parseInt(tv.getString("messagesID")));
                                if (tv.getString("messagesSEND").equals(""+id))
                                {
                                    msg.setMe(false);
                                } else
                                {
                                    msg.setMe(true);
                                }
                                msg.setMessage(tv.getString("messagesBODY"));
                                msg.setDateTime(DateFormat.getDateTimeInstance().format(new Date()));
                                chatHistory.add(msg);
                                displayMessage(msg);
                            }
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener NoiDungTinNhan = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());
                        if (data.length()!=0) {
                            displayMessage(msgnew);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
    private Emitter.Listener NoiDungTinNhanX = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject data = new JSONObject(args[0].toString());
                        if (data.length()!=0) {
                            ChatMessage msgnew1 = new ChatMessage();
                            msgnew1.setMe(false);
                            msgnew1.setDateTime(data.getString("messagesDATE"));
                            msgnew1.setMessage(data.getString("messagesBODY"));
                            displayMessage(msgnew1);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    };
}
