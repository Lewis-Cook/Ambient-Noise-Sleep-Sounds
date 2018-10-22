package uk.co.lewiscook.ambientnoises;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    public static String IMAGE_BUNDLE_KEY = "ImageBundleID";

    public static String AUDIO_BUNDLE_KEY = "AudioResID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<String> noises = new ArrayList<>();
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

                mFirebaseAnalytics = FirebaseAnalytics.getInstance(MainActivity.this);
                Bundle bundle = new Bundle();
                bundle.putString("PageTitle", noises.get(position));
                mFirebaseAnalytics.logEvent("PageOpened", bundle);

                if (position == 0) {
                    Intent intentLake = new Intent(MainActivity.this, AudioActivity.class);
                    intentLake.putExtra(IMAGE_BUNDLE_KEY, R.drawable.lake);
                    intentLake.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_calm_lake);
                    startActivity(intentLake);
                } else if (position == 1) {
                    Intent intentRain = new Intent(MainActivity.this, AudioActivity.class);
                    intentRain.putExtra(IMAGE_BUNDLE_KEY, R.drawable.new_york_city);
                    intentRain.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_city_rain);
                    startActivity(intentRain);
                } else if (position == 2) {
                    Intent intentWind = new Intent(MainActivity.this, AudioActivity.class);
                    intentWind.putExtra(IMAGE_BUNDLE_KEY, R.drawable.rain_forest);
                    intentWind.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_rain_forest);
                    startActivity(intentWind);
                } else if (position == 3) {
                    Intent intentCreek = new Intent(MainActivity.this, AudioActivity.class);
                    intentCreek.putExtra(IMAGE_BUNDLE_KEY, R.drawable.creek_4);
                    intentCreek.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_stream);
                    startActivity(intentCreek);
                } else if (position == 4) {
                    Intent intentForest = new Intent(MainActivity.this, AudioActivity.class);
                    intentForest.putExtra(IMAGE_BUNDLE_KEY, R.drawable.rainy_forest);
                    intentForest.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_tent_woods);
                    startActivity(intentForest);
                } else if (position == 5) {
                    Intent intentLightLake = new Intent(MainActivity.this, AudioActivity.class);
                    intentLightLake.putExtra(IMAGE_BUNDLE_KEY, R.drawable.waves_lapping);
                    intentLightLake.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_waves_shore);
                    startActivity(intentLightLake);
                } else if (position == 6) {
                    Intent intentLightWind = new Intent(MainActivity.this, AudioActivity.class);
                    intentLightWind.putExtra(IMAGE_BUNDLE_KEY, R.drawable.waves_crashing_2);
                    intentLightWind.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_waves_breaking);
                    startActivity(intentLightWind);
                } else if (position == 7) {
                    Intent intentOcean = new Intent(MainActivity.this, AudioActivity.class);
                    intentOcean.putExtra(IMAGE_BUNDLE_KEY, R.drawable.rainy_ocean);
                    intentOcean.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_rain_ocean);
                    startActivity(intentOcean);
                } else if (position == 8) {
                    Intent intentRiver = new Intent(MainActivity.this, AudioActivity.class);
                    intentRiver.putExtra(IMAGE_BUNDLE_KEY, R.drawable.stream_2);
                    intentRiver.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_river);
                    startActivity(intentRiver);
                } else if (position == 9) {
                    Intent intentWaterfall = new Intent(MainActivity.this, AudioActivity.class);
                    intentWaterfall.putExtra(IMAGE_BUNDLE_KEY, R.drawable.waterfall);
                    intentWaterfall.putExtra(AUDIO_BUNDLE_KEY, R.raw.ambient_waterfall);
                    startActivity(intentWaterfall);
                }
            }
        });

    }
}