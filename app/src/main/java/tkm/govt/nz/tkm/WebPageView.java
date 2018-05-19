package tkm.govt.nz.tkm;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

public class WebPageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_page_view);

        ((WebView)findViewById(R.id.webpage)).setInitialScale(getScale());
        ((WebView)findViewById(R.id.webpage)).loadUrl((String) getIntent().getExtras().get("URL"));
    }

    private int getScale(){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(860);
        val = val * 100d;
        return val.intValue();
    }
}
