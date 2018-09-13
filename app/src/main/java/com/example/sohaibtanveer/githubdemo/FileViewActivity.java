package com.example.sohaibtanveer.githubdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import br.tiagohm.markdownview.MarkdownView;

public class FileViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_view);
        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");
        MarkdownView mdView = (MarkdownView) findViewById(R.id.fileMarkdownView);
        if(url!=null)
            mdView.loadMarkdownFromUrl(url);

    }
}
