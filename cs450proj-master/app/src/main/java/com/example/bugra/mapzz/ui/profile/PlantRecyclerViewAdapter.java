package com.example.bugra.mapzz.ui.profile;

import android.arch.lifecycle.LifecycleOwner;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.example.bugra.mapzz.BR;
import com.example.bugra.mapzz.R;
import com.example.bugra.mapzz.model.Plant;
import com.example.bugra.mapzz.ui.common.Action;
import com.example.bugra.mapzz.ui.common.BasicActionType;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewAdapter;
import com.example.bugra.mapzz.ui.common.RecyclerView.BaseRecyclerViewModel;

public class PlantRecyclerViewAdapter extends BaseRecyclerViewAdapter<Plant, BasicActionType> {

    public PlantRecyclerViewAdapter( BaseRecyclerViewModel<Plant, BasicActionType> baseRecyclerViewModel, LifecycleOwner lifecycleOwner ) {
        super( baseRecyclerViewModel, lifecycleOwner );
    }

    @Override
    protected void setViewHolderBindings( ViewDataBinding binding, Plant model ) {
        binding.setVariable( BR.viewModel, new PlantItemViewModel( model ) );

        binding.getRoot().setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                mAction.postValue( new Action<>( model, BasicActionType.RECYCLER_ITEM_CLICK) );
            }
        } );
    }

    @Override
    protected int getItemViewId() {
        return R.layout.plant_item;
    }
}
