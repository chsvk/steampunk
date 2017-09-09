package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.Adapters.ConversationAdapter;
import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Message;

public class ChatActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @BindView(R.id.message_content)EditText message;
    @BindView(R.id.camera_intent)ImageView camera_action;
    @BindView(R.id.send_message)ImageView send_button;
    @BindView(R.id.conversation_list)RecyclerView conversation_list;
    private List<Message> conversation;
    private ConversationAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        conversation = new ArrayList<>();
        toolbar = (Toolbar)findViewById(R.id.chat_activity_toolbar);
        Intent in = getIntent();
        String name = in.getStringExtra("Name");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);
        messageAdapter = new ConversationAdapter(conversation);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        conversation_list.setLayoutManager(mLayoutManager);
        conversation_list.setItemAnimator(new DefaultItemAnimator());
        conversation_list.setAdapter(messageAdapter);
        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().isEmpty()){
                    Toast.makeText(ChatActivity.this, "Type a Message to send", Toast.LENGTH_SHORT).show();
                }else {
                    Date currentTime = Calendar.getInstance().getTime();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(currentTime);
                    int hours = calendar.get(Calendar.HOUR_OF_DAY);
                    int minutes = calendar.get(Calendar.MINUTE);
                    int seconds = calendar.get(Calendar.SECOND);
                    String newTime = hours + ":" + minutes;
                    Message t= new Message(message.getText().toString().trim(), newTime);
                    message.setText("");
                    conversation.add(t);
                    messageAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        message.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        super.onStart();
    }

    @Override
    protected void onStop() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        super.onStop();
    }

    @Override
    protected void onPause() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        super.onDestroy();
    }
}
