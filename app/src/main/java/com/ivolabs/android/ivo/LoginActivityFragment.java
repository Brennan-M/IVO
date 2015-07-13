package com.ivolabs.android.ivo;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.widget.LoginButton;
import android.content.Intent;
import android.widget.TextView;

import com.facebook.login.LoginResult;
import com.facebook.FacebookException;
import android.util.Log;


/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment {

    private TextView mTextDetails;
    private CallbackManager mCallbackManager;
    private AccessTokenTracker mTokenTracker;
    private ProfileTracker mProfileTracker;

    // Callback registration
    private FacebookCallback<LoginResult> mCallback = new FacebookCallback<LoginResult>() {

        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile(); // Stores much information about our FB user, be sure to read more about its capabilities here
                            // https://developers.facebook.com/docs/facebook-login/android/v2.4 , video here https://www.youtube.com/watch?v=myWu-q8Q2NA
            // mTextDetails.setText(constructWelcomeMessage(profile));
            Intent startHomescreenActivity = new Intent(LoginActivityFragment.this.getActivity(), Homescreen.class);
            startActivity(startHomescreenActivity);
        }

        @Override
        public void onCancel() {
            Log.d("IVOLogin", "onCancel");
        }

        @Override
        public void onError(FacebookException exception) {
            Log.d("IVOLogin", "onError " + exception);
        }
    };

    public LoginActivityFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext()); // Try taking out this line next time
        // Initialize the SDK before executing any other operations,
        // especially, if you're using Facebook UI elements.
        mCallbackManager = CallbackManager.Factory.create();
        setupTokenTracker();
        setupProfileTracker();

        mTokenTracker.startTracking();
        mProfileTracker.startTracking();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState); // Try taking out this line next time
        setupTextDetails(view);
        setupLoginButton(view);
    }

    private void setupTextDetails(View view) {
        mTextDetails = (TextView) view.findViewById(R.id.text_details);
    }

    private void setupLoginButton(View view) {
        LoginButton loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setFragment(this);
        loginButton.setReadPermissions("user_friends"); // THIS IS JUST A SAMPLE, DON'T REQUEST PERMISSIONS IF YOU DON'T NEED THEM, I DON'T THINK WE WILL
        loginButton.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        mTextDetails.setText(constructWelcomeMessage(profile));
    }

    @Override
    public void onStop() {
        super.onStop();
        mTokenTracker.stopTracking();
        mProfileTracker.stopTracking();
    }

    private void setupTokenTracker() {
        mTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                Log.d("IVO", "" + newAccessToken);
            }
        };
    }

    private void setupProfileTracker() {
        mProfileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                Log.d("IVO", "" + newProfile);
            }
        };
    }

    private String constructWelcomeMessage(Profile profile) { // This function can be changed to do whatever.
        StringBuffer stringBuffer = new StringBuffer();
        if (profile != null) {
            stringBuffer.append("Welcome " + profile.getName());
        }
        return stringBuffer.toString();
    }
}
