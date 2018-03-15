package com.tokang.customer.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tokang.customer.R;
import com.tokang.customer.interfaces.ItemClickListener;

/**
 * Created by royli on 3/14/2018.
 */

public class RecommendationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final ImageView image;
    public final TextView text;

    private ItemClickListener itemClickListener;

    public RecommendationViewHolder(View itemView) {
        super(itemView);

        image = itemView.findViewById(R.id.image_recommendation);
        text = itemView.findViewById(R.id.recommendation_name);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
