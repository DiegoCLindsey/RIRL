package com.cppandi.rirl.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public final class firestoreUtils {
    public static void sendObject(Object o, String path) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(path).add(o).addOnCompleteListener(
                new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Log.d("OK", "Archivo subido con éxito");
                        } else {
                            Log.d("ERROR", "Error al subir el objeto", task.getException());
                        }
                    }
                }
        );
    }
}
