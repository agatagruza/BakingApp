package com.udacity.bakingapp.views;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.DetailActivity;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.stepsArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionFragment extends Fragment {

    private SimpleExoPlayer myExoPlayer;
    private long myLastPlayerPosition;
    private boolean myLastPlayerState;
    private DetailActivity myDetailedActivity;
    private int myCurrentLocation;

    @BindView(R.id.video_pv)
    PlayerView mPlayerView;
    @BindView(R.id.thumbnail_container)
    ImageView mThumbnailView;

    @BindView(R.id.instruction_text)
    TextView mInstructionText;

    @BindView(R.id.navigation_container)
    FrameLayout mNavigationContainer;
    @BindView(R.id.prev_step_button)
    Button mPreviousButton;
    @BindView(R.id.next_step_button)
    Button mNextButton;
    @BindView(R.id.current_step_text)
    TextView mCurrentStepText;

    //Mandatory constructor for instantiating the fragment
    public InstructionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    //onCreateView is called when fragment that we just created is inflated
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup,
                             Bundle savedInstanceState) {
        final View myView =
                inflater.inflate(R.layout.fragment_instruction, viewGroup, false);
        ButterKnife.bind(this, myView);
        myDetailedActivity = (DetailActivity) getActivity();
        myCurrentLocation = myDetailedActivity.getLocation();
        String fullInstruction =
                myDetailedActivity.getStepsArrayList().get(myCurrentLocation).getFullDescription();
        mInstructionText.setText(fullInstruction);
        setThumbnailView(extractThumbnailLink());
        startPlayer(fullVideoURL());
        setToListen();
        reportViews();
        return myView;
    }

    public void setThumbnailView(String thumbnailURL) {
        Picasso.get()
                .load(Uri.parse(thumbnailURL))
                .placeholder(R.drawable.ic_cake_gold_56dp)
                .error(R.drawable.ic_cake_gold_56dp)
                .into(mThumbnailView);
    }


    public String extractThumbnailLink() {
        stepsArrayList currentStep = myDetailedActivity.getStepsArrayList().get(myCurrentLocation);
        return currentStep.getThumbnailURL();
    }


    //Based on ExoPlayer Android Developer Guide
    public void startPlayer(String videoUrl) {
        if (TextUtils.isEmpty(videoUrl)) {
            mPlayerView.setVisibility(View.GONE);
            mThumbnailView.setVisibility(View.VISIBLE);
        } else {
            mThumbnailView.setVisibility(View.GONE);
            mPlayerView.setVisibility(View.VISIBLE);
            Uri videoURI = Uri.parse(videoUrl);
            DefaultBandwidthMeter bandwidth = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidth);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);
            myExoPlayer =
                    ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            mPlayerView.setPlayer(myExoPlayer);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(getContext(),
                    Util.getUserAgent(getContext(), getString(R.string.app_name)), bandwidth);
            MediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(videoURI);
            myExoPlayer.prepare(mediaSource);
        }
    }

    public String fullVideoURL() {
        stepsArrayList currentStepArrayList = myDetailedActivity.getStepsArrayList().get(myCurrentLocation);
        String videoURL = currentStepArrayList.getVideoURL();
        if (TextUtils.isEmpty(videoURL)) {
            videoURL = currentStepArrayList.getThumbnailURL();
        }
        return videoURL;
    }

    public void reportViews() {

        String currentPositionText = "Step " + Integer.toString(myCurrentLocation) + " out of " + Integer.toString(myDetailedActivity.getStepsArrayList().size() - 1);
        mCurrentStepText.setText(currentPositionText);

        if (myCurrentLocation == 0) {
            //mPreviousButton.setVisibility(View.INVISIBLE);
            mPreviousButton.setEnabled(false);
            mPreviousButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_before_grey_56dp, 0, 0, 0);
            mNextButton.setVisibility(View.VISIBLE);
        } else if (myCurrentLocation == myDetailedActivity.getStepsArrayList().size() - 1) {
            mPreviousButton.setVisibility(View.VISIBLE);
            mPreviousButton.setEnabled(true);
            mPreviousButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_before_black_56dp, 0, 0, 0);
            mNextButton.setEnabled(false);
            mNextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_next_grey_56dp, 0, 0, 0);
            //mNextButton.setVisibility(View.INVISIBLE);
        } else {
            mPreviousButton.setVisibility(View.VISIBLE);
            mPreviousButton.setEnabled(true);
            mPreviousButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_before_black_56dp, 0, 0, 0);
            mNextButton.setVisibility(View.VISIBLE);
            mNextButton.setEnabled(true);
            mNextButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_navigate_next_black_56dp, 0, 0, 0);
        }
    }

    public void setToListen() {
        mPreviousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCurrentLocation > 0) {
                    myCurrentLocation--;
                }
                reportViews();
                resetState();
                onPositionChanged(myCurrentLocation);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myCurrentLocation < myDetailedActivity.getStepsArrayList().size() - 1) {
                    myCurrentLocation++;
                }
                reportViews();
                resetState();
                onPositionChanged(myCurrentLocation);
            }
        });
    }

    public void resetState() {
        myLastPlayerPosition = 0;
        myLastPlayerState = false;
    }

    public void onPositionChanged(int newPosition) {
        myCurrentLocation = newPosition;

        releaseExoPlayer();
        startPlayer(fullVideoURL());
        myExoPlayer.seekTo(myLastPlayerPosition);
        myExoPlayer.setPlayWhenReady(myLastPlayerState);

        if (myCurrentLocation > -1 &&
                myCurrentLocation < myDetailedActivity.getStepsArrayList().size()) {
            stepsArrayList newRecipeStep = myDetailedActivity.getStepsArrayList().get(myCurrentLocation);
            mInstructionText.setText(newRecipeStep.getFullDescription());
        }
        reportViews();
    }

    public void releaseExoPlayer() {
        if (myExoPlayer != null) {
            myExoPlayer.release();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if (myExoPlayer == null) {
            startPlayer(fullVideoURL());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (myExoPlayer == null) {
            startPlayer(fullVideoURL());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releaseExoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseExoPlayer();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle myBUNBLE) {
        super.onActivityCreated(myBUNBLE);
        if (myBUNBLE != null) {
            myLastPlayerState = myBUNBLE.
                    getBoolean(getString(R.string.player_state));
            myCurrentLocation = myBUNBLE.getInt(getString(R.string.position));
            myLastPlayerPosition = myBUNBLE.getLong(getString(R.string.player_location));
            onPositionChanged(myCurrentLocation);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle BUNDLE) {
        super.onSaveInstanceState(BUNDLE);
        BUNDLE.putInt(getString(R.string.position), myCurrentLocation);
        if (myExoPlayer != null) {
            BUNDLE.putBoolean(getString(R.string.player_state),
                    myExoPlayer.getPlayWhenReady());
            BUNDLE.putLong(getString(R.string.player_location), myExoPlayer.getCurrentPosition());
        }
    }
}
