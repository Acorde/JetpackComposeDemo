package com.igor.jetpackcompose.view_pager.download;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class KeyValue implements ISearchable, Serializable {

    @SerializedName("Key")
    @Expose
    String key;

    @SerializedName("Value")
    @Expose
    String value;


    public KeyValue() {
    }

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getDisplayedName() {
        return value;
    }

    @Override
    public String getId() {
        return key;
    }

    @Override
    public String toString() {
        return value;
    }
}
