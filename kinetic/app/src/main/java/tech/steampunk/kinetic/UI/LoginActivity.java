package tech.steampunk.kinetic.UI;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import tech.steampunk.kinetic.R;

public class LoginActivity extends Activity {

    @BindView(R.id.Login) EditText login;
    @BindView(R.id.Password)EditText Password;
    @BindView(R.id.Register) Button Register;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login.getText().toString().isEmpty()||Password.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Please Fill The Required Fields", Toast.LENGTH_SHORT).show();
                }else{
                    String l = login.getText().toString().trim() + "@kinetic.com";
                    mAuth.createUserWithEmailAndPassword(l,Password.getText().toString())
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Toast.makeText(LoginActivity.this, "Registration Succesful!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        finish();
                                    }else {
                                        FirebaseAuthException e = (FirebaseAuthException )task.getException();
                                        Toast.makeText(LoginActivity.this, "Failed Registration: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                        return;
//                                        Toast.makeText(LoginActivity.this, "Registration Failed! Try Again", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }
}
