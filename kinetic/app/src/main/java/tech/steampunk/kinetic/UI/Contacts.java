package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.R;
import tech.steampunk.kinetic.data.Contact;

public class Contacts extends Activity  {

    private Cursor cursor;
    private String name;
    private String phonenumber;
    private List<Contact> StoreContacts;
    @BindView(R.id.contactTest)TextView contactTest;

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

        contactTest.setText(StoreContacts.get(0).getNumber());
    }

}
