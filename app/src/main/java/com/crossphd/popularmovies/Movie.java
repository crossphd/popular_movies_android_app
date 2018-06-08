package com.crossphd.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;

public class Movie implements Parcelable {

//    private long mId;
    private String mTitle;
    private String mPosterImage;
    private String mOverview;
    private String mUserRating;
    private String mReleaseDate;
    private String mBackdrop;
    private long mID;

    public Movie(String mTitle, String mPosterImage, String mOverview, String mUserRating, String mReleaseDate, String mBackdrop, long mID) {

//        this.mId = mid;
        this.mTitle = mTitle;
        this.mPosterImage = mPosterImage;
        this.mOverview = mOverview;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
        this.mBackdrop = mBackdrop;
        this.mID = mID;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmPosterImage() {
        return mPosterImage;
    }

    public void setmPosterImage(String mPosterImage) {
        this.mPosterImage = mPosterImage;
    }

    public String getmOverview() {
        return mOverview;
    }

    public void setmOverview(String mOverview) {
        this.mOverview = mOverview;
    }

    public String getmUserRating() {
        return mUserRating;
    }

    public void setmUserRating(String mUserRating) {
        this.mUserRating = mUserRating;
    }

    public String getmReleaseDate() {
        return mReleaseDate;
    }

    public void setmReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getmBackdrop() {
        return mBackdrop;
    }

    public void setmBackdrop(String mBackdrop) {
        this.mBackdrop = mBackdrop;
    }

    public long getmID() {
        return mID;
    }

    public void setmID(long mID) {
        this.mID = mID;
    }


    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mTitle);
        dest.writeString(mPosterImage);
        dest.writeString(mOverview);
        dest.writeString(mUserRating);
        dest.writeString(mReleaseDate);
        dest.writeString(mBackdrop);
        dest.writeLong(mID);
    }

    private Movie(Parcel parcel){

        mTitle = parcel.readString();
        mPosterImage = parcel.readString();
        mOverview = parcel.readString();
        mUserRating = parcel.readString();
        mReleaseDate = parcel.readString();
        mBackdrop = parcel.readString();
        mID = parcel.readLong();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>(){

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[0];
        }
    };

}
