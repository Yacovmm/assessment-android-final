package com.yacov.assessmentfundamentoandroid.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.yacov.assessmentfundamentoandroid.R;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.emailLoginID);
        pass = (EditText) findViewById(R.id.passwordLoginID);

        mAuth= FirebaseAuth.getInstance();
    }

    public void signInExistingUser(View v)   {
        attemptLogin();

    }

    //Complete the attemptLogin() method
    private void attemptLogin() {

        String emailString = email.getText().toString();
        String passwordString = pass.getText().toString();

        if (emailString.equals("") || passwordString.equals("")) return;
        Toast.makeText(this, "Login in progress...", Toast.LENGTH_SHORT).show();

        // TODO: Use FirebaseAuth to sign in with email & password
        mAuth.signInWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Assessment", "signInWithEmail() OnComplete: " + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d("Assessment", "Problem signing in: " + task.getException());
                    showErrorDialog("Error " + task.getException());
                }else {
                    Intent intent = new Intent(getApplicationContext(), ActivityContent.class);
                    finish();
                    startActivity(intent);
                }

            }
        });
    }



    public void registerNewUser(View v) {
        Intent intent = new Intent(this, RegisterActivity.class);
        finish();
        startActivity(intent);
    }

    private void showErrorDialog(String message){

        new AlertDialog.Builder(this)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
