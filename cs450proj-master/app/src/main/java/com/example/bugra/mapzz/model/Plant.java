package com.example.bugra.mapzz.model;

public class Plant extends BaseModel {

    public enum TYPE {
        GREENERY,
        FARMING,
        FLOWERS
    }

    private float Lat, Lng;
    private TYPE type;
    private int success;
    private String ownerId;

    public Plant( float lat, float lng, TYPE type, int success, String ownerId ) {
        Lat = lat;
        Lng = lng;
        this.type = type;
        this.success = success;
        this.ownerId = ownerId;
    }

    public float getLat() {
        return Lat;
    }

    public void setLat( float lat ) {
        Lat = lat;
    }

    public float getLng() {
        return Lng;
    }

    public void setLng( float lng ) {
        Lng = lng;
    }

    public TYPE getType() {
        return type;
    }

    public void setType( TYPE type ) {
        this.type = type;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess( int success ) {
        this.success = success;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId( String ownerId ) {
        this.ownerId = ownerId;
    }
}
