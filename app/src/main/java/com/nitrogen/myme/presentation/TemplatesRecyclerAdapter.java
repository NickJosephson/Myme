package com.nitrogen.myme.presentation;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import com.nitrogen.myme.R;
import com.nitrogen.myme.objects.TemplateMeme;
import com.squareup.picasso.Picasso;

// Note that we specify the custom ViewHolder which gives us access to our views
public class TemplatesRecyclerAdapter extends RecyclerView.Adapter<TemplatesRecyclerAdapter.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Views in row and it's data
        public ImageView templateImageView;
        public TemplateMeme template;
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
            templateImageView = (ImageView) itemView.findViewById(R.id.template_image);
            mCallback = listener;

            //set method to handle click events
            itemView.setOnClickListener(this);
        }

        //**************************************************
        // Event handlers
        //**************************************************

        @Override
        public void onClick(View view) {
            mCallback.getID(template.getTemplateID());
        }

    }

    // Store a member variable for the templates
    private List<TemplateMeme> templates;
    private OnItemClick mCallback;

    //**************************************************
    // Constructor
    //**************************************************

    // Pass in the template array into the constructor
    public TemplatesRecyclerAdapter(List<TemplateMeme> templates, OnItemClick listener) {
        this.templates = templates;
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
        View templateView = inflater.inflate(R.layout.item_template_thumbnail, parent, false);

        // Return a new holder instance
        return new ViewHolder(templateView, mCallback);
    }

    // Populate data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull TemplatesRecyclerAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        TemplateMeme template = templates.get(position);

        // Set item views based on your views and data model
        ImageView imageView = viewHolder.templateImageView;
        viewHolder.template = template;

        Picasso.get().load(template.getImagePath()).into(imageView);
        //imageView.setImageURI(Uri.parse(template.getImagePath()));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return templates.size();
    }

    // mutator to update the templates that will be displayed
    public void updateTemplateList(List<TemplateMeme> newTemplateList) {
        this.templates = newTemplateList;
        notifyDataSetChanged();
    }

}
