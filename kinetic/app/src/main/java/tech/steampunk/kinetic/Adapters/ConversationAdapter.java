package tech.steampunk.kinetic.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Message;

/**
 * Created by Vamshi on 9/9/2017.
 */

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.MyViewHolder> {

    private List<Message> conversation;

    public ConversationAdapter(List<Message> conversation) {
        this.conversation = conversation;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView actual_message, message_time_stamp;

        public MyViewHolder(View itemView) {
            super(itemView);
            actual_message = (TextView)itemView.findViewById(R.id.actual_message);
            message_time_stamp = (TextView)itemView.findViewById(R.id.message_time_stamp);
        }
    }

    @Override
    public ConversationAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_message, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ConversationAdapter.MyViewHolder holder, int position) {
        Message m = conversation.get(position);
        holder.actual_message.setText(m.getMessage());
        holder.message_time_stamp.setText(m.getTime());
    }

    @Override
    public int getItemCount() {
        return conversation.size();
    }
}
