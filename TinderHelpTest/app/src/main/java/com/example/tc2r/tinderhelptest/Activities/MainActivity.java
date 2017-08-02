package com.example.tc2r.tinderhelptest.Activities;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.tc2r.tinderhelptest.Models.ChapterModel;
import com.example.tc2r.tinderhelptest.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import static android.util.TypedValue.applyDimension;

public class MainActivity extends AppCompatActivity {


    //Tag for Debugging
    private static final String TAG = "MainActivity";

    //Context for the programmatic cardviews and textviews
    private Context mContext;

    //View to display the banner Ad
    private AdView mAdView;

    //Dynamic ArrayList to hold the chapterModels
    private ArrayList<ChapterModel> chapterArray;

    private LinearLayout adLinearLayout, containerLayout;

    private Gson gson;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Define the adView and load the adRequest
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("514641DDFB7ACF491C8B5B0057EB4B68")
                .build();
        mAdView.loadAd(adRequest);

        //Define the adLinearLayout and bring it to front
        adLinearLayout = (LinearLayout) findViewById(R.id.ad_linearLayout);
        adLinearLayout.bringToFront();
        adLinearLayout.invalidate();


        //Define the chapterArray as an empty ArrayList
        chapterArray = new ArrayList<>();

        //Call the getData Function
        getData();

    }


    /******************************************************************
     * getData is a function that creates an Async thread and handles *
     * all of the requests from the server and populates the          *
     * chapterArray with the appropriate amount of chapters.          *
     ******************************************************************/
    public void getData(){

        AsyncTask<String, Void, Void> task = new AsyncTask<String, Void, Void>() {

            @Override
            protected Void doInBackground(String... strings) {
                // Create client and request using OkHttp
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://r4zhost.000webhostapp.com/ResidentEvil7Walkthrough.php?")
                        //.url("https://tchost.000webhostapp.com/Resident_Evil_7_Walkthrough.php?")
                        .build();

                try{
                    //Check for a response, if received parse through it
                    Response response = client.newCall(request).execute();
                    Log.wtf(TAG, "Response = " + response);
                    response.body();
                    JSONArray array = new JSONArray(response.body().string());

                    //For how every element in the JSONArray create a new chapter
                    //in chapterArray, and populate it with the appropriate info
                    for (int i = 0; i < array.length(); i++){

                        Gson gson = new Gson();

                        ChapterModel chapter = gson.fromJson(array.get(i).toString(), ChapterModel.class);
                        chapterArray.add(chapter);
                        Log.wtf(TAG, "image = " + chapter.getImage());
                    }

                } catch (JSONException | IOException e){
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                //Call the initUI function and pass it the newly populated
                //chapterArray
                initUI(chapterArray);
            }
        };
        task.execute();
    }

    private void initUI(ArrayList<ChapterModel> chapterArray) {


        //For each element in the chapterArray create a tempChapter
        //call the createButton function and pass the tempChapter object
        for(ChapterModel tempChapter:chapterArray){
            createButton(tempChapter);
        }
    }


    /********************************************************************
     * createButton: is a function that takes an argument of tempChapter *
     * It programmatically creates the cardviewButtons and the textViews*
     * to fill them using the data parsed into the chapterModel         *
     ********************************************************************/
    public void createButton(final ChapterModel tempChapter){
        //Establish the context for creating our button
        mContext = this;

       //Create the cardView and set the params
        final CardView cardView = new CardView(mContext);
        LayoutParams params = new LayoutParams(290, 80);

        //Convert the height and width to pixels instead of dp
        int height = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, getResources().getDisplayMetrics());
        int width = (int) applyDimension(TypedValue.COMPLEX_UNIT_DIP, 290, getResources().getDisplayMetrics());
        params.height = height;
        params.width = width;

        //Set the margins of the cardview, and convert them to pixel using
        //the dpToPx function
        params.setMargins(0, dpToPx(5), 0, dpToPx(15));
        params.gravity = Gravity.CENTER;

        //Create the cardview and pass it the params we set up
        cardView.setLayoutParams(params);



        // Set CardView corner radius, elevation, padding, and our
        //cardView_background layout we set up that handles state pressed
        cardView.setRadius(9);

        cardView.setCardElevation(12);

        cardView.setPadding(0, dpToPx(10), 0, dpToPx(10));
        cardView.setBackgroundResource(R.drawable.cardview_background);



        // Initialize a new TextView with params to put in CardView
        TextView tv = new TextView(mContext);
        FrameLayout.LayoutParams tvParams = new FrameLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
                Gravity.CENTER);
        tv.setLayoutParams(tvParams);

        //Set the text to the value that we previously stored in the
        //title variable of tempChapter
        tv.setText(tempChapter.getTitle());

        //Set the textView to be compatible on any device and it will
        //show a standard size
        TextViewCompat.setTextAppearance(tv, android.R.style.TextAppearance_Medium);
        tv.setTextColor(Color.BLACK);

        //Add the textView to the cardView
        cardView.addView(tv);

        //Add the cardView to the linearLayout
        containerLayout = (LinearLayout) findViewById(R.id.container_linear_layout);
        containerLayout.addView(cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gson = new Gson();
                Intent myIntent = new Intent(MainActivity.this, CustomActivity.class);

                //Bundle the gson tempChapter data into the Intent to pass to
                //the next activity
                myIntent.putExtra("model", gson.toJson(tempChapter));
                MainActivity.this.startActivity(myIntent);

            }

        });

    }

    /******************************************************************
     *                                                                *
     * dpToPx: A function to convert dp units to pixels for formatting*
     ******************************************************************/
    private int dpToPx (int dp){

        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
        return px;
    }

}
