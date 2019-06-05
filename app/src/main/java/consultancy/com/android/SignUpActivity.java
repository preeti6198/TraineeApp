package consultancy.com.android;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    private EditText firstnameEditText;
    private EditText lastnameEditText;
    private EditText phonenumberEditText;
    private EditText emailEditText;
    private EditText passEditText;
    private EditText confirmationpassEditText;
    private DatabaseHelper databaseHelper;
    TextView txtSignIn;
    TextView btnSignUp;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstnameEditText = findViewById(R.id.edt_firstname);
        lastnameEditText = findViewById(R.id.edt_lastname);
        phonenumberEditText = findViewById(R.id.edt_phonenumber);
        emailEditText = findViewById(R.id.edt_emailaddress);
        passEditText = (EditText) findViewById(R.id.edt_password);
        confirmationpassEditText = (EditText) findViewById(R.id.edt_confirmationpassword);
        databaseHelper = new DatabaseHelper(this);


        Button btnsignup = findViewById(R.id.btn_signup);

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = firstnameEditText.getText().toString();
                final String lastname = lastnameEditText.getText().toString();
                final String phonenumber = phonenumberEditText.getText().toString();
                final String email = emailEditText.getText().toString();
                final String pass = passEditText.getText().toString();
                final String confirmationpass = confirmationpassEditText.getText().toString();
                RadioGroup radioSexGroup = findViewById(R.id.rdg_sex);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton = findViewById(selectedId);

                if (!isValidFirstname(firstname)) {
                    firstnameEditText.setError("Invalid Firstname");
                } else if (!isValidLastname(lastname)) {
                    lastnameEditText.setError("Invalid Lastname");
                } else if (!isValidPhonenumber(phonenumber)) {
                    phonenumberEditText.setError("Invalid Phonenumber");
                } else if (!isValidEmail(email)) {
                    emailEditText.setError("Invalid Email");
                } else if (!isValidPassword(pass)) {
                    passEditText.setError("Invalid Password");
                } else if (!isValidConfirmationpassword(pass, confirmationpass)) {
                    confirmationpassEditText.setError("Invalid Password");
                } else {
                    User user = new User();
                    user.setEmail(email);
                    user.setPassword(pass);
                    user.setFirst_name(firstname);
                    user.setLast_name(lastname);
                    user.setPhoneno(phonenumber);
                    user.setGender(radioSexButton.getText().toString());
                    if (databaseHelper.addUser(user)) {
                        Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignUpActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        TextView txtalreadyhaveanaccount = findViewById(R.id.txt_alreadyhaveanaccount);
        txtalreadyhaveanaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to redirect one activity into others
                Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    //validating Firstname
    private boolean isValidFirstname(String firstname) {
        return firstname.length() > 0;
    }

    //validating Lastname
    private boolean isValidLastname(String lastname) {
        return lastname.length() > 0;
    }

    //validating Phonenumber
    private boolean isValidPhonenumber(String phonenumber) {
        return phonenumber.length() == 10;
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

    private boolean isValidConfirmationpassword(String password, String confirmationpass) {
        if (confirmationpass.equals(password)) {
            return true;
        }
        return false;
    }
}