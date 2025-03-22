package com.example.starsrating;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starsrating.Beans.Star;
import com.example.starsrating.Service.StarService;
import com.example.starsrating.Adapter.StarAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private List<Star> stars;
    private RecyclerView recyclerView;
    private StarAdapter starAdapter = null;
    private StarService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        stars = new ArrayList<>();
        service = StarService.getInstance();
        init();
        recyclerView = findViewById(R.id.recycle_view);
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        //insérer le code
        starAdapter = new StarAdapter(this, service.findAll());
        recyclerView.setAdapter(starAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint("Rechercher...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                starAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_share_message) {
            Intent smsIntent = new Intent(Intent.ACTION_SEND);
            smsIntent.setType("text/plain");
            smsIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez ces stars !");
            startActivity(Intent.createChooser(smsIntent, "Partager via"));
            return true;

        } else if (id == R.id.action_share_bluetooth) {
            Intent bluetoothIntent = new Intent(Intent.ACTION_SEND);
            bluetoothIntent.setType("text/plain");
            bluetoothIntent.putExtra(Intent.EXTRA_TEXT, "Découvrez ces stars !");
            bluetoothIntent.setPackage("com.android.bluetooth");
            startActivity(Intent.createChooser(bluetoothIntent, "Partager via"));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void init(){
        service.create(new Star("Leonardo dicaprio", "https://cdn.britannica.com/65/227665-050-D74A477E/American-actor-Leonardo-DiCaprio-2016.jpg?w=400&h=225&c=crop", 3.5f));
        service.create(new Star("The rock", "https://www.nme.com/wp-content/uploads/2023/07/dwayne-johnson-the-rock-actors-strike.jpg", 3));
        service.create(new Star("will smith",
                "https://www.looper.com/img/gallery/will-smiths-best-co-stars-ranked/intro-1643308597.jpg", 5));
        service.create(new Star("jason statham", "https://i0.wp.com/www.muscleandfitness.com/wp-content/uploads/2019/03/jason-statham-1109.jpg?quality=86&strip=all", 1));
        service.create(new Star("tom holland", "https://i.insider.com/5a43c31e8ba89220008b45ca?width=800&format=jpeg&auto=webp", 5));
        service.create(new Star("sophie turner", "https://images.pexels.com/photos/19292779/pexels-photo-19292779/free-photo-of-robe-occidentale-2024-shoot-par-dhanno-mayra-jaffri.jpeg", 1));
    }
}
