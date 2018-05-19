package tkm.govt.nz.tkm.util;

import android.content.Context;
import android.graphics.Color;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import tkm.govt.nz.tkm.R;
import tkm.govt.nz.tkm.adapter.TKMInfoWindow;
import tkm.govt.nz.tkm.data.Region;
import tkm.govt.nz.tkm.data.Regions;
import tkm.govt.nz.tkm.data.SubRegion;

/**
 * Created by kanaano on 25/03/18.
 */

public class TKMUtils {
    public static Regions getTKMConfig(Context context) throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        Regions config = mapper.readValue(new InputStreamReader(context.getResources().openRawResource(R.raw.map_config)), Regions.class);
        return config;
    }

    public static List<PolylineOptions> getPolylineFromCoordinatesFile(Context context, String files) throws IOException {
        List<PolylineOptions> regionPolylines = new ArrayList<>();
        for (String file: files.split(",")){

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(context.getResources().openRawResource(context.getResources().getIdentifier(file, "raw", context.getPackageName())))
            );

            LatLng firstPoint = null;
            PolylineOptions regionPolyline = new PolylineOptions();

            String line = null;
            while((line = reader.readLine()) != null) {
                String regionName = null;
                String lntLng[] = line.split(",");
                //Expects 3 points for a single coordinates, otherwise ignore.
                if(lntLng.length == 3) {
                    LatLng coordinate = new LatLng(Float.parseFloat(lntLng[1]), Float.parseFloat(lntLng[0]));

                    if(firstPoint == null) {
                        firstPoint = coordinate;
                    }
                    regionPolyline.add(new LatLng(Float.parseFloat(lntLng[1]), Float.parseFloat(lntLng[0])));
                    regionPolyline.color(Color.argb(85, 0 , 0 , 0));
                }
            }
            regionPolyline.add(firstPoint);
            regionPolylines.add(regionPolyline);
            regionPolyline.width(3);
        }

        return regionPolylines;

    }

    public static void putMarkerForRegion(GoogleMap mMap, SubRegion subRegion) {
        float latitude = Float.parseFloat(subRegion.getInfoMarkerCoordinate().split(",")[1]);
        float longitude = Float.parseFloat(subRegion.getInfoMarkerCoordinate().split(",")[0]);

        LatLng markerLocation = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(markerLocation).title(subRegion.getName());

        Marker marker = mMap.addMarker(markerOptions);
        marker.setTag(subRegion);
    }


}
