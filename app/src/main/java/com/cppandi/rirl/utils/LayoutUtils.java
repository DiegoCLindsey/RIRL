package com.cppandi.rirl.utils;

import com.cppandi.rirl.R;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

public final class LayoutUtils {
    public static CircleOptions createDefaultCircleOptions(LatLng latLng, double radius) {

        return new CircleOptions().center(latLng)
                .radius(radius)
                .strokeWidth(3f)
                .strokeColor(R.color.colorPrimary)
                .fillColor(R.color.colorAccentEdit);
    }

    public static CircleOptions createDefaultCircleOptions(LatLng latLng) {
        return createDefaultCircleOptions(latLng, Constants.DEFAULT_RADIUS);
    }
}
