package com.tokang.customer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tokang.customer.R;
import com.tokang.customer.interfaces.ItemClickListener;
import com.tokang.customer.model.RecommendedItem;
import com.tokang.customer.viewholders.RecommendationViewHolder;

import java.util.List;

/**
 * Created by royli on 3/14/2018.
 */

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationViewHolder> {
    private final Context context;
    private final List<RecommendedItem> items;

    public RecommendationAdapter(Context context, List<RecommendedItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public RecommendationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);

        return new RecommendationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecommendationViewHolder holder, int position) {
        final RecommendedItem clickItem = items.get(position % items.size());

        Picasso.with(context).load(clickItem.getImage()).into(holder.image);
        holder.text.setText(clickItem.getText());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Toast.makeText(context, clickItem.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {

        return items == null ? 0 : items.size() * 10;
    }
}
