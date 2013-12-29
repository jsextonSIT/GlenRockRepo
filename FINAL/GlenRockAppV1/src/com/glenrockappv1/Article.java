package com.glenrockappv1;

import android.os.Parcel;
import android.os.Parcelable;
/* Class for each news article item
 * Contains id, title, text and dateAdded
 * is parcelable to allow passing from mainactivity to fragment
 */
public class Article implements Parcelable{
	public String artId;
	public String title;
	public String text;
	public String dateAdded;
	//constructors
	//input arguments: id, title, text and dateAdded
	public Article(String artId, String title, String text, String dateAdded){
		this.artId = artId;
		this.title = title.trim();
		this.text = text;
		this.dateAdded = dateAdded;
	}
	//article not including dateAdded constructor
	public Article(String artId, String title, String text){
		this.artId = artId;
		this.title = title.trim();
		this.text = text;
		this.dateAdded = null;
	}
	//generic android constructor used for getting back the data in the parcel
	//input: parcel
	//output: arguments held in the parcel
	public Article(Parcel source){
		artId = source.readString();
        title = source.readString().trim();
        text = source.readString();
  }
	//generic android function with necessary definition, but unused
	public int describeContents(){
        return 0;
    }
	//input:A parcel to write to, any set flags
	//output: all the fields of the article written to the parcel
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(artId);
		dest.writeString(title);
		dest.writeString(text);		
	}
	public class MyCreator implements Parcelable.Creator<Article> {
		//generic function for changing a parcel to its individual components
		//input: a parcel to get data from
		//output: a article with that data
	      public Article createFromParcel(Parcel source) {
	            return new Article(source);
	      }
	      //generic function for creating an array of articles
	      //input: size of array
	      //output: newly allocated Article array of that size
		@Override
		public Article[] newArray(int size) {
			return new Article[size];
		}
	}

}
