package tkm.govt.nz.tkm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import tkm.govt.nz.tkm.adapter.TKMInfoWindow;
import tkm.govt.nz.tkm.data.Region;
import tkm.govt.nz.tkm.data.Regions;
import tkm.govt.nz.tkm.data.SubRegion;
import tkm.govt.nz.tkm.util.TKMUtils;

public class NZMap extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLngBounds newZealandBounds = new LatLngBounds(new LatLng(-48, 161), new LatLng(-32.5, -170.00417));
    private List<Polyline> topLevelRegions = new ArrayList<>();
    private List<Polyline> subRegions = new ArrayList<>();
    private final float MIN_ZOOM = 4.5f;
    private final float MAX_ZOOM = 14.0f;
    private final float REGION_ZOOM = 7.0f;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nzmap);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng auckland = new LatLng(-36.848461, 174.763336);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(auckland));
        mMap.moveCamera(CameraUpdateFactory.zoomTo(4.5f));
        mMap.setMinZoomPreference(MIN_ZOOM);
        mMap.setMaxZoomPreference(MAX_ZOOM);
        mMap.setLatLngBoundsForCameraTarget(newZealandBounds);
        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                if(cameraPosition.zoom > REGION_ZOOM) {
                    for(Polyline line : topLevelRegions) {
                        line.setVisible(false);
                    }
                    for(Polyline line : subRegions) {
                        line.setVisible(true);
                    }
                } else {
                    for(Polyline line : topLevelRegions) {
                        line.setVisible(true);
                    }
                    for(Polyline line : subRegions) {
                        line.setVisible(false);
                    }
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                TKMInfoWindow infoWindow = new TKMInfoWindow(NZMap.this, (SubRegion) marker.getTag());
                mMap.setInfoWindowAdapter(infoWindow);
                if(marker.isInfoWindowShown()) {
                    marker.hideInfoWindow();
                } else {
                    marker.showInfoWindow();
                }

                return true;
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                Toast.makeText(getApplicationContext(), "Feature not implemented yet.", Toast.LENGTH_SHORT).show();
            }
        });

        try {
            Regions regions = TKMUtils.getTKMConfig(getApplicationContext());
            int colorIndex = 0;
            int[] colors = {Color.BLACK,
                          Color.GRAY,
                          Color.LTGRAY,
                          Color.WHITE,
                          Color.RED,
                          Color.GREEN,
                          Color.BLUE,
                          Color.YELLOW,
                          Color.CYAN,
                          Color.MAGENTA,
                    (int) (Color.MAGENTA*Math.random()),
                    (int) (Color.CYAN*Math.random()),
                    (int) (Color.GREEN*Math.random())};

            for(Region region : regions.getRegions()) {
                int regionColor = colors[colorIndex];
                for(PolylineOptions options : TKMUtils.getPolylineFromCoordinatesFile(getApplicationContext(), region.getRegion().getFile())) {
                    Polyline polyline = mMap.addPolyline(options);
                    polyline.setClickable(true);
                    topLevelRegions.add(polyline);

                    for(SubRegion subRegion : region.getRegion().getSubRegions()) {
                        for(PolylineOptions subRegionOptions : TKMUtils.getPolylineFromCoordinatesFile(getApplicationContext(), subRegion.getFile())) {
                            subRegionOptions.color((int) (regionColor));
                            subRegionOptions.width(5);
                            Polyline subRegionPolyline = mMap.addPolyline(subRegionOptions);
                            subRegions.add(subRegionPolyline);
                        }
                        TKMUtils.putMarkerForRegion(mMap, subRegion);
                    }
                }

                colorIndex++;
                colorIndex = colorIndex%colors.length;
                System.out.print("colorIndex ############## "+ colorIndex);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
