package com.example.sohaibtanveer.githubdemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static Bus bus = new Bus(ThreadEnforcer.ANY);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(isUserActive()==true) {
            Intent intent = new Intent(this,UserHome.class);
            startActivity(intent);
        }
        else {
            setContentView(R.layout.activity_main);
            Button loginBtn = (Button) findViewById(R.id.login);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://github.com/login/oauth/authorize?client_id=25a2190a925d5982a5ae"));
                    startActivity(i);
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private boolean isUserActive(){
        SharedPreferences pref = getSharedPreferences("user_data",MODE_PRIVATE);
        if(pref.contains("access_token"))
            return true;
        else
            return false;
    }
}

