package tech.steampunk.kinetic.UI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.Adapters.SectionsPagerAdapter;
import tech.steampunk.kinetic.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int READ_CONTACTS = 1;
    private android.support.v7.widget.Toolbar toolbar;
    private ViewPager viewPager;
    private SectionsPagerAdapter sectionsPagerAdapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Kinetic");
        tabLayout = (TabLayout)findViewById(R.id.main_tabs);
        viewPager = (ViewPager)findViewById(R.id.ViewPager);
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_CONTACTS},
                READ_CONTACTS);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
