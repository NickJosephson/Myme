package com.nitrogen.myme.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

import com.nitrogen.myme.R;
import com.nitrogen.myme.objects.Meme;
import com.nitrogen.myme.presentation.DisplayMemeActivity;

// Note that we specify the custom ViewHolder which gives us access to our views
public class MemesRecyclerAdapter extends RecyclerView.Adapter<MemesRecyclerAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView memeImageView;
        public Meme meme;
        public ImageView favouriteIconView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            itemView.setOnClickListener(this);
            memeImageView = (ImageView) itemView.findViewById(R.id.meme_image);
            favouriteIconView = (ImageView) itemView.findViewById(R.id.heartIcon);
        }

        public static final String EXTRA_MESSAGE = "com.nicholasjosephson.myfirstapp.MESSAGE";

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(view.getContext(), DisplayMemeActivity.class);
            intent.putExtra(DisplayMemeActivity.EXTRA_MESSAGE_MEME_ID, meme.getMemeID());
            view.getContext().startActivity(intent);
        }

    }

    // Store a member variable for the memes
    private List<Meme> memes;

    // Pass in the meme array into the constructor
    public MemesRecyclerAdapter(List<Meme> memes) {
        this.memes = memes;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public MemesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View memeView = inflater.inflate(R.layout.item_meme_thumbnail, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(memeView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull MemesRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Meme meme = memes.get(position);

        // Set item views based on your views and data model
        ImageView imageView = viewHolder.memeImageView;
        viewHolder.meme = meme;

        imageView.setImageURI(Uri.parse(meme.getThumbnailPath()));
        int vis = (meme.getIsFavourite()) ? View.VISIBLE : View.INVISIBLE;
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
    }
}
