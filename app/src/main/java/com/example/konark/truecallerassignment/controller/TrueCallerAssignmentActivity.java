package com.example.konark.truecallerassignment.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.konark.truecallerassignment.R;
import com.example.konark.truecallerassignment.constants.URLConstant;
import com.example.konark.truecallerassignment.model.AsyncTasks.HttpAsyncTask;
import com.example.konark.truecallerassignment.model.listeners.CallBackListener;


/**
 * Created by konark on 11/6/15.
 */

public class TrueCallerAssignmentActivity extends Activity implements CallBackListener {

    private ProgressDialog progressDialog;
    private Button AssignmentButton;
    private TextView textView10thCharacter;
    private TextView textViewEvery10thCharacter;
    private TextView textViewWordCounter;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_caller_assignment);
        setupToolbar();
        getLayoutElementViewsById();
    }

    /**
     * Method used for setting up toolbar.
     */
    public void setupToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.ripple_toolbar);

        // Inflate the title to be displayed in the toolbar
        toolbar.setTitle("Assignment Activity");
        toolbar.setTitleTextColor(Color.WHITE);
    }

    /*
   * Get layout elements For view used in XML
   */
    private void getLayoutElementViewsById() {
        textView10thCharacter = (TextView) findViewById(R.id.text_view);
        textViewEvery10thCharacter = (TextView) findViewById(R.id.text_view_every_10th_character);
        textViewWordCounter = (TextView) findViewById(R.id.text_view_word_counter);
        AssignmentButton = (Button) findViewById(R.id.assignment_button);
    }

    /*
   * Function called for calling html view from given url and is called from xml button click
   */
    public void getDataFromServer(View view) {

        if (isConnected()) {
            showProgressDialog("Loading data for you..");
            new HttpAsyncTask(TrueCallerAssignmentActivity.this, URLConstant.TRUE_CALLER_URL).execute();
        }else {
            showShortToast("Please connect to internet first");
        }

    }

    /*
     * Successful callback from the server
     */
    public void onSuccess(String response) {
        TrueCallerDataStructureFactory dataParsedPerProblemStatement = new TrueCallerDataStructureFactory();

        // Get an object of find10thChar from the Problem Statement Data Structure factory class.
        Find10thCharacter find10thChar = (Find10thCharacter)(dataParsedPerProblemStatement.getDataStructureType("find10thChar", response));
        StringBuilder tengthChar = find10thChar.getData();

        //get a map for every10thChar in the response from the Problem Statement Data Structure factory class.
        Every10thCharacter every10thCharacter = (Every10thCharacter)(dataParsedPerProblemStatement.getDataStructureType("every10thChar", response));
        StringBuilder every10thChar = every10thCharacter.getData();

        //get a map for words occurences in the response from the Problem Statement Data Structure factory class.
        WordsCount wordsOccurencesCount = (WordsCount)(dataParsedPerProblemStatement.getDataStructureType("wordsCount", response));
        StringBuilder wordsCount = wordsOccurencesCount.getData();

        hideProgressDialog();

        textView10thCharacter.setText(tengthChar);
        textViewEvery10thCharacter.setText(every10thChar);
        textViewWordCounter.setText(wordsCount);
    }

    /*
     * Failure callback from the server
     */
    public void onFailed(String message) {
        hideProgressDialog();
    }

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

    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    /**
     * Shows Toast message for short period of time.
     * @param toastMessage
     */
    public void showShortToast(String toastMessage) {
        if (toastMessage != null && toastMessage.length() > 0)
            Toast.makeText(this, toastMessage, Toast.LENGTH_SHORT).show();
    }


}