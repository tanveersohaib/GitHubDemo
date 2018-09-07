package com.example.sohaibtanveer.githubdemo;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class RepositoryView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_view);
        setBottomNavBar();
        MainActivity.bus.register(this);
    }

    private void setBottomNavBar() {
        final BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_code:
                                selectedFragment = CodeFragment.newInstance();
                                break;
                            case R.id.bottom_nav_issues:
                                selectedFragment = IssuesFragment.newInstance();
                                break;
                            case R.id.bottom_nav_pullReq:
                                selectedFragment = PullRequestsFragment.newInstance();
                                break;
                        }
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_layout, selectedFragment);
                        transaction.commit();
                        return true;
                    }
                });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, CodeFragment.newInstance());
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    @Subscribe
    public void fragmentListener(Fragment context){
        if(context instanceof CodeFragment)
            Toast.makeText(this,"CodeFragment",Toast.LENGTH_LONG).show();
        else if(context instanceof IssuesFragment)
            Toast.makeText(this,"IssuesFragment",Toast.LENGTH_LONG).show();
        else if(context instanceof PullRequestsFragment)
            Toast.makeText(this,"PullReqFragment",Toast.LENGTH_LONG).show();
        else if(context instanceof CommitsFragment)
            Toast.makeText(this,"CommitsFragment",Toast.LENGTH_LONG).show();
        else if(context instanceof CodefilesFragment)
            Toast.makeText(this,"CodeFilesFragment",Toast.LENGTH_LONG).show();
    }

}
