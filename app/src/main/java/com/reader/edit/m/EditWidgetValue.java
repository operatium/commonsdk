package com.reader.edit.m;

import android.os.Parcel;
import android.os.Parcelable;

import com.qicaibear.bookplayer.m.client.WidgetValue;

public class EditWidgetValue extends WidgetValue implements Parcelable {
    private int isEditable;//编辑状态 0=编辑  1=非编辑

    public EditWidgetValue(String myName, String viewType) {
        super(myName, viewType);
    }

    protected EditWidgetValue(Parcel in) {
        super(in);
        isEditable = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(isEditable);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<EditWidgetValue> CREATOR = new Creator<EditWidgetValue>() {
        @Override
        public EditWidgetValue createFromParcel(Parcel in) {
            return new EditWidgetValue(in);
        }

        @Override
        public EditWidgetValue[] newArray(int size) {
            return new EditWidgetValue[size];
        }
    };

    public int getIsEditable() {
        return isEditable;
    }

    public void setIsEditable(int isEditable) {
        this.isEditable = isEditable;
    }
}
