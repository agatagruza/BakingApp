package com.udacity.bakingapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class stepsArrayList implements Parcelable {

    private int myID;
    private String myBriefDescription;
    private String myFullDescription;
    private String myVideoURL;
    private String myThumbnailURL;

    public int getmID() {
        return myID;
    }

    public String getBriefDescription() {
        return myBriefDescription;
    }

    public String getFullDescription() {
        return myFullDescription;
    }

    public String getVideoURL() {
        return myVideoURL;
    }

    public String getThumbnailURL() {
        return myThumbnailURL;
    }

    public void setmID(int myID) {
        this.myID = myID;
    }

    public void setBriefDescription(String mShortDescription) {
        this.myBriefDescription = mShortDescription;
    }

    public void setFullDescription(String mDescription) {
        this.myFullDescription = mDescription;
    }

    public void setVideoURL(String mVideoUrl) {
        this.myVideoURL = mVideoUrl;
    }

    public void setThumbnailURL(String mThumbnailUrl) {
        this.myThumbnailURL = mThumbnailUrl;
    }

    public stepsArrayList(int id, String briefDescription,
                          String fullDescription, String videoURL, String thumbnailURL) {
        myID = id;
        myBriefDescription = briefDescription;
        myFullDescription = fullDescription;
        myVideoURL = videoURL;
        myThumbnailURL = thumbnailURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<stepsArrayList> CREATOR
            = new Parcelable.Creator<stepsArrayList>() {
        @Override
        public stepsArrayList createFromParcel(Parcel source) {
            return new stepsArrayList(source);
        }

        @Override
        public stepsArrayList[] newArray(int size) {
            return new stepsArrayList[size];
        }
    };

    private stepsArrayList(Parcel in) {
        myID = in.readInt();
        myBriefDescription = in.readString();
        myFullDescription = in.readString();
        myVideoURL = in.readString();
        myThumbnailURL = in.readString();
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(myID);
        out.writeString(myBriefDescription);
        out.writeString(myFullDescription);
        out.writeString(myVideoURL);
        out.writeString(myThumbnailURL);
    }
}
