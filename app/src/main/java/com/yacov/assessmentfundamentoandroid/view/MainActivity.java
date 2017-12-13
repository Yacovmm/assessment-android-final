package com.yacov.assessmentfundamentoandroid.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.yacov.assessmentfundamentoandroid.R;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText email, pass;

    private FirebaseAuth mAuth;
    private CallbackManager mCallBackManager;
    private Button btnFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = (EditText) findViewById(R.id.emailLoginID);
        pass = (EditText) findViewById(R.id.passwordLoginID);
        btnFacebook = (Button) findViewById(R.id.faceBtnID);

        FacebookSdk.setApplicationId("135398787133529");
        FacebookSdk.sdkInitialize(getApplicationContext());

        mAuth= FirebaseAuth.getInstance();

        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    String username = user.getDisplayName();
                    Toast.makeText(getApplicationContext(), "Welcome " + username, Toast.LENGTH_SHORT).show();
                }
            }
        });

        mCallBackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallBackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AuthCredential credential = FacebookAuthProvider
                        .getCredential(loginResult.getAccessToken().getToken());
                signInCredential(credential);
            }

            @Override
            public void onCancel() {
                // O user cancelou o login enquanto carregava
            }

            @Override
            public void onError(FacebookException error) {
                //Ocorreu um erro ao tentar fazer logi
            }
        });

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(MainActivity.this,
                        Arrays.asList("public_profile", "email"));
            }
        });

    }

    private void signInCredential(AuthCredential credential)
    {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser usuario = mAuth.getCurrentUser();
                            // todos os dados do usuário ficam guardados na variavel usuario.
                            Intent intent = new Intent(getApplicationContext(), ActivityContent.class);
                            finish();
                            startActivity(intent);

                        }
                        else
                        {
                            //O login falhou
                            Log.d("Assessment", "Problem signing in: " + task.getException());
                            showErrorDialog("Error " + task.getException());
                        }
                    }
                });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
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
