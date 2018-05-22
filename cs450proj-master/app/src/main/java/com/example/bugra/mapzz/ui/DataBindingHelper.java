package com.example.bugra.mapzz.ui;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bugra.mapzz.R;

public class DataBindingHelper {

    @BindingAdapter( "profilePhotoUrl" )
    public static void loadImage( ImageView view, String uri ) {
        Context context = view.getContext();
        Glide.with( context )
                .load( uri )
                .apply( new RequestOptions()
                        .placeholder( R.drawable.profile )
                        .circleCrop()
                        .autoClone() )
                .into( view );
    }
}
