package tkm.govt.nz.tkm.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import tkm.govt.nz.tkm.R;
import tkm.govt.nz.tkm.data.SubRegion;

/**
 * Created by kanaano on 25/03/18.
 */

public class TKMInfoWindow implements GoogleMap.InfoWindowAdapter {
    private Context context;
    private SubRegion subRegion;

    public TKMInfoWindow(Context context, SubRegion subRegion) {
        this.context = context;
        this.subRegion = subRegion;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater().inflate(R.layout.custom_info_window, null);

        TextView subRegionName = (TextView) view.findViewById(R.id.name);
        TextView subRegionInfo = (TextView) view.findViewById(R.id.info);
        subRegionName.setText(subRegion.getName());

        String info = readStream(context.getResources().openRawResource(context.getResources().getIdentifier(subRegion.getInfoFile(), "raw", context.getPackageName())));

        subRegionInfo.setText(info);

        return view;
    }

    private static String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder(512);
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line = null;
            while ((line = r.readLine()) != null) {
                sb.append(line+"\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }

}
