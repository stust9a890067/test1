package com.example.myapplication20220512;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication20220512.databinding.ActivityABinding;
import com.example.myapplication20220512.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class A extends AppCompatActivity {

    private StorageReference storageRef;

    private ActivityABinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityABinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        storageRef = FirebaseStorage.getInstance().getReference();

        binding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imgUrl = "https://firebasestorage.googleapis.com/v0/b/test-e9cf5.appspot.com/o/apple.jfif?alt=media&token=c9c50241-d966-40e4-a536-0f1f8d3cc7fe";

                Glide.with(A.this)
                        .load(imgUrl)
                        .into(binding.imageView);

            }
        });

        binding.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String imgName = binding.editText.getText().toString();
                storageRef.child("img0512").child("apple (2).jfif")
                        .getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Glide.with(A.this)
                                        .load(uri)
                                        .into(binding.imageView);
                            }
                        });
            }
        });

        binding.btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent picker = new Intent(Intent.ACTION_GET_CONTENT);
                picker.setType("image/*");
                startActivityForResult(picker,101);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode ==101){
            Uri uri  = data.getData();

            Glide.with(A.this)
                    .load(uri)
                    .into(binding.imageView);
            storageRef.child("putimg").child(uri.getLastPathSegment())
                    .putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(A.this,"OK:"+uri.getLastPathSegment()
                                    ,Toast.LENGTH_LONG).show();
                        }
                    });

        }
    }
}