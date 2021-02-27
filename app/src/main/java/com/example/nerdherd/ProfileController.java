package com.example.nerdherd;

// Author: Zhipeng Z zhipeng4

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import androidx.annotation.NonNull;

public class ProfileController {
    private String name;
    private String password;
    private String email;
    private String id;
    private Bitmap avatar;
    private Profile profile;
    private FirebaseFirestore db;
    private HashMap<String, Object> profileData;
    private CollectionReference collectionReference;
    private ByteArrayOutputStream byteArrayOutputStream;
    private String compressString;
    private String stringAvatar;

    // Constructor
    public ProfileController(String name, String password, String email, String id, Bitmap avatar) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.id = id;
        this.avatar = avatar;
    }

    public Boolean avatarChecker(){
        profile = new Profile(name, password, email, avatar);
        stringAvatar = compresser(profile);
        if (stringAvatar.getBytes().length> 1000000){
            return false;
        }
        else{
            return true;
        }
    }

    public void uploadProfile(){
        // Access database
        db = FirebaseFirestore.getInstance();
        collectionReference = db.collection("Profile");
        profileData = new HashMap<>();
        profileData.put("Name", profile.getName());
        profileData.put("Password", profile.getPassword());
        profileData.put("Email", profile.getEmail());
        profileData.put("Avatar", stringAvatar);
        collectionReference
                .document(id)
                .set(profileData);
    }

    private String compresser(Profile profile){
        // Compress avatar
        byteArrayOutputStream = new ByteArrayOutputStream();
        profile.getAvatar().compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        compressString = byteArrayOutputStream.toString();
        return compressString;
    }
}