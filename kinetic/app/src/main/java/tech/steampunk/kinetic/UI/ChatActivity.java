package tech.steampunk.kinetic.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
    private DatabaseReference messageDatabase;
    private DatabaseReference oMessageDatabase;
    private DatabaseReference chatsReference;
    private DatabaseReference ochatsReference;
    private DatabaseReference notificationsReference;
    public static String UID;
    private FirebaseRecyclerAdapter<Message, viewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        Intent in = getIntent();

        SharedPreferences preferences = getSharedPreferences("AUTH", MODE_PRIVATE);

        conversation = new ArrayList<>();
        toolbar = findViewById(R.id.chat_activity_toolbar);
        setSupportActionBar(toolbar);
        final String name = in.getStringExtra("Name");
        UID = preferences.getString("UID","Default UID");
        final String UNumber = preferences.getString("Number", "Default_User");
        final String MNumber = in.getStringExtra("Number");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(name);

        messageDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(UNumber).child("Messages").child(MNumber);
        oMessageDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(MNumber).child("Messages").child(UNumber);
        chatsReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UNumber).child("Chats");
        ochatsReference = FirebaseDatabase.getInstance().getReference().child("Users").child(MNumber).child("Chats");
        notificationsReference = FirebaseDatabase.getInstance().getReference().child("Notifications");

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        conversation_list.setLayoutManager(mLayoutManager);
        conversation_list.setItemAnimator(new DefaultItemAnimator());
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
                    String newMinutes = String.valueOf(minutes);
                    if(String.valueOf(minutes).length() == 1){
                        newMinutes = "0" + String.valueOf(minutes);
                    }
                    String newTime = hours + ":" + newMinutes;
                    Message t= new Message(message.getText().toString().trim(), newTime, UID);
                    message.setText("");
                    HashMap<String, String> completeMessage = new HashMap<>();
                    completeMessage.put("Sender", UNumber);
                    completeMessage.put("Receiver", MNumber);
                    completeMessage.put("Message", t.getMessage());
                    completeMessage.put("UID", UID);
                    completeMessage.put("Time", newTime);
                    if(t.getType()!=null){
                        completeMessage.put("Type", t.getType());
                    }else{
                        completeMessage.put("Type", "Message");
                    }
                    HashMap<String, String> recentMessage = new HashMap<>();
                    recentMessage.put("name",name);
                    recentMessage.put("number",MNumber);
                    recentMessage.put("message",t.getMessage());

                    HashMap<String, String> oRecentMessage = new HashMap<>();
                    oRecentMessage.put("number", UNumber);
                    oRecentMessage.put("message", t.getMessage());
                    oRecentMessage.put("Type", "oRecentMessage");

                    HashMap<String, String> notification = new HashMap<>();
                    notification.put("Sender", UNumber);
                    notification.put("Message", t.getMessage());

                    messageDatabase.push().setValue(completeMessage);
                    oMessageDatabase.push().setValue(completeMessage);
                    chatsReference.child(MNumber).setValue(recentMessage);
                    ochatsReference.child(UNumber).setValue(oRecentMessage);
                    notificationsReference.child(MNumber).push().setValue(notification);

                    firebaseRecyclerAdapter.notifyDataSetChanged();
                    conversation.add(t);
                }
            }
        });
        try {
            messageDatabase.keepSynced(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        message.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        try {
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, viewHolder>(
                    Message.class, R.layout.single_message, viewHolder.class, messageDatabase

            ) {
                @Override
                protected void populateViewHolder(viewHolder viewHolder, final Message model, int position) {
                    viewHolder.msg(model);
                    conversation_list.smoothScrollToPosition(position);
                }
            };
            conversation_list.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class viewHolder extends RecyclerView.ViewHolder{

        View view;
        public viewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }

        public void msg(Message msg){

            TextView myMessage= view.findViewById(R.id.actual_message);
            TextView mytime = view.findViewById(R.id.message_time_stamp);
            TextView RMessage = view.findViewById(R.id.Ractual_message);
            TextView RmyTime = view.findViewById(R.id.Rmessage_time_stamp);
            CardView myCard = view.findViewById(R.id.single_message_card);
            CardView RCard = view.findViewById(R.id.Rsingle_message_card);
            if(UID == msg.getUID().trim()){
                myCard.setVisibility(View.GONE);
                RCard.setVisibility(View.VISIBLE);
                RMessage.setText(msg.getMessage());
                RmyTime.setText(msg.getTime());
            }else {
                RCard.setVisibility(View.GONE);
                myCard.setVisibility(View.VISIBLE);
                myMessage.setText(msg.getMessage());
                mytime.setText(msg.getTime());
            }

        }
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
