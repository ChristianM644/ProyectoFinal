package com.example.projectandroid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectandroid.network.CatalogueEntry;
import com.example.projectandroid.network.ImageRequest;

import java.util.List;

public class CatalogueCardRecyclerViewAdapter extends RecyclerView.Adapter<CatalogueCardViewHolder>
{
    private List<CatalogueEntry> productList;
    private ImageRequest imageRequest;

    CatalogueCardRecyclerViewAdapter(List<CatalogueEntry> productList) {
        this.productList = productList;
        imageRequest = ImageRequest.getInstance();
    }

    @NonNull
    @Override
    public CatalogueCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.catalogue_card, parent, false);
        return new CatalogueCardViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull CatalogueCardViewHolder holder, int position) {
        if (productList != null && position < productList.size()) {
            CatalogueEntry product = productList.get(position);
            holder.clothesTitle.setText(product.title);
            holder.clothesPrice.setText(product.price);


            imageRequest.setImageFromUrl(holder.clothesImage, product.url);
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
