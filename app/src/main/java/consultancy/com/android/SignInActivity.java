package consultancy.com.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passEditText;
    private DatabaseHelper databaseHelper;
    TextView btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        emailEditText = (EditText) findViewById(R.id.edt_email);
        passEditText = (EditText) findViewById(R.id.edt_password);

        databaseHelper = new DatabaseHelper(this);

        btnSignIn = findViewById(R.id.btn_signin);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString();
                String pass = passEditText.getText().toString();
                if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email");
                } else if (!isValidPassword(pass)) {
                    passEditText.setError("Invalid Password");
                } else {
                    if (databaseHelper.checkUser(email, pass)) {
                        isUserLoggedIn(email, true);
                        Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                        startActivityForResult(intent, 0);
                    } else {
                        Toast.makeText(SignInActivity.this, "Invalid email or Password", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        TextView txtSignUp = findViewById(R.id.txt_createaccount);
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to redirect one activity into others
                Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        TextView txtforgottenpassword = findViewById(R.id.text_forgottenpassword);
        txtforgottenpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, ForgottenpasswordActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // validating password with retype password
    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }

    private void isUserLoggedIn(String email, boolean value) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.putBoolean("is_user_login", value);
        editor.apply();
    }

}

