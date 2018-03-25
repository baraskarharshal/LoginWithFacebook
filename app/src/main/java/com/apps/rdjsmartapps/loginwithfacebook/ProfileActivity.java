package com.apps.rdjsmartapps.loginwithfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;

public class ProfileActivity extends AppCompatActivity {

    TextView name;
    Button log_out;
    ProfilePictureView profilePictureView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.name);
        log_out = (Button) findViewById(R.id.log_out);
        profilePictureView = (ProfilePictureView) findViewById(R.id.friendProfilePicture);

        Bundle inBundle = getIntent().getExtras();
        String id = inBundle.get("id").toString();
        String first_name = inBundle.get("first_name").toString();
        String last_name = inBundle.get("last_name").toString();
        String imageUrl1 = inBundle.get("imageUrl").toString();

        // Set profile values

        name.setText("Name: "+ first_name+" "+last_name);
        profilePictureView.setProfileId(id);


        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logOut();
                Intent login = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(login);
                finish();
            }
        });
    }
}
