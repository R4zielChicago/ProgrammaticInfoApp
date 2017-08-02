package com.example.tc2r.tinderhelptest.Activities;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tc2r.tinderhelptest.Adapters.ChapterAdapter;
import com.example.tc2r.tinderhelptest.Models.ChapterModel;
import com.example.tc2r.tinderhelptest.Models.InformationModel;
import com.example.tc2r.tinderhelptest.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by jarvischarles on 7/13/17.
 */

public class CustomActivity extends AppCompatActivity {

    private static final String TAG = "CustomActivity";


    // Recycleview stuff
    private ChapterAdapter chapterAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private InformationModel informationModel;
    private ArrayList<InformationModel> informationList;

    private InterstitialAd mInterstitialAd;


    private TextView titleText, subtitleText;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);

        Intent mIntent = getIntent();
        Gson gson = new Gson();

        //Get the Gson string from the previous activity and parse
        //it into the ChapterModel
        String strObj = mIntent.getStringExtra("model");
        ChapterModel chapter = gson.fromJson(strObj, ChapterModel.class);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4916179572672875/7392954612");
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("514641DDFB7ACF491C8B5B0057EB4B68")
                .build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                Intent myIntent = new Intent(CustomActivity.this, MainActivity.class);
                CustomActivity.this.startActivity(myIntent);
            }
        });


        titleText = (TextView) findViewById(R.id.custom_title);
        titleText.setText(chapter.getTitle());
        subtitleText = (TextView) findViewById(R.id.custom_subTitle);
        subtitleText.setText(chapter.getSubtitle());
        Toolbar mToolbar;
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        informationList = new ArrayList<InformationModel>();


        //ToolBar Stuff
        setSupportActionBar(mToolbar);
        final ActionBar actionBar = getSupportActionBar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Back");
        final Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_material);
        upArrow.setColorFilter(getResources().getColor(R.color.ColorPrimary), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        //Parse the strings into separate elements in an array
        String[] arrayOfImageUrls = chapter.getImage().split(",");
        ArrayList<String> imagesUrlList = new ArrayList<String>(Arrays.asList(arrayOfImageUrls));

        String[] arrayOfDescriptions = chapter.getDescription().split("`");
        ArrayList<String> descriptionsList = new ArrayList<String>(Arrays.asList(arrayOfDescriptions));


        //Pass the newly created Arrays to the informationModel


        for (int i = 0; i < descriptionsList.size(); i++) {
            informationModel = new InformationModel();
            informationModel.setDescription(descriptionsList.get(i));

            if (i < imagesUrlList.size()) {
                informationModel.setImageUrl(imagesUrlList.get(i));

            }

            informationList.add(informationModel);
        }


        chapterAdapter = new ChapterAdapter(informationList);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(false);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(chapterAdapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                try {
                    Random random = new Random();
                    int randomNumber = random.nextInt(3) + 1;
                    if (mInterstitialAd.isLoaded() && randomNumber == 2) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("TAG", "The interstitial wasn't loaded yet.");
                        super.onBackPressed();
                    }

                } catch (Exception e) {
                    Toast.makeText(this, "Back Button Clicked Error", Toast.LENGTH_LONG).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Random random = new Random();
        int randomNumber = random.nextInt(3) + 1;
        if (mInterstitialAd.isLoaded() && randomNumber == 2) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            super.onBackPressed();
        }
    }
}

