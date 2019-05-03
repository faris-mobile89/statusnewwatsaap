package com.example.statusnewwatsaap;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewHolder extends RecyclerView.ViewHolder {
    View mView;
    public ViewHolder(View itemView) {
        super(itemView);

        mView=itemView;
        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        //item long click
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemLongClick(view,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String image){
        //  TextView mTitletv=mView.findViewById(R.id.rTitletv);
        // TextView mDecription=mView.findViewById(R.id.rDescritiontv);
        ImageView mImageView=mView.findViewById(R.id.rImageview);
        //  mTitletv.setText(title);
        //mDecription.setText(description);
        Picasso.get().load(image).into(mImageView);
    }
    private ViewHolder.ClickListener mClickListener;
    //interface to send callback
    public interface ClickListener{
        void onItemClick(View view,int position);
        void onItemLongClick(View  view,int position);

    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener)  {
        mClickListener = clickListener;
    }
}