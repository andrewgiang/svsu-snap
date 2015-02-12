package com.detroitlabs.snapit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.detroitlabs.snapit.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        startMainActivityIfLoggedIn();
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.submitButton).setOnClickListener(this);

    }

    private void startMainActivityIfLoggedIn() {
        if (ParseUser.getCurrentUser() != null) {
            Intent i = new Intent(this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(i);
        }
    }

    @Override
    public void onClick(View v) {
        if(formNotEmpty()) {
            ParseUser.logInInBackground(username.getText().toString().toLowerCase(), password.getText().toString(), getLoginCallback());
        }
    }

    private LogInCallback getLoginCallback() {
        return new LogInCallback() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser != null) {
                    startMainActivityIfLoggedIn();
                } else {
                    if(e.getCode() == ParseException.OBJECT_NOT_FOUND){
                        registerUser();
                    }
                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void registerUser() {
        ParseUser user = new ParseUser();
        if(formNotEmpty()){
            user.setUsername(username.getText().toString());
            user.setPassword(password.getText().toString());
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if(e == null){
                        startMainActivityIfLoggedIn();
                    }
                }
            });
        }

    }

    private boolean formNotEmpty() {
        return !TextUtils.isEmpty(username.getText()) && !TextUtils.isEmpty(password.getText());
    }

}
