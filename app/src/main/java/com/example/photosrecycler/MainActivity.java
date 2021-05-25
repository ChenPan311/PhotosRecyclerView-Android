package com.example.photosrecycler;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.photosrecyclerview.PhotosPreviewRecyclerview;
import com.example.photosrecyclerview.PreviewImagesAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://picsum.photos/200/300");
        urls.add("https://picsum.photos/200/301");
        urls.add("https://picsum.photos/200/302");
        urls.add("https://picsum.photos/200/303");
        urls.add("https://picsum.photos/200/304");

        PhotosPreviewRecyclerview recycler = findViewById(R.id.recycler);
        recycler.init(8);
        recycler.setImagesUris(urls);
        recycler.setOnTouchPreviewImageListener(new PreviewImagesAdapter.OnTouchPreviewInterface() {
            @Override
            public void onTouch(int position, View view) {
                Toast.makeText(MainActivity.this, "" , Toast.LENGTH_SHORT).show();
            }
        });
        recycler.setSpanCount(3);
        recycler.setImagesSize(120,120);
        recycler.setDeleteButtonColor(R.color.colorSecondary);



        ImageButton addImageBtn = findViewById(R.id.add_image_btn);

        ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Intent data = result.getData();
                            recycler.addPhoto(data != null ? data.getData() : null);
                        }
                    }
                });

        addImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                someActivityResultLauncher.launch(i);
            }
        });

    }
}