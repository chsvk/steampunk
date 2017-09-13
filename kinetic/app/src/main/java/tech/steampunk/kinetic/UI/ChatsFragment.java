package tech.steampunk.kinetic.UI;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Contact;
import tech.steampunk.kinetic.data.Message;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {

    private FloatingActionButton fab;
    private RecyclerView active_chats;
    private FirebaseRecyclerAdapter<Contact, ACviewHolder> firebaseRecyclerAdapter;
    private DatabaseReference chatsReference;

    public ChatsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_chats, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("AUTH", MODE_PRIVATE);
        final String UNumber = preferences.getString("Number", "Default_User");
        active_chats = v.findViewById(R.id.active_chats_list);
        chatsReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UNumber).child("Chats");
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        active_chats.setLayoutManager(mLayoutManager);
        active_chats.setItemAnimator(new DefaultItemAnimator());
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Contacts.class));
            }
        });
        try {
            chatsReference.keepSynced(true);

        }catch (Exception e){
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        try {
            firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Contact, ACviewHolder>(
                    Contact.class, R.layout.single_active_chat, ACviewHolder.class, chatsReference

            ) {
                @Override
                protected void populateViewHolder(ACviewHolder viewHolder, final Contact model, int position) {
                    viewHolder.msg(model);
                }
            };
            active_chats.setAdapter(firebaseRecyclerAdapter);
            firebaseRecyclerAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class ACviewHolder extends RecyclerView.ViewHolder{

        View view;
        public ACviewHolder(View itemView) {
            super(itemView);
            view=itemView;
        }

        public void msg(Contact contact){

            TextView name= view.findViewById(R.id.chat_name);
            TextView recentMessage = view.findViewById(R.id.chat_recent_message);
            name.setText(contact.getName());
            recentMessage.setText(contact.getMessage());
        }
    }
}
