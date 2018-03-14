package com.tokang.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.tokang.customer.R;
import com.tokang.customer.interfaces.ItemClickListener;
import com.tokang.customer.menu.Booking;
import com.tokang.customer.menu.MenuInfo;
import com.tokang.customer.model.RenovationModel;
import com.tokang.customer.utils.FormatUtils;
import com.tokang.customer.viewholders.RenovationViewHolder;

import java.math.BigInteger;
import java.util.List;

/**
 * Created by royli on 2/16/2018.
 */

public class RenovationAdapter extends RecyclerView.Adapter<RenovationViewHolder>{
    private final Context context;
    private final List<RenovationModel> renovations;

    public RenovationAdapter(Context context, List<RenovationModel> renovations) {
        this.context = context;
        this.renovations = renovations;
    }

    @Override
    public RenovationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_renovation, parent, false);

        return new RenovationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RenovationViewHolder holder, int position) {
        final RenovationModel clickItem = renovations.get(position);
        holder.txtPackageName.setText(clickItem.getName());
        Picasso.with(context).load(clickItem.getImage()).into(holder.imagePackage);
        String priceMin = FormatUtils.truncateFormat(clickItem.getPriceMinimum(), context);
        String priceMax = FormatUtils.truncateFormat(clickItem.getPriceMaximum(), context);

        String prices = priceMin+" ~ "+priceMax;
        holder.txtPricePackage.setText(prices);

        holder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent info = new Intent(context, MenuInfo.class);
                info.putExtra("Key", clickItem.getKey());
                info.putExtra("Name", clickItem.getName());
                info.putStringArrayListExtra("ImageDescriptions", clickItem.getImageDescription());
                info.putExtra("Description", clickItem.getDescription());
                context.startActivity(info);
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent booking = new Intent(context, Booking.class);
                booking.putExtra("ServiceKey", clickItem.getKey());
                context.startActivity(booking);
            }
        });
    }

    @Override
    public int getItemCount() {
        return renovations.size();
    }

}
