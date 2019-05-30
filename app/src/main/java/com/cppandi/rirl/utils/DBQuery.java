package com.cppandi.rirl.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class DBQuery {
    public DocumentSnapshot response;

    public DBQuery(String id,String collection){
        this.response = null;

        Log.d("log","INICIANDO QUERY");
        FirebaseFirestore.getInstance().collection(collection).document(id).get().addOnCompleteListener(
                new OnCompleteListener<DocumentSnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful()) {
                            final DocumentSnapshot res = task.getResult();
                            response = res;
                            Log.d("log","RESPUESTA RECIBIDA: "+response);
                            onResponse();

                        } else {
                            Log.d("prueba", "Error getting documents: ", task.getException());
                        }
                    }

                });

    }

    public void onResponse(){

    }

}