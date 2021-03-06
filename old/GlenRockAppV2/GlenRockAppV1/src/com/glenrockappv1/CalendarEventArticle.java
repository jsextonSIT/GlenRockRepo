package com.glenrockappv1;

import android.os.Parcel;
import android.os.Parcelable;

public class CalendarEventArticle implements Parcelable{
	public int artId;
	public String title;
	public String desc;
	public String location;
	public String contact;
	public String time;
	public String date;
	//constructors
	public CalendarEventArticle(int artId, String title, String desc, String time, String contact, String location, String date){
		this.artId = artId;
		this.title = title;
		this.desc = desc;
		this.time = time;
		this.location = location;
		this.contact = contact;
		this.date = date;
	}
	
	public CalendarEventArticle(Parcel source){
		artId = source.readInt();
        title = source.readString();
        desc = source.readString();
        contact = source.readString();
        location = source.readString();
        time = source.readString();
        date = source.readString();
  }
	public int describeContents(){
        return 0;
    }
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(artId);
		dest.writeString(title);
		dest.writeString(desc);
		dest.writeString(location);
		dest.writeString(contact);
		dest.writeString(time);
		dest.writeString(date);
	}
	public class MyCreator implements Parcelable.Creator<CalendarEventArticle> {
	      public CalendarEventArticle createFromParcel(Parcel source) {
	            return new CalendarEventArticle(source);
	      }

		@Override
		public CalendarEventArticle[] newArray(int size) {
			return new CalendarEventArticle[size];
		}
	}

}
