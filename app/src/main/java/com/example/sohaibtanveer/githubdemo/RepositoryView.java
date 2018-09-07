package com.example.sohaibtanveer.githubdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class RepositoryView extends AppCompatActivity implements CodeFragment.OnFragmentInteractionListener,
        IssuesFragment.OnFragmentInteractionListener,PullRequestsFragment.OnFragmentInteractionListener,
        ReadmeFragment.OnFragmentInteractionListener,CodefilesFragment.OnFragmentInteractionListener,
        ReleasesFragment.OnFragmentInteractionListener, ContributorsFragment.OnFragmentInteractionListener,
        CommitsFragment.OnFragmentInteractionListener
        {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_view);
        setBottomNavBar();

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
                                selectedFragment = CodeFragment.newInstance("hey","hi");
                                break;
                            case R.id.bottom_nav_issues:
                                selectedFragment = IssuesFragment.newInstance("hey","hi");
                                break;
                            case R.id.bottom_nav_pullReq:
                                selectedFragment = PullRequestsFragment.newInstance("hey","hi");
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
        transaction.replace(R.id.frame_layout, CodeFragment.newInstance("hey","hi"));
        transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}
