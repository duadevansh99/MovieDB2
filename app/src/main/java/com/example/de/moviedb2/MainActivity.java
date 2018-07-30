package com.example.de.moviedb2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements MovieFragment.movieFragmentCallback, TVShowsFragment.TVShowsFragmentCallback {

    public static final String DISPLAY_MOVIE="open movie for display from main activity";
    public static final String DISPLAY_TV="open show for display from main activity";
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("");
        }

        bottomNavigationView=findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,new MovieFragment()).commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment;
            int id= menuItem.getItemId();
            if(id== R.id.movie_menu){
                selectedFragment= new MovieFragment();
            }
            else if(id == R.id.tv_menu){
                selectedFragment=new TVShowsFragment();
            }
            else{
                selectedFragment= new PeopleFragment();
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onMovieClicked(long id) {

        Intent intent=new Intent(this,DisplayMovieItem.class);
        intent.putExtra(DISPLAY_MOVIE,id);
        startActivity(intent);

    }


    public void seeAllMovies(View view){
        //Toast.makeText(this,"MainActivityONClick",Toast.LENGTH_LONG).show();

        int id=view.getId();
        String category=" ";
        if(id==R.id.seeAllButton1){
            category="Popular";
        }
        else if(id==R.id.seeAllButton2){
            category="Now Playing";
        }
        else if(id==R.id.seeAllButton3){
            category="Upcoming";
        }
        else if(id==R.id.seeAllButton4){
            category="Top Rated";
        }

        Bundle bundle=new Bundle();
        bundle.putString("category",category);
        Intent intent=new Intent(this,DisplayCategory.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void seeAllTV(View view){

        int id= view.getId();
        String category=" ";

        if(id==R.id.seeAllButton1TV){
            category="Popular TV";
        }
        else if(id==R.id.seeAllButton2TV){
            category="Airing Today";
        }
        else if(id==R.id.seeAllButton3TV){
            category="On The Air";
        }
        else if(id==R.id.seeAllButton4TV){
            category="Top Rated TV";
        }

        Bundle bundle=new Bundle();
        bundle.putString("category",category);
        Intent intent=new Intent(this,DisplayCategory.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onShowClicked(long id) {

        Intent intent=new Intent(this,DisplayMovieItem.class);
        intent.putExtra(DISPLAY_TV,id);
        startActivity(intent);

    }
}
