package com.example.bugra.mapzz.ui.common;


import com.example.bugra.mapzz.model.BaseModel;

public class Action<Model extends BaseModel, ActionType> {

    private Model mModel;
    private ActionType mActionType;

    public Action( Model model, ActionType actionType ) {
        mModel = model;
        mActionType = actionType;
    }

    public Model getModel() {
        return mModel;
    }

    public ActionType getActionType() {
        return mActionType;
    }
}
