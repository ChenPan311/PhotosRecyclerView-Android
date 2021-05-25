package com.example.photosrecyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PreviewImagesAdapter extends RecyclerView.Adapter<PreviewImagesAdapter.ViewHolder> {
    private List<String> mImagesPath;
    private Context mContext;
    private int mPlaceholder = R.drawable.placeholder;
    private int mImageWidth;
    private int mImageHeight;
    private int mDeleteBtnColor = R.color.colorAccent;

    public interface DeletePreviewInterface {
        void onDelete(int position, View view);
    }

    private DeletePreviewInterface mDeleteListener;

    public interface OnTouchPreviewInterface {
        void onTouch(int position, View view);
    }

    private OnTouchPreviewInterface mTouchListener;

    public PreviewImagesAdapter(Context context, List<String> imagesPath) {
        this.mImagesPath = imagesPath;
        this.mContext = context;
        mImageWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 80, mContext.getResources().getDisplayMetrics()));
        mImageHeight = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 80, mContext.getResources().getDisplayMetrics()));
    }

    public void setPlaceholder(int resourceId) {
        mPlaceholder = resourceId;
    }

    public void setImagesSize(int width, int height) {
        mImageHeight = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, height, mContext.getResources().getDisplayMetrics()));
        mImageWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, width, mContext.getResources().getDisplayMetrics()));
    }

    public void setDeleteBtnColor(int colorId) {
        mDeleteBtnColor = colorId;
    }

    public void setDeleteListener(DeletePreviewInterface listener) {
        this.mDeleteListener = listener;
    }

    public void setTouchListener(OnTouchPreviewInterface listener) {
        this.mTouchListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageView;
        private final FloatingActionButton deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image_iv);
            this.deleteBtn = itemView.findViewById(R.id.delete_btn);
            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mDeleteListener != null) {
                        mDeleteListener.onDelete(getAdapterPosition(), v);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTouchListener != null) {
                        mTouchListener.onTouch(getAdapterPosition(), v);
                    }
                }
            });
        }
    }

    @SuppressLint("ResourceAsColor")
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deletable_imageview, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String imageUri = mImagesPath.get(position);
        Glide.with(mContext)
                .load(imageUri)
                .placeholder(mPlaceholder)
                .into(holder.imageView);
        holder.imageView.getLayoutParams().width = mImageWidth;
        holder.imageView.getLayoutParams().height = mImageHeight;
        holder.imageView.requestLayout();
        holder.deleteBtn.setBackgroundTintList(ColorStateList.valueOf(mContext.getResources().
                getColor(mDeleteBtnColor)));
        if (imageUri != null) {
            holder.deleteBtn.setVisibility(View.VISIBLE);
        } else {
            holder.deleteBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mImagesPath.size();
    }
}
