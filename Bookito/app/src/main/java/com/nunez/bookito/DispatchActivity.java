package com.nunez.bookito;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.ResultCodes;
import com.google.firebase.auth.FirebaseAuth;
import com.nunez.bookito.bookLists.BookListsActivity;

import java.util.Arrays;

/**
 * Created by paulnunez on 3/24/17.
 */

public class DispatchActivity extends AppCompatActivity {

  public static final  int    RC_SIGN_IN = 25927;
  private static final String TAG        = "DispatchActivity";
  boolean errorShown = false;
  private View container;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.dispacth_activity);

    container = findViewById(R.id.dispatcher_container);

    /** If this wasn't a non common dispatch activity we would change
     * the theme with setTheme() for removing the splashScreen theme
     * and setting the correct here.
     */

    FirebaseAuth auth = FirebaseAuth.getInstance();

    if (auth != null && auth.getCurrentUser() != null) {

      // signed in
      Intent startMainActivityIntent = new Intent(this, BookListsActivity.class);
      startMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

      startActivity(startMainActivityIntent);

    } else {
      // not signed in
      goToLoginActivity();
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (requestCode == RC_SIGN_IN) {

      IdpResponse response = IdpResponse.fromResultIntent(data);

      // Successfully signed in
      if (resultCode == ResultCodes.OK) {
        Intent startMainActivityIntent = new Intent(this, BookListsActivity.class);
        startActivity(startMainActivityIntent);
        finish();
        return;
      } else {
        // Sign in failed
        if (response == null) {
          // User pressed back button
          Log.e(TAG, "onActivityResult: sign_in_cancelled");
          showErrorMessage("To bad... It seems like you cancelled :/");
        }

        if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
          Log.e(TAG, "onActivityResult: no_internet_connection");
          showErrorMessage("Ups! There's no internet connection");
        }

        if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
          Log.e(TAG, "onActivityResult: unknown_error");
          showErrorMessage("Woot! Something unexpected just happend.");
        }
      }

      if (!errorShown) {
        Log.e(TAG, "onActivityResult: unknown_sign_in_response");
        showErrorMessage("Woot! Something unexpected just happend.");
      }

      new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
          finish(); // TODO: CHANGE THIS
        }
      }, Snackbar.LENGTH_SHORT);

    }
  }

  private void showErrorMessage(String message) {
    errorShown = true;
    Snackbar.make(container, message, Snackbar.LENGTH_SHORT).show();
  }

  private void goToLoginActivity() {
    errorShown = false;

    startActivityForResult(
        AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.LoginTheme)
            .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
            .setLogo(R.drawable.bookito_logo)
            .build(),
        RC_SIGN_IN);
  }
}

