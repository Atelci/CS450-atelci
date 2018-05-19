package com.example.bugra.mapzz.model;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {

    public String getContent() {
        return this.toString();
    }
}
