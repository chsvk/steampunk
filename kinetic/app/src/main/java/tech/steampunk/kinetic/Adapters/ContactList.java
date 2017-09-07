package tech.steampunk.kinetic.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.UI.Contacts;
import tech.steampunk.kinetic.data.Contact;

/**
 * Created by Vamshi on 9/7/2017.
 */

public class ContactList extends ArrayAdapter<Contact> {

    private List<Contact> tContacts;
    private Context context;

    public ContactList(@NonNull Context context,List<Contact> contacts) {
        super(context,R.layout.single_contact, contacts);
        this.context = context;
        this.tContacts = contacts;
    }

    @Override
    public int getCount() {
        return tContacts.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View vi = inflater.inflate(R.layout.single_contact, parent, false);
        ImageView contactDP = (ImageView) vi.findViewById(R.id.contactDP);
        TextView contactName = (TextView) vi.findViewById(R.id.contactName);
        TextView contactNumber = (TextView) vi.findViewById(R.id.contactNumber);
        ImageView contactInvite = (ImageView) vi.findViewById(R.id.ContactInvite);
        Contact t = tContacts.get(position);
        contactName.setText(t.getName());
        contactNumber.setText(t.getNumber());
        contactInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Invite This Contact!", Toast.LENGTH_SHORT).show();
            }
        });
        return vi;
    }

}
