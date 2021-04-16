package tech.erichier.zoomlauncher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button zoom = findViewById(R.id.open);
        Button close = findViewById(R.id.close);

        zoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("us.zoom.videomeetings");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                }
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://rwth.zoom.us/j/93496606108"));
                startActivity(browserIntent);
            }
        });

        loadLectures();
    }

    void loadLectures() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://zb.robindecker.me/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        ObjectMapper mapper = new ObjectMapper();
                        try {
                            Lecture[] lectures = mapper.readValue(new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8), Lecture[].class);

                            setupListView(lectures);
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(), "Could not parse lectures", Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Could not fetch lectures", Toast.LENGTH_LONG).show();
            }
        });

        queue.add(stringRequest);
    }

    void setupListView(final Lecture[] lectures) {
        final ListView listview = findViewById(R.id.listview);
        ArrayList<String> strings = new ArrayList<>();

        for (Lecture l : lectures)
            strings.add(l.toString());

        final StableArrayAdapter adapter = new StableArrayAdapter(this, android.R.layout.simple_list_item_1, strings);
        listview.setAdapter(adapter);

        listview.setSelection(0);
        listview.requestFocus();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(lectures[position].url));
                startActivity(browserIntent);
            }
        });
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {
        HashMap<String, Integer> mIdMap = new HashMap<>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
