package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.Adapters.ContactList;
import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Contact;

public class Contacts extends AppCompatActivity  {

    private Cursor cursor;
    private String name;
    private String phonenumber;
    private List<Contact> StoreContacts;
    private ContactList contactListAdapter;
    @BindView(R.id.contactList)ListView contactListView;
    private Set<Contact> hashSet;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        toolbar = (Toolbar)findViewById(R.id.contacts_activity_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Contacts");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        StoreContacts = new ArrayList<>();
        hashSet = new LinkedHashSet<>();
        fetchContacts();
    }

    public void fetchContacts(){
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).trim();

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();
            phonenumber = phonenumber.replace(" ", "");
            if (phonenumber.length() >= 10 && phonenumber.length() <= 13) {
                if (phonenumber.startsWith("+91")) {

                } else {
                    if (phonenumber.length() == 10) {
                        phonenumber = "+91" + phonenumber;
                    }
                    if (phonenumber.length() == 11) {
                        phonenumber = phonenumber.replaceFirst("0", "");
                        phonenumber = "+91" + phonenumber;
                    }
                }

                Contact t = new Contact(name, phonenumber);
                StoreContacts.add(t);
            }
        }
        hashSet.addAll(StoreContacts);
        StoreContacts.clear();
        StoreContacts.addAll(hashSet);
        cursor.close();
        hasDuplicates(StoreContacts);
        contactListAdapter = new ContactList(this,StoreContacts);
        contactListView.setAdapter(contactListAdapter);
        contactListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in = new Intent(Contacts.this, ChatActivity.class);
                Contact temp = StoreContacts.get(i);
                in.putExtra("Name", temp.getName());
                in.putExtra("Number", phonenumber);
                startActivity(in);
                Toast.makeText(Contacts.this, StoreContacts.get(i).getName().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hasDuplicates(List<Contact> contacts) {
        final List<String> usedNames = new ArrayList<String>();
        Iterator<Contact> it = contacts.iterator();
        while (it.hasNext()) {
            Contact car = it.next();
            final String name = car.getNumber();

            if (usedNames.contains(name)) {
                it.remove();

            } else {
                usedNames.add(name);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
