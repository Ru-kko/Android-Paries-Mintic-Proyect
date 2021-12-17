package com.example.reto.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Party implements Parcelable {
    private int id;
    private int price;
    private String name;
    private String address;
    private String description;

    // null constructor
    public Party() {
    }

    public Party(int id, int price, String name, String address, String description) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.address = address;
        this.description = description;
    }

    protected Party(Parcel in) {
        id = in.readInt();
        price = in.readInt();
        name = in.readString();
        address = in.readString();
        description = in.readString();
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
    }
}
