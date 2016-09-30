package com.tramsun.flickr_gallery.features.search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.tramsun.flickr_gallery.data.model.FlickrPhoto;
import com.tramsun.flickr_gallery.utils.Unit;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageHolder> {
    private final Context context;
    private final int dp150;
    private List<FlickrPhoto> images;
    private final OnImageClicked listener;

    public interface OnImageClicked {
        void onImageClicked(FlickrPhoto imageData);
    }

    public ImagesAdapter(Context context, SearchMvvm.ViewModel viewModel) {
        this.context = context;
        this.images = viewModel.getAllImages();
        this.listener = viewModel;
        this.dp150 = Unit.dpToPx(context, 150);
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageView imageView = new ImageView(context);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp150);
        imageView.setLayoutParams(lp);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new ImageHolder(imageView);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        final FlickrPhoto imageData = images.get(position);
        Glide.with(context)
                .load(imageData.getThumbnailUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.imageView);
        holder.imageView.setOnClickListener(v -> listener.onImageClicked(imageData));
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setImages(List<FlickrPhoto> data) {
        images.clear();

        images.addAll(data);

        notifyDataSetChanged();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView;
        }
    }
}
