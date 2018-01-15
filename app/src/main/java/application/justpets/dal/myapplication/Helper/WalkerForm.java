package application.justpets.dal.myapplication.Helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import application.justpets.dal.myapplication.R;

public class WalkerForm extends AppCompatActivity {

    private WebView walkerformlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walker_form);

        setTitle("Start application to become a dog walker");

        walkerformlink = (WebView)findViewById(R.id.link);
        WebSettings webSettings = walkerformlink.getSettings();

        walkerformlink.setInitialScale(200);
        walkerformlink.getSettings().setSupportZoom(true);
        walkerformlink.getSettings().setLoadWithOverviewMode(true);
        walkerformlink.getSettings().setBuiltInZoomControls(true);

        webSettings.setJavaScriptEnabled(true);
        walkerformlink.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSeCrRHwGAphQzT7oKI5cKFkRDcQH3djg8JLB26KBZQyaLNNEw/viewform?usp=sf_link");
        walkerformlink.setWebViewClient(new WebViewClient());
    }

    @Override
    public void onBackPressed() {
        if (walkerformlink.canGoBack()){
            walkerformlink.goBack();
        } else
            super.onBackPressed();
    }
}
