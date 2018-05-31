package com.crud.singl.eyehealthv3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.crud.singl.eyehealthv3.helper.SQLiteHandler;
import com.crud.singl.eyehealthv3.helper.SessionManager;

import java.util.HashMap;

public class MemberProfileActivity extends AppCompatActivity {
    private TextView txtName;
    private TextView txtSurname;
    private TextView txtEmail;
    private Button btnLogout;

    private SQLiteHandler db;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.eye_activity_member_profile);

        //Tool bar back menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar_sign_up);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MemberProfileActivity.this,
                        MoresActivity.class);
                startActivity(intent);
            }
        });

        txtName = (TextView) findViewById(R.id.name);
        txtSurname = (TextView) findViewById(R.id.surname);
        txtEmail = (TextView) findViewById(R.id.email);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());

        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();

        String name = user.get("name");
        String surname = user.get("surname");
        String email = user.get("email");

        // Displaying the user details on the screen
        txtName.setText(name);
        txtSurname.setText(surname);
        txtEmail.setText(email);

        // Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }

    /**
     * Logging out the user. Will set isLoggedIn flag to false in shared
     * preferences Clears the user data from sqlite users table
     * */
    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MemberProfileActivity.this, SignInActivity.class);
        startActivity(intent);
        finish();
    }
}
