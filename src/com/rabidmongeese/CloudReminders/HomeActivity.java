package com.rabidmongeese.CloudReminders;

import android.app.Activity;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.plus.Plus;


public class HomeActivity extends Activity implements 
	OnClickListener, ConnectionCallbacks, OnConnectionFailedListener {
	
	private static final int RC_SIGN_IN = 0;
	
	/* Client to interact with the Google API */
	private GoogleApiClient mGoogleApiClient;
	
	private ConnectionResult mConnectionResult;
	/* Flag that keeps us from initiating other intents while 
	 * the PendingIntent is in progress */
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	
	private SignInButton bSignIn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.bSignIn).setOnClickListener(this);
        
        bSignIn = (SignInButton) findViewById(R.id.bSignIn);
        bSignIn.setOnClickListener(this);
        
        mGoogleApiClient = new GoogleApiClient.Builder(this)
		.addConnectionCallbacks(this)
		.addOnConnectionFailedListener(this).addApi(Plus.API, null)
		.addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bSignIn:
                signIn();
                break;
            default:
                break;
        }

    }
    
    private void signIn() {
		if (!mGoogleApiClient.isConnected()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}
    
    private void resolveSignInError() {
    	if (mConnectionResult.hasResolution()) {
    		try {
    			mIntentInProgress = true;
    			mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
    		} catch (SendIntentException e) {
    			mIntentInProgress = false;
    			mGoogleApiClient.connect();
    		}
    	}
    }

	protected void onStart() {
    	super.onStart();
    	mGoogleApiClient.connect();

    }
    
    protected void onStop() {
    	super.onStop();
    	if (mGoogleApiClient.isConnected()) {
    		mGoogleApiClient.disconnect();
    	}
    }

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(),
					this, 0).show();
			return;
		}
		
		if (!mIntentInProgress) {
			mConnectionResult = result;
			if (mSignInClicked) {
				resolveSignInError();
			}
		}
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mSignInClicked = false;
		Toast.makeText(this, "User successfuly connected", Toast.LENGTH_LONG).show();
		//getProfileInformation();
		
	}

	@Override
	public void onConnectionSuspended(int cause) {
		mGoogleApiClient.connect();
		
	}
}
