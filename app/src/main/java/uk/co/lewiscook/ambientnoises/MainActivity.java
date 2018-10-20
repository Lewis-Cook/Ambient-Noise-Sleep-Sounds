package uk.co.lewiscook.ambientnoises;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Static MediaPlayer so all classes can access it
    public static PerfectLoopMediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayList<String> noises = new ArrayList<>();
        noises.add("Calm Lake");
        noises.add("Rain in the City");
        noises.add("Rain Forest");
        noises.add("Trickling Stream");
        noises.add("Tent in the Woods");
        noises.add("Waves on the Shore");
        noises.add("Ocean Waves Breaking");
        noises.add("Rain on the Ocean");
        noises.add("Fast Moving River");
        noises.add("Waterfall");

        ArrayAdapter adapter = new ArrayAdapter<>(MainActivity.this, R.layout.custom_listview, noises);

        ListView list = findViewById(R.id.list_view);

        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position == 0) {
                    Intent intentLake = new Intent(MainActivity.this, CalmLake.class);
                    intentLake.putExtra("resID", R.drawable.lake);
                    startActivity(intentLake);
                } else if (position == 1) {
                    Intent intentRain = new Intent(MainActivity.this, RainInCity.class);
                    intentRain.putExtra("resID", R.drawable.new_york_city);
                    startActivity(intentRain);
                } else if (position == 2) {
                    Intent intentWind = new Intent(MainActivity.this, RainForest.class);
                    intentWind.putExtra("resID", R.drawable.rain_forest);
                    startActivity(intentWind);
                }
                else if (position == 3) {
                    Intent intentCreek = new Intent(MainActivity.this, TricklingStream.class);
                    intentCreek.putExtra("resID", R.drawable.creek_4);
                    startActivity(intentCreek);
                }
                else if (position == 4) {
                    Intent intentForest = new Intent(MainActivity.this, TentInWoods.class);
                    intentForest.putExtra("resID", R.drawable.rainy_forest);
                    startActivity(intentForest);
                }
                else if (position == 5) {
                    Intent intentLightLake = new Intent(MainActivity.this, WavesOnShore.class);
                    intentLightLake.putExtra("resID", R.drawable.waves_lapping);
                    startActivity(intentLightLake);
                }
                else if (position == 6) {
                    Intent intentLightWind = new Intent(MainActivity.this, WavesBreaking.class);
                    intentLightWind.putExtra("resID", R.drawable.waves_crashing_2);
                    startActivity(intentLightWind);
                }
                else if (position == 7) {
                    Intent intentOcean = new Intent(MainActivity.this, RainOnOcean.class);
                    intentOcean.putExtra("resID", R.drawable.rainy_ocean);
                    startActivity(intentOcean);
                }
                else if (position == 8) {
                    Intent intentRiver = new Intent(MainActivity.this, FastRiver.class);
                    intentRiver.putExtra("resID", R.drawable.stream_2);
                    startActivity(intentRiver);
                }
                else if (position == 9) {
                    Intent intentWaterfall = new Intent(MainActivity.this, Waterfall.class);
                    intentWaterfall.putExtra("resID", R.drawable.waterfall);
                    startActivity(intentWaterfall);
                }
            }
        });

    }
}