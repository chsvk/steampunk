package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.Adapters.ContactList;
import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Contact;

public class Contacts extends Activity  {

    private Cursor cursor;
    private String name;
    private String phonenumber;
    private List<Contact> StoreContacts;
    @BindView(R.id.contactTest)TextView contactTest;
    private ContactList contactListAdapter;
    @BindView(R.id.contactList)ListView contactListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        StoreContacts = new ArrayList<>();
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Contact t = new Contact(name,phonenumber);
            StoreContacts.add(t);
        }

        cursor.close();

        contactListAdapter = new ContactList(this,StoreContacts);
        contactListView.setAdapter(contactListAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Contacts.this, StoreContacts.get(i).getName().toString(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
