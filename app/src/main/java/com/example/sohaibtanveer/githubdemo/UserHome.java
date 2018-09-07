package com.example.sohaibtanveer.githubdemo;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GithubAuthProvider;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static String userAccessToken = null;
    private static User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        setContents();

        String code = getCode();

        if (code != null) {
            final GitHubService service = RetrofitClient.getClient("https://github.com").create(GitHubService.class);
            Call<AccessToken> call = service.getAccessToken("25a2190a925d5982a5ae", "feb32616d4c1331e755fe5a33193c3deafc4fa48", code);
            call.enqueue(new Callback<AccessToken>() {
                @Override
                public void onResponse(Call<AccessToken> call, Response<AccessToken> response) {
                    userAccessToken = response.body().getAccessToken();
                    getUser();
                    SharedPreferences.Editor editor = getSharedPreferences("USER_DATA", MODE_PRIVATE).edit();
                    editor.putString("accessToken", userAccessToken);
                    editor.commit();
                }

                @Override
                public void onFailure(Call<AccessToken> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void setContents(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

   private void getUser(){
        if(userAccessToken!=null){
            GitHubService serviceUser = RetrofitClient.getClient("https://api.github.com").create(GitHubService.class);
            Call<User> callUser = serviceUser.getUser(userAccessToken);
            callUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> callUser, Response<User> response) {
                    user = response.body();
                    updateUI();
                }

                @Override
                public void onFailure(Call<User> callUser, Throwable t) {
                    String error = t.getMessage();
                    Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void updateUI(){
        if(user!=null) {
            ImageView avatar = (ImageView) findViewById(R.id.userAvatar);
            String aURL = user.getAvatarUrl();
            Picasso.get().load(Uri.parse(user.getAvatarUrl())).resize(100, 100).into(avatar);
            TextView username = (TextView) findViewById(R.id.username);
            username.setText(user.getLogin());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_home, menu);
        /*// Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));*/
        SearchView search = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, SearchResultsActivity.class)));
        search.setQueryHint(getResources().getString(R.string.search_hint));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
/*
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String getCode() {
        Intent intent = getIntent();
        String code = null;
        if (intent.getAction() == Intent.ACTION_VIEW) {
            code = intent.getData().getQueryParameter("code");
        }
        return code;
    }
}
