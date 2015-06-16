package com.example.konark.truecallerassignment.appControllers;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

/**
 * This is BaseActivity for all activities of app. Here we have defined some common operation
 * we usually perform like showToast and showProgress Dialog with proper message and openActivity
 * and openActivityForResults. This is very useful when we want to change whole app's activities.
 * For example from ActionBarActivity to FragmentActivity or Activity to ActionBarActivity.
 *
 */
public abstract class BaseActivity extends ActionBarActivity {
    private ProgressDialog progressDialog;

    /**
     * Show progress dialog with passed message.
     * @param message
     */
    public void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Hide progress dialog with proper handling of exception related to context leak.
     */
    public void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {

        } finally {
            progressDialog = null;
        }
    }

    /**
     * Shows Toast message for long Time.
     * @param toastMessage
     */
    public void showLongToast(String toastMessage) {
        if (toastMessage != null && toastMessage.length() > 0)
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
    }

    /**
     * Shows Toast message for short period of time.
     * @param toastMessage
     */
    public void showShortToast(String toastMessage) {
        if (toastMessage != null && toastMessage.length() > 0)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }

    /**
     * Boolean method to check internet connectivity
     */
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}
