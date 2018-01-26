package com.levrsoft.carmudiapptest.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.levrsoft.carmudiapptest.R;
import com.levrsoft.carmudiapptest.main.model.CarmudiResponse;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joeyramirez on 1/26/18.
 */

public class CarRecyclerAdapter extends RecyclerView.Adapter<CarRecyclerAdapter.CarViewHolder> {


    //Global variables
    private Context mContext;
    private CarmudiResponse mCarmudiResponse;


    public CarRecyclerAdapter(Context mContext) {
        this.mContext = mContext;

    }

    public void setNewCarmudiData(CarmudiResponse carmudiData) {


        if(mCarmudiResponse != null){
            mCarmudiResponse.getMetadata().getResults().addAll(carmudiData.getMetadata().getResults());
        }else{
            mCarmudiResponse = carmudiData;
        }

        notifyDataSetChanged();
    }

    @Override
    public CarRecyclerAdapter.CarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_car, parent, false);


        return new CarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarRecyclerAdapter.CarViewHolder holder, int position) {

        holder.bindData(mCarmudiResponse.getMetadata().getResults().get(position));
    }

    @Override
    public int getItemCount() {
        try {
            return mCarmudiResponse.getMetadata().getResults().size();
        } catch (Exception e) {
            return 0;
        }

    }

    public void clearOldData() {

        mCarmudiResponse = null;


        notifyDataSetChanged();

    }

    public class CarViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_view_price)
        TextView textViewPrice;

        @BindView(R.id.text_view_brand)
        TextView textViewBrand;

        @BindView(R.id.text_view_model)
        TextView textViewModel;

        @BindView(R.id.image_view_car)
        ImageView imageViewCarPreview;

        public CarViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);


        }

        public void bindData(CarmudiResponse.MetadataBean.ResultsBean resultsBean) {

            CarmudiResponse.MetadataBean.ResultsBean.DataBean dataBean = resultsBean.getData();

            textViewBrand.setText(dataBean.getBrand());
            textViewPrice.setText(dataBean.getPrice());
            textViewModel.setText(dataBean.getBrand_model());


            Glide.with(mContext)
                    .load(resultsBean.getImages().get(0).getUrl())
                    .override(480, 480)
                    .crossFade()
                    .centerCrop()
                    .into(imageViewCarPreview);

        }
    }
}
