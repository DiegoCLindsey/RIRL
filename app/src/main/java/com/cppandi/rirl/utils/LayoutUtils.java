package com.cppandi.rirl.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.cppandi.rirl.R;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public final class LayoutUtils {

    public static CircleOptions createDefaultCircleOptions(LatLng latLng, double radius) {

        return new CircleOptions().center(latLng)
                .radius(radius)
                .strokeWidth(3f);
    }

    public static CircleOptions createTavern(LatLng latLng){
        return createDefaultCircleOptions(latLng).strokeColor(Constants.taberna|Constants.opaque)
                .fillColor(Constants.taberna);
    }

    public static MarkerOptions tavernMarker(LatLng latLng, Context context){
        return new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(LayoutUtils.getBitmap(R.drawable.tavern,context)))
                .anchor(0.5f,0.5f);
    }

    public static CircleOptions createDungeon(LatLng latLng){
        return createDefaultCircleOptions(latLng).strokeColor(Constants.mazmorra|Constants.opaque)
                .fillColor(Constants.mazmorra);
    }

    public static MarkerOptions dungeonMarker(LatLng latLng,Context context){
        return new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(LayoutUtils.getBitmap(R.drawable.dungeon,context)))
                .anchor(0.5f,0.5f);
    }

    public static CircleOptions createDefaultCircleOptions(LatLng latLng) {
        return createDefaultCircleOptions(latLng, Constants.DEFAULT_RADIUS);
    }

    public static Bitmap getBitmap(int drawableRes, Context context) {
        Drawable drawable = context.getResources().getDrawable(drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);

        return bitmap;
    }


}
