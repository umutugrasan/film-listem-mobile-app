package com.example.odev.data;

import com.example.odev.Constants;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

// Firestore'a kolay erisim icin yardimci sinif
public class FirestoreHelper {

    // movies koleksiyonunu ver
    public static CollectionReference moviesCollection() {
        return FirebaseFirestore.getInstance().collection(Constants.MOVIES_COLLECTION);
    }
}
