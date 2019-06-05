package consultancy.com.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileActivity extends AppCompatActivity {
    private EditText fristnameEditText, lastnameEditText, AddressEditText, MobileNumberEditText,
            EmailaddressEditText;
    private DatabaseHelper databaseHelper;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fristnameEditText = findViewById(R.id.edt_profileFirstName);
        lastnameEditText = findViewById(R.id.edt_profileLastName);
        AddressEditText = findViewById(R.id.edt_profileEmailAddress);
        MobileNumberEditText = findViewById(R.id.edt_profileMobileNo);
        EmailaddressEditText = findViewById(R.id.edt_profileEmailAddress);
        Button btnupdate = findViewById(R.id.btn_Update);
        databaseHelper = new DatabaseHelper(this);

        setDefaultdata();

        btnupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = fristnameEditText.getText().toString();
                final String lastname = lastnameEditText.getText().toString();
                final String address = AddressEditText.getText().toString();
                final String moblienumber = MobileNumberEditText.getText().toString();
                final String email = EmailaddressEditText.getText().toString();
                RadioGroup radioSexGroup = findViewById(R.id.rdg_profilegender);
                int selectedId = radioSexGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton = findViewById(selectedId);


                if (!isValidfristname(firstname)) {
                    fristnameEditText.setError("Invalid fristanme");
                } else if (!isValidlastname(lastname)) {
                    lastnameEditText.setError("Invalid lastanme");
//                } else if (!isValidEmail(email)) {
//                    EmailaddressEditText.setError("Invalid Email");
//                } else if (!isValidAddress(address)) {
//                    AddressEditText.setError("Invalid Address");
                } else if (!isvalidnumber(moblienumber)) {
                    MobileNumberEditText.setError("Invalid Moblienumber");
                } else {
                    User user = new User();
                    user.setEmail(email);
                    user.setFirst_name(firstname);
                    user.setLast_name(lastname);
                    user.setPhoneno(moblienumber);
                    user.setGender(radioSexButton.getText().toString());
                    boolean isupdateUser = databaseHelper.updateUser(user);
                    if (isupdateUser) {
                        Intent intent = new Intent(ProfileActivity.this, DashboardActivity.class);
                        startActivity(intent);
                    } else {

                        Toast.makeText(ProfileActivity.this, "Successfully Updted!!", Toast.LENGTH_SHORT).show();
                    }
                }

            }

        });
    }

    private void setDefaultdata() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String email = sharedPreferences.getString("email","");
        List<User> profileArrayList= databaseHelper.currentUser(email);
        fristnameEditText.setText(profileArrayList.get(0).getFirst_name());
    }
//
//    private boolean isValidEmail(String email) {
//        Pattern pattern;
//        Matcher matcher;
//        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
//        pattern = Pattern.compile(EMAIL_PATTERN);
//        matcher = pattern.matcher(email);
//        return matcher.matches();
//    }

    private boolean isValidlastname(String lastname) {
        return lastname.length() > 0;
    }

    private boolean isValidfristname(String fristname) {
        return fristname.length() > 0;

    }

//    private boolean isValidAddress(String address) {
//        return address.length() > 0;
//    }

    private boolean isvalidnumber(String MoblieNumber) {
        return MoblieNumber.length() >= 10;
    }
}


