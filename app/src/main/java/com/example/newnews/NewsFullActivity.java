package com.example.newnews;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class NewsFullActivity extends AppCompatActivity {
    WebView webView;
    ImageButton share;
    private LinearProgressIndicator progressIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        share = findViewById(R.id.share_news);
        progressIndicator = findViewById(R.id.progress_bar);

        progressIndicator.setIndicatorColor(getResources().getColor(R.color.my_primary, null));

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NewsFullActivity.this, "Share Button is Clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                String sh = getIntent().getStringExtra("url");
                intent.putExtra(Intent.EXTRA_TEXT, sh);
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share Via"));
            }
        });

        String url = getIntent().getStringExtra("url");

        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl(url);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    private void changeInProgress(boolean show) {
        if (show) {
            progressIndicator.setVisibility(View.VISIBLE);
        } else {
            progressIndicator.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            changeInProgress(true);
            webView.goBack();
        } else {
            changeInProgress(true);
            super.onBackPressed();
        }
    }
}
