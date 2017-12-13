package com.yacov.assessmentfundamentoandroid.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yacov.assessmentfundamentoandroid.R;
import com.yacov.assessmentfundamentoandroid.model.User;
import com.yacov.assessmentfundamentoandroid.view.MainActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, pass, passConfirm, cpf;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.regNameID);
        email = (EditText) findViewById(R.id.regEmailID);
        pass = (EditText) findViewById(R.id.regPasID);
        passConfirm = (EditText) findViewById(R.id.regPassConfirmID);
        cpf = (EditText) findViewById(R.id.regCpfID);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();


    }

    //OnClick sign up
    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration() {

        // Reset errors displayed in the form.
        email.setError(null);
        pass.setError(null);

        // Store values at the time of the login attempt.
        String emailString = email.getText().toString();
        String passwordString = pass.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(passwordString) && !isPasswordValid(passwordString)) {
            pass.setError("Invalid Password");
            focusView = pass;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailString)) {
            email.setError("Required Field");
            focusView = email;
            cancel = true;
        } else if (!isEmailValid(emailString)) {
            email.setError("Invalid Email");
            focusView = email;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            registerUser();
            createFirebaseUser();

        }
    }



    private boolean isEmailValid(String email) {
        // You can add more checking logic here.
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        String confirmPass = passConfirm.getText().toString();
        return confirmPass.equals(password) && password.length() >  3;
    }

    //Create a Firebase user
    private void createFirebaseUser(){

        String emailString = email.getText().toString();
        String passwordString = pass.getText().toString();
        mAuth.createUserWithEmailAndPassword(emailString, passwordString).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Assessment", "createUser onComplete: " + task.isSuccessful());

                if (!task.isSuccessful()){
                    Log.d("Assessment", "user creation failed " + task.getException());
                    showErrorDialog("Registration attempt failed" );
                }else {
                    Toast.makeText(getApplicationContext(), "Registered successfuled", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        });
    }

    public void registerUser(){

        String nameString = name.getText().toString();
        String emalString = email.getText().toString();
        String senhaString =pass.getText().toString();
        String cpfString = cpf.getText().toString();

        ref.push().setValue(new User(nameString, emalString, senhaString, cpfString));

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
