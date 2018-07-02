package com.nitrogen.myme.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import com.nitrogen.myme.R;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.objects.TemplateMeme;

// Note that we specify the custom ViewHolder which gives us access to our views
public class TemplatesRecyclerAdapter extends RecyclerView.Adapter<TemplatesRecyclerAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views in row and it's data
        public ImageView memeImageView;
        public ImageView favouriteIconView;
        public Meme meme;
        private OnItemClick mCallback;

        //**************************************************
        // Constructor
        //**************************************************

        // Constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnItemClick listener) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            memeImageView = (ImageView) itemView.findViewById(R.id.meme_image);
            favouriteIconView = (ImageView) itemView.findViewById(R.id.heartIcon);
            mCallback = listener;

            //set method to handle click events
            itemView.setOnClickListener(this);
        }

        //**************************************************
        // Event handlers
        //**************************************************

        @Override
        public void onClick(View view) {
            if(meme instanceof TemplateMeme)
                mCallback.getSource(((TemplateMeme)meme).getImagePath());
        }

    }

    // Store a member variable for the memes
    private List<Meme> memes;
    private OnItemClick mCallback;

    //**************************************************
    // Constructor
    //**************************************************

    // Pass in the meme array into the constructor
    public TemplatesRecyclerAdapter(List<Meme> memes, OnItemClick listener) {
        this.memes = memes;
        this.mCallback = listener;
    }

    //**************************************************
    // Required Recycler Methods
    //**************************************************

    // Inflate the layout from XML and returning the holder
    @NonNull
    @Override
    public TemplatesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View memeView = inflater.inflate(R.layout.item_meme_thumbnail, parent, false);

        // Return a new holder instance
        return new ViewHolder(memeView, mCallback);
    }

    // Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull TemplatesRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Meme meme = memes.get(position);

        // Set item views based on your views and data model
        ImageView imageView = viewHolder.memeImageView;
        viewHolder.meme = meme;

        imageView.setImageURI(Uri.parse(meme.getThumbnailPath()));
        int vis = (meme.isFavourite()) ? View.VISIBLE : View.INVISIBLE;
        viewHolder.favouriteIconView.setVisibility(vis);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return memes.size();
    }

    // mutator to update the memes that will be displayed
    public void updateMemeList(List<Meme> newMemeList) {
        this.memes = newMemeList;
        notifyDataSetChanged();
    }

}
