package com.example.nerdherd.UserInterfaceSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nerdherd.MenuController;
import com.example.nerdherd.Model.UserProfile;
import com.example.nerdherd.ObjectManager.ProfileManager;
import com.example.nerdherd.R;
import com.example.nerdherd.RecycleViewAdapters.AdapterController;
import com.example.nerdherd.RecycleViewAdapters.ProfileListAdapter;
import com.example.nerdherd.UserInterfaceProfile.ProfileActivity;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

/**
 * This handles when a user wants to search a specific user with keyword
 * @author Utkarsh S. usaraswa
 * @author Zhipeng Z. zhipeng4
 * @author Harjot S. harjotsi
 */
public class SearchUserActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private MenuController menuController;
    private RecyclerView recyclerView;
    private ProfileListAdapter adapter;
    private ProfileListAdapter.onClickListener listener;
    private AdapterController adapterController;
    private Button searchButton;
    private TextView keywordEdit;
    private ArrayList<UserProfile> resultList;
    private Intent profileView;
    private ProfileManager pMgr = ProfileManager.getInstance();
    public static final String EXTRA_MESSAGE = "userId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user);

        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.user_draw_layout);
        navigationView = findViewById(R.id.user_navigator);
        recyclerView = findViewById(R.id.user_recyclerView);
        searchButton = findViewById(R.id.user_search_button);
        keywordEdit = findViewById(R.id.user_keyword_edit);

        setSupportActionBar(toolbar);

        menuController = new MenuController(SearchUserActivity.this, toolbar, navigationView, drawerLayout);
        menuController.useMenu(false);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfiles();
            }
        });

        listener = new ProfileListAdapter.onClickListener() {
            @Override
            public void onClick(View view, int index) {
                UserProfile up = resultList.get(index);
                if(up == null) {
                    // shouldn't happen
                    return;
                }

                profileView = new Intent(SearchUserActivity.this, ProfileActivity.class);
                profileView.putExtra(EXTRA_MESSAGE, up.getUserId());
                startActivity(profileView);
                //finish(); //finish was taken to fix the back button to go back to user screen instead of closing the app
            }
        };

        showProfiles();
    }

    private void showProfiles(){
        resultList = pMgr.searchProfileByKeyword(keywordEdit.getText().toString());
        keywordEdit.setText("");
        adapter = new ProfileListAdapter(resultList, listener);
        adapterController = new AdapterController(SearchUserActivity.this, recyclerView, adapter);
        adapterController.useAdapter();
    }
}