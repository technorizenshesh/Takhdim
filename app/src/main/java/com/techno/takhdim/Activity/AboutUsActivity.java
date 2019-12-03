package com.techno.takhdim.Activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.techno.takhdim.R;

public class AboutUsActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_back;
    private WebView webview;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        findId();
        img_back.setOnClickListener(this);
//        myWeb.setWebViewClient(new WebViewClient());
//        myWeb.loadUrl("http://technorizen.com/Jitendra/carBooking/help.html");
        LoadPage("http://technorizen.com/Jitendra/carBooking/about.html");
    }

    private void findId() {
        img_back = findViewById(R.id.img_back);
        webview = findViewById(R.id.myWeb);
    }

    @Override
    public void onClick(View v) {
        if (v == img_back) {
            finish();
        }
    }

    private void LoadPage(String url) {
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading....");
        dialog.setMessage("Please wait!!!");
        dialog.show();
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webview.getSettings().setBuiltInZoomControls(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("PrivacypolicyActivity", "Processing webview url click...");
                dialog.show();
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i("PrivacypolicyActivity", "Finished loading URL: " + url);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e("PrivacypolicyActivity", "Error: " + description);
                dialog.dismiss();

            }
        });
        webview.loadUrl(url);
    }
}
