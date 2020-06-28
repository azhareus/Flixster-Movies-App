package com.example.flixstermovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixstermovieapp.MovieDetailsActivity;
import com.example.flixstermovieapp.R;
import com.example.flixstermovieapp.models.Movie;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);

            int radius = 30; // corner radius, higher value = more rounded
            int margin = 10; // crop margin, set to 0 for corners with no crop
            // add this as the itemView's OnClickListener
            itemView.setOnClickListener(this);

        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imgUrl;
            int radius = 50; // corner radius, higher value = more rounded
            int margin = 50; // crop margin, set to 0 for corners with no crop

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imgUrl = movie.getBackdropPath();
                //Glide.with(context).load(imgUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
                //Glide.with(context).load(imgUrl).override(1000, 1000).into(ivPoster);//works without rounded corners
                Glide.with(context).load(imgUrl).transform(new RoundedCornersTransformation(radius, margin)).override(500, 500).into(ivPoster);


            } else {
                //portrait
                imgUrl = movie.getPosterPath();
                //Glide.with(context).load(imgUrl).transform(new RoundedCornersTransformation(radius, margin)).override(100, 100).into(ivPoster);
                //Glide.with(context).load(imgUrl).into(ivPoster);
                Glide.with(context).load(imgUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);

            }
            //if phone is in landscape
            //then imgUrl = backdrop page
            //else imgUrl = poster image
            //int radius = 0; // corner radius, higher value = more rounded
            //int margin = 0; // crop margin, set to 0 for corners with no crop
            //Glide.with(context).load(imgUrl).transform(new RoundedCornersTransformation(radius, margin)).override(100, 200).into(ivPoster);
            //Glide.with(context).load(imgUrl).transform()
            //Glide.with(context).load(imgUrl).into(ivPoster);
            //Glide.with(context).load(movie.getPosterPath() ).placeholder(R.drawable.ic_action_name).fitCenter().into(ivPoster);


        }

        @Override
        public void onClick(View view) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
