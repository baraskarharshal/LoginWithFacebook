package com.apps.rdjsmartapps.loginwithfacebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;

    private static final String EMAIL = "email";
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        callbackManager = CallbackManager.Factory.create();

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                // do something
            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                displayProfile(newProfile);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        loginButton.setReadPermissions("email", "public_profile");
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Profile profile = Profile.getCurrentProfile();
                displayProfile(profile);
                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });

    } // end of onCreate

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    void displayProfile(Profile profile){
        if(profile != null){
            Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
            profileIntent.putExtra("id", profile.getId());
            profileIntent.putExtra("first_name", profile.getFirstName());
            profileIntent.putExtra("last_name", profile.getLastName());

            profileIntent.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            startActivity(profileIntent);
        }
    }
}
