package com.example.sohaibtanveer.githubdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class FileViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        WebView webview = (WebView) findViewById(R.id.fileWebView);
        if(url!=null)
            webview.loadUrl(url);

    }
}
