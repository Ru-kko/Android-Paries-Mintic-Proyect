package com.example.reto.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;

public class Party implements Parcelable {
    private int id;
    private int price;
    private String name;
    private String address;
    private String description;
    private Bitmap image;

    // null constructor
    public Party() {
    }

    public Party(int id, int price, String name, String address, String description, Bitmap image) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.address = address;
        this.description = description;
        this.image = image;
    }

    protected Party(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        address = in.readString();
        description = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Party> CREATOR = new Creator<Party>() {
        @Override
        public Party createFromParcel(Parcel in) {
            return new Party(in);
        }

        @Override
        public Party[] newArray(int size) {
            return new Party[size];
        }
    };

    public Bitmap getImage() {
        return image;
    }

    public byte[] getImageData(){
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, out);

        return out.toByteArray();
    }

    public void setImage(byte [] data){
         this.image = BitmapFactory.decodeByteArray(data, 0, data.length);

    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(price);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeString(description);
        parcel.writeParcelable(image, i);
    }
}
