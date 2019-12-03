package com.techno.takhdim.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.techno.takhdim.Adapter.ChatLVAdapter;
import com.techno.takhdim.App.AppConfig;
import com.techno.takhdim.Holder.DataHolder;
import com.techno.takhdim.R;
import com.techno.takhdim.Service.GetChatService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.techno.takhdim.App.MySharedPref.getData;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView send_chat_image_view,imgLeftMenu;
    String reciver_id, user_id;
    EditText send_chat_edit_text;
    String msg;
    public static final String mBroadcastGetChatData = "GetTeacherChatData";
    private IntentFilter mIntentFilter;
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(mBroadcastGetChatData)) {
                try {
                    ListView list = (ListView) findViewById(R.id.chatRecyclerView);
                    ChatLVAdapter adapter = new ChatLVAdapter(ChatActivity.this, DataHolder.getGetChat());
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        user_id = getData(this, "user_id", null);
        if (getIntent().getExtras() != null) {
            reciver_id = getIntent().getExtras().getString("reciver_id");
            Log.e("reciver_id", reciver_id);
            Log.e("sender_id", user_id);
            DataHolder.setReciver(reciver_id);
            DataHolder.setSender(user_id);
        }else {
            reciver_id="1";
            DataHolder.setReciver(reciver_id);
            DataHolder.setSender(user_id);
        }
        findId();
        send_chat_image_view.setOnClickListener(this);
        imgLeftMenu.setOnClickListener(this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(mBroadcastGetChatData);
        GetChatService.count = 0;
        startService(new Intent(this, GetChatService.class));

    }

    private void findId() {
        imgLeftMenu = (ImageView) findViewById(R.id.imgLeftMenu);
        send_chat_image_view = (ImageView) findViewById(R.id.send_chat_image_view);
        send_chat_edit_text = (EditText) findViewById(R.id.send_chat_edit_text);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }

    @Override
    public void onPause() {
        unregisterReceiver(mReceiver);
        super.onPause();
    }

    private void sendMsgCall() {
        Call<ResponseBody> call = AppConfig.loadInterface().insertChat(user_id, reciver_id, msg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.isSuccessful()) {
                        String responseData = response.body().string();
                        JSONObject object = new JSONObject(responseData);
                        System.out.println("Login Data :- " + object);
                        if (object.getString("status").equals("1")) {

                        } else {

                        }
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(ChatActivity.this, "Please Check Connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == imgLeftMenu) {
            finish();
        } else if (view == send_chat_image_view) {
            msg = send_chat_edit_text.getText().toString();
            if (msg.equalsIgnoreCase("")) {
                //do nathing
            } else {
                send_chat_edit_text.setText("");
                sendMsgCall();
            }
        }
    }
}