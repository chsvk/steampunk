package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.content.Context;
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

public class Contacts extends Activity  {

    private Cursor cursor;
    private String name;
    private String phonenumber;
    private List<Contact> StoreContacts;
    @BindView(R.id.contactTest)TextView contactTest;
    private ContactList contactListAdapter;
    @BindView(R.id.contactList)ListView contactListView;
    private Set<Contact> hashSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        StoreContacts = new ArrayList<>();
        hashSet = new LinkedHashSet<>();
        fetchContacts();
    }

    public void fetchContacts(){
        cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null, null, null);

        while (cursor.moveToNext()) {

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).trim();

            phonenumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).trim();

            Contact t = new Contact(name,phonenumber);
            StoreContacts.add(t);
        }
        hashSet.addAll(StoreContacts);
        StoreContacts.clear();
        StoreContacts.addAll(hashSet);
        cursor.close();
        hasDuplicates(StoreContacts);
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
        setDisplay();
    }

    public void setDisplay(){
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
