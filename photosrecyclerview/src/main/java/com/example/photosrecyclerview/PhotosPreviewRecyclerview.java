package com.example.photosrecyclerview;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PhotosPreviewRecyclerview extends RecyclerView {
    private int IMAGE_VIEW_SIZE = 8;
    private List<String> mSelectedImageList = new ArrayList<>();
    private PreviewImagesAdapter mImagePreviewAdapter;
    private int mImageViewCounter = 0;
    private String mMessage = "Full";

    public PhotosPreviewRecyclerview(@NonNull Context context) {
        super(context);
    }

    public PhotosPreviewRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PhotosPreviewRecyclerview(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(int size){
        IMAGE_VIEW_SIZE = size;
        for (int i = 0; i < IMAGE_VIEW_SIZE; i++) {
            mSelectedImageList.add(null);
        }
        mImagePreviewAdapter = new PreviewImagesAdapter(getContext(),mSelectedImageList);
        setHasFixedSize(true);
        setLayoutManager(new GridLayoutManager(getContext(), 4));

        mImagePreviewAdapter.setDeleteListener(new PreviewImagesAdapter.DeletePreviewInterface() {
            @Override
            public void onDelete(int position, View view) {
                mSelectedImageList.remove(position);
                mSelectedImageList.add(null);
                mImagePreviewAdapter.notifyItemRemoved(position);
                if (mImageViewCounter > 0) {
                    mImageViewCounter--;
                }
            }
        });

        setAdapter(mImagePreviewAdapter);
    }

    public void addPhoto(Uri uri){
        if(mImageViewCounter < IMAGE_VIEW_SIZE) {
            mSelectedImageList.remove(mImageViewCounter);
            mSelectedImageList.add(mImageViewCounter, uri.toString());
            mImagePreviewAdapter.notifyItemChanged(mImageViewCounter++);
        }else{
            Toast.makeText(getContext(), mMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public List<String> getSelectedImageList(){
        return mSelectedImageList;
    }

    public int getImageCounter(){
        return mImageViewCounter;
    }

    public void setPlaceholder(int resourceId){
        mImagePreviewAdapter.setPlaceholder(resourceId);
    }

    public void setSpanCount(int span){
        setLayoutManager(new GridLayoutManager(getContext(), span));
    }

    public void setImagesUris(ArrayList<String> uris) {
        if (uris.size() < IMAGE_VIEW_SIZE) {
            mSelectedImageList.clear();
            mSelectedImageList.addAll(uris);
            mImageViewCounter = uris.size();
            for(int i=0 ; i<IMAGE_VIEW_SIZE - uris.size() ; i++){
                mSelectedImageList.add(null);
            }
            mImagePreviewAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(), mMessage, Toast.LENGTH_SHORT).show();
        }
    }

    public void setMessageWhenFull(String msg){
        mMessage = msg;
    }

    public void setImagesSize(int width, int height){
        mImagePreviewAdapter.setImagesSize(width,height);
    }

    public void setDeleteButtonColor(int colorId){
        mImagePreviewAdapter.setDeleteBtnColor(colorId);
    }

    public void setOnTouchPreviewImageListener(PreviewImagesAdapter.OnTouchPreviewInterface listener){
        mImagePreviewAdapter.setTouchListener(listener);
    }
}
