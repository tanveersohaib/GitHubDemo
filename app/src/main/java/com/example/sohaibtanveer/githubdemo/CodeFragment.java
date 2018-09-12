package com.example.sohaibtanveer.githubdemo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CodeFragment extends Fragment {

    private static Item repository;
    private static View rootView;
    private String accessToken;

    private RecyclerView recyclerViewDirectory;
    private DirectoryAdapter directoryAdapter;

    private RecyclerView recyclerViewPath;
    private PathRecyclerViewAdapter adapterPath;

    public CodeFragment() {
        // Required empty public constructor
    }

    public static CodeFragment newInstance(String repoJsonString) {
        CodeFragment fragment = new CodeFragment();
        Bundle b = new Bundle();
        b.putString("repoJsonString",repoJsonString);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeAccessToken();
        recyclerViewDirectory = null;
        directoryAdapter = null;
        adapterPath = null;
        recyclerViewPath = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MainActivity.bus.register(this);
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_code, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("README"));
        tabLayout.addTab(tabLayout.newTab().setText("FILES"));
        tabLayout.addTab(tabLayout.newTab().setText("COMMITS"));
        tabLayout.addTab(tabLayout.newTab().setText("RELEASES"));
        tabLayout.addTab(tabLayout.newTab().setText("CONTRIBUTORS"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.pager);
        final PagerAdapterCustom adapter = new PagerAdapterCustom
                (getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition() == 0) //Readme
                    getReadmeUrl();
                else if(tab.getPosition() == 1) {//Code files
                    SharedPreferences pref = getActivity().getSharedPreferences("repo_data",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("access_token",accessToken);
                    editor.putString("repo_name",repository.getFullName());
                    editor.commit();
                    createPathRecyclerView("master");
                    getCodeFiles(null,"master");
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //Initialize repository static member
        Bundle b = getArguments();
        String repoJsonString = b.getString("repoJsonString");
        Gson gson = new Gson();
        repository = gson.fromJson(repoJsonString,Item.class);
        getReadmeUrl();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //context handling
        MainActivity.bus.post(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initializeAccessToken(){
        SharedPreferences pref = getActivity().getSharedPreferences("user_data",Context.MODE_PRIVATE);
        if(pref.contains("access_token")){
            accessToken = pref.getString("access_token",null);
        }
    }

    private void getReadmeUrl(){
        GitHubService serviceUser = RetrofitClient.getClient("https://api.github.com").create(GitHubService.class);
        Call<ReadmeObject> readme = serviceUser.getReadmeObject("/repos/" + repository.getFullName() + "/readme",accessToken);
        readme.enqueue(new Callback<ReadmeObject>() {
            @Override
            public void onResponse(Call<ReadmeObject> readme, Response<ReadmeObject> response) {
                loadReadMe(response.body());
            }

            @Override
            public void onFailure(Call<ReadmeObject> readme, Throwable t) {

            }
        });
    }

    private void loadReadMe(ReadmeObject obj){
        if(obj != null) {
            WebView readmeWebView = (WebView) rootView.findViewById(R.id.readmeWebView);
            readmeWebView.loadUrl(obj.getDownloadUrl());
        }
    }

    private void getCodeFiles(String dir,final String ref){
        GitHubService serviceUser = RetrofitClient.getClient("https://api.github.com").create(GitHubService.class);
        Call<List<Directory>> directory;
        if(dir == null)
             directory = serviceUser.getFiles("/repos/" + repository.getFullName() + "/contents",ref,accessToken);
        else if(dir.equals("root"))
            directory = serviceUser.getFiles("/repos/" + repository.getFullName() + "/contents",ref,accessToken);
        else
            directory = serviceUser.getFiles("/repos/"+ repository.getFullName() + "/contents/" + dir ,ref,accessToken);
        directory.enqueue(new Callback<List<Directory>>() {
            @Override
            public void onResponse(Call<List<Directory>> call, Response<List<Directory>> response) {
                loadCodeFiles(response.body(),ref);
            }

            @Override
            public void onFailure(Call<List<Directory>> call, Throwable t) {

            }
        });

    }

    private void loadCodeFiles(List<Directory> directories,String ref){
        if(directories != null) {
            if(directoryAdapter == null) {
                recyclerViewDirectory = (RecyclerView) getActivity().findViewById(R.id.directoryRecyclerView);
                directoryAdapter = new DirectoryAdapter(directories, ref);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                recyclerViewDirectory.setLayoutManager(layoutManager);
                recyclerViewDirectory.setAdapter(directoryAdapter);
            }
            else
                directoryAdapter.loadData(directories,ref);
        }
    }

    private void createPathRecyclerView(String ref){

        ArrayList<String> paths = new ArrayList<String>();
        paths.add("root");
        if(adapterPath == null) {
            recyclerViewPath = (RecyclerView) getActivity().findViewById(R.id.pathRecyclerView);
            adapterPath = new PathRecyclerViewAdapter(paths, ref);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            recyclerViewPath.setLayoutManager(layoutManager);
            recyclerViewPath.setAdapter(adapterPath);
        }
        else
            adapterPath.reset(paths);
    }

    private void addNewPath(String newPath){
        adapterPath.addItem(newPath);
    }

    //Directory
    /*@Override
    public void onclick(View v, Directory dir) {
        String type = dir.getType();
        if(type.equals("dir")){
            addNewPath(dir.getName());
            getCodeFiles(dir.getPath(),);
        }
        else if(type.equals("file")){
            Intent intent = new Intent(getActivity(),FileViewActivity.class);
            intent.putExtra("url",dir.getDownloadUrl());
            startActivity(intent);
        }
    }*/

    @Subscribe void changeDirectory(Communicator com){
        if(com.getTypeOfData().equals("directory_data")){
            ArrayList<String> data = (ArrayList<String>) com.getObj();
            Gson gson = new Gson();
            Directory dir = gson.fromJson(data.get(0),Directory.class);
            String ref = data.get(1);
            String type = dir.getType();
            if(type.equals("dir")){
                addNewPath(dir.getName());
                getCodeFiles(dir.getPath(),ref);
            }
            else if(type.equals("file")){
                Intent intent = new Intent(getActivity(),FileViewActivity.class);
                intent.putExtra("url",dir.getDownloadUrl());
                startActivity(intent);
            }
        }
    }

    /*//Path
    @Override
    public void onclick(View v, String path) {
        adapter.removeItem(path);
        getCodeFiles(path,);
    }*/

    @Subscribe
    public void changePath(Communicator com){
        if(com.getTypeOfData().equals("path_information")){
            ArrayList<String> data = (ArrayList<String>) com.getObj();
            getCodeFiles(data.get(0),data.get(1));
        }
    }

    //Branch handler
    @Subscribe
    public void changeBranch(Communicator com){
        if(com.getTypeOfData().equals("branch_reference")){
            String ref = (String) com.getObj();
            createPathRecyclerView(ref);
            getCodeFiles(null,ref);
        }
    }

}
