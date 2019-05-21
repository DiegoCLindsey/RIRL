package com.cppandi.rirl.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class DBQuery<T> {
    private Object response;

    public DBQuery(String id,String collection){
        FirebaseFirestore.getInstance().collection(collection).document(id).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            final Object res = task.getResult().toObject(Object.class);
                            response = res;

                        } else {
                            Log.d("prueba", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public T getResponse(){
        return (T) response;
    }

}