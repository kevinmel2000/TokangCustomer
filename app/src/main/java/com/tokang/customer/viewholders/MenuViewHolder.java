package com.tokang.customer.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tokang.customer.R;
import com.tokang.customer.interfaces.ItemClickListener;

/**
 * Created by royli on 1/28/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final ImageView imageLogo;
    public final TextView menuTagline;
    public final Button infoButton;
    public final RelativeLayout cardLayout;

    private ItemClickListener itemClickListener;

    public MenuViewHolder(View itemView) {
        super(itemView);

        imageLogo = itemView.findViewById(R.id.menu_logo);
        menuTagline = itemView.findViewById(R.id.menu_tagline);
        infoButton = itemView.findViewById(R.id.info);
        cardLayout = itemView.findViewById(R.id.card_layout);

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
