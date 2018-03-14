package com.tokang.customer.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tokang.customer.R;
import com.tokang.customer.interfaces.ItemClickListener;

/**
 * Created by royli on 2/16/2018.
 */

public class RenovationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public final TextView txtPackageName;
    public final TextView txtPricePackage;
    public final ImageView imagePackage;

    public final Button infoButton;

    private ItemClickListener itemClickListener;

    public RenovationViewHolder(View itemView) {
        super(itemView);

        txtPackageName = itemView.findViewById(R.id.package_name);
        txtPricePackage = itemView.findViewById(R.id.package_price);
        imagePackage = itemView.findViewById(R.id.image_package);
        infoButton = itemView.findViewById(R.id.info);

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
