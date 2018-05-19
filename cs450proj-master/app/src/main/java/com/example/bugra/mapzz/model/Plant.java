package com.example.bugra.mapzz.model;

public class Plant extends BaseModel {

    public enum TYPE {
        GREENERY(0),
        FARMING(1),
        FLOWER(2);

        private final String string;

        private TYPE( int id ) {
            switch( id ) {
                case 0:
                    string = "Yeşil Yapraklı";
                    break;
                case 1:
                    string = "Tarım";
                    break;
                case 2:
                    string = "Çiçek";
                    break;
                default:
                    string = "";
            }
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private float lat, lng;
    private TYPE type;
    private String plantName;
    private int success;
    private String ownerId;

    public Plant() {}

    public Plant( float lat, float lng, TYPE type, String plantName, int success, String ownerId ) {
        this.lat = lat;
        this.lng = lng;
        this.type = type;
        this.plantName = plantName;
        this.success = success;
        this.ownerId = ownerId;
    }

    public float getLat() {
        return lat;
    }

    public void setLat( float lat ) {
        this.lat = lat;
    }

    public float getLng() {
        return lng;
    }

    public void setLng( float lng ) {
        this.lng = lng;
    }

    public TYPE getType() {
        return type;
    }

    public void setType( TYPE type ) {
        this.type = type;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName( String plantName ) {
        this.plantName = plantName;
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
