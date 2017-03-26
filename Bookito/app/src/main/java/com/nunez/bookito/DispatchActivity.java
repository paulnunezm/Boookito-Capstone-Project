package com.nunez.bookito;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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

  private static final String TAG        = "DispatchActivity";
  public static final int     RC_SIGN_IN = 25927;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    /** If this wasn't a non common dispatch activity we would change
     * the theme with setTheme() for removing the splashScreen theme
     * and setting the correct here.
     */

    FirebaseAuth auth = FirebaseAuth.getInstance();

    if (auth.getCurrentUser() != null) {
      // signed in
      Intent startMainActivityIntent = new Intent(this, BookListsActivity.class);
      startActivity(startMainActivityIntent);

    } else {
      // not signed in
      startActivityForResult(
          AuthUI.getInstance()
              .createSignInIntentBuilder()
              .setTheme(R.style.LoginTheme)
              .setProviders(Arrays.asList(new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build(),
                  new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
              .setLogo(R.mipmap.ic_launcher)
              .build(),
          RC_SIGN_IN);
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
          return;
        }

        if (response.getErrorCode() == ErrorCodes.NO_NETWORK) {
          Log.e(TAG, "onActivityResult: no_internet_connection");

          return;
        }

        if (response.getErrorCode() == ErrorCodes.UNKNOWN_ERROR) {
          Log.e(TAG, "onActivityResult: unknown_error");

          return;
        }
      }

      Log.e(TAG, "onActivityResult: unknown_sign_in_response");
    }
  }
}
