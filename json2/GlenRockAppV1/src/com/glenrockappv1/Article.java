package com.glenrockappv1;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable{
	public String artId;
	public String title;
	public String text;
	public String dateAdded;
	//constructors
	public Article(String artId, String title, String text, String dateAdded){
		this.artId = artId;
		this.title = title.trim();
		this.text = text;
		this.dateAdded = dateAdded;
	}
	//article not including dateAdded
	public Article(String artId, String title, String text){
		this.artId = artId;
		this.title = title.trim();
		this.text = text;
		this.dateAdded = null;
	}
	public Article(Parcel source){
		artId = source.readString();
        title = source.readString().trim();
        text = source.readString();
  }
	public int describeContents(){
        return 0;
    }
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(artId);
		dest.writeString(title);
		dest.writeString(text);		
	}
	public class MyCreator implements Parcelable.Creator<Article> {
	      public Article createFromParcel(Parcel source) {
	            return new Article(source);
	      }

		@Override
		public Article[] newArray(int size) {
			return new Article[size];
		}
	}

}
