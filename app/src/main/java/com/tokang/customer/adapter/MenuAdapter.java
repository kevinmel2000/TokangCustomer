package com.tokang.customer.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tokang.customer.R;
import com.tokang.customer.constants.KeyConstants;
import com.tokang.customer.interfaces.ItemClickListener;
import com.tokang.customer.menu.MenuInfo;
import com.tokang.customer.menu.renovation.ToRenov;
import com.tokang.customer.model.Menu;
import com.tokang.customer.viewholders.MenuViewHolder;

import java.util.List;

/**
 * Created by royli on 1/28/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {
    private final Context context;
    private final List<Menu> menus;

    public MenuAdapter(Context context, List<Menu> menus) {
        this.context = context;
        this.menus = menus;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        final Menu clickItem = menus.get(position);

        Picasso.with(context).load(clickItem.getLogoDark()).into(holder.imageLogo);
        Picasso.with(context).load(clickItem.getMenuIcon()).into(holder.menuIcon);
        holder.menuTagline.setText(clickItem.getTagline());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                selectIntent(clickItem.getKey());
            }
        });

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
    }

    @Override
    public int getItemCount() {
        return menus.size();
    }

    private void selectIntent(String key){
        switch (key){
            case KeyConstants.RENOVATION_KEY:
                Intent toRenov = new Intent(context, ToRenov.class);
                context.startActivity(toRenov);
                break;
            default:
                Toast.makeText(context, key, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
