package com.example.timeglimpse

import android.os.Parcel
import android.os.Parcelable
import java.util.Vector

data class Task(
   //var image:String?,
   var date: String?,
   var startTime: String?,
   var endTime: String?,
   var description: String?,
   var category: String?

) : Parcelable {
   constructor(parcel: Parcel) : this(
      //parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString(),
      parcel.readString()
   )

   override fun writeToParcel(parcel: Parcel, flags: Int) {
      parcel.writeString(date)
      parcel.writeString(startTime)
      parcel.writeString(endTime)
      parcel.writeString(description)
      parcel.writeString(category)
   }

   override fun describeContents(): Int {
      return 0
   }

   companion object CREATOR : Parcelable.Creator<Task> {
      override fun createFromParcel(parcel: Parcel): Task {
         return Task(parcel)
      }

      override fun newArray(size: Int): Array<Task?> {
         return arrayOfNulls(size)
      }
   }
}