package tkm.govt.nz.tkm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class MLA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(MLA.this, WebPageView.class);
                searchIntent.putExtra("URL", "http://www.tkm.govt.nz/search/");
                startActivity(searchIntent);
            }
        });

        findViewById(R.id.nzmap).setAnimation(getMapAlphaAnimation());
        findViewById(R.id.nzmap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapIntent = new Intent(MLA.this, NZMap.class);
                startActivity(mapIntent);
            }
        });

    }

    private AlphaAnimation getMapAlphaAnimation(){
        AlphaAnimation blinkanimation= new AlphaAnimation(1, .7f); // Change alpha from fully visible to invisible
        blinkanimation.setDuration(600);
        blinkanimation.setStartOffset(1000);
        blinkanimation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
        blinkanimation.setRepeatCount(-1); // Repeat animation infinitely
        blinkanimation.setRepeatMode(Animation.REVERSE);
        return blinkanimation;
    }

}

