package com.referminds.app.chat.view.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.referminds.app.chat.R;
import com.referminds.app.chat.data.Model.SessionManager;
import com.referminds.app.chat.databinding.ActivityLoginBinding;
import com.referminds.app.chat.view.Utils.CommonSessionCall;
import com.referminds.app.chat.viewmodel.LoginViewModel;


/**
 * A login screen that offers login via username.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsernameView, password_input;
    private Button signuplink;
    private String username, pass;
    private Button signInButton;
    private SessionManager sessionManager;
    private CommonSessionCall commonSessionCallbck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        LoginViewModel viewModel = new LoginViewModel();
        binding.setLoginViewModel(viewModel);
        viewModel.init(this);

       // setContentView(R.layout.activity_login);
       // sessionManager = new SessionManager(this);
       // commonSessionCallbck = new CommonSessionCall(this);
       // intViews();
    }

    private void intViews() {
        mUsernameView = (EditText) findViewById(R.id.username_input);
        password_input = (EditText) findViewById(R.id.password_input);
        signuplink = findViewById(R.id.signuplink);
        signInButton = (Button) findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(this);
        signuplink.setOnClickListener(this);
    }

    private void signUp() {
        // Reset errors.
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        username = mUsernameView.getText().toString().trim();
        pass = password_input.getText().toString().trim();

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            mUsernameView.setError(getString(R.string.error_field_required));
            mUsernameView.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            password_input.setError(getString(R.string.error_field_required));
            password_input.requestFocus();
            return;
        }

     //   commonSessionCallbck.attemptLogin(username, pass);
    }


    public void onAuthenticationFailed() {
       // mUsernameView.setError(getString(R.string.wrong_username));
        //password_input.setError(getString(R.string.wrong_Password));
    }

    public void onAuthenticated() {
        sessionManager.createLoginSession(username, null);
        Intent loginIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(loginIntent);
        finish();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
               // signUp();

                break;
            case R.id.signuplink:
              //  navigateToSignin();
                break;
        }
    }

    private void navigateToSignin() {
        Intent signUpIntent = new Intent(LoginActivity.this, Registration.class);
        startActivity(signUpIntent);
    }
}


