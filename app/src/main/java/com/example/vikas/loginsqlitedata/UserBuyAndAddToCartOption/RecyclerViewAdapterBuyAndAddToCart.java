package com.example.vikas.loginsqlitedata.UserBuyAndAddToCartOption;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewAdapterBuyAndAddToCart extends RecyclerView.Adapter<RecyclerViewAdapterBuyAndAddToCart.ViewHolderClass> {

    @NonNull
    @Override
    public ViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderClass holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        public ViewHolderClass(View itemView) {
            super(itemView);
        }
    }
}
