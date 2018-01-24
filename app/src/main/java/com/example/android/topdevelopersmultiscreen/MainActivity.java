package com.example.android.topdevelopersmultiscreen;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.topdevelopersmultiscreen.models.ItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mainListView);
        listView.setOnItemClickListener(new ListClickHandler());

        new GetJSON().execute("https://api.stackexchange.com/2.2/users?page=1&pagesize=10&order=desc&sort=reputation&site=stackoverflow");
    }

    public class ItemAdapter extends ArrayAdapter {

        private List<ItemModel> itemModelList;
        private int resource;
        private LayoutInflater inflater;

        public ItemAdapter(@NonNull Context context, int resource, List<ItemModel> objects) {
            super(context, resource, objects);
            itemModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if(convertView == null) {
                convertView = inflater.inflate(resource, null);
            }
            TextView nameTextView = findViewById(R.id.nameTextView);
            //ImageView imageView = findViewById(R.id.imageView);

            nameTextView.setText(itemModelList.get(position).getDisplay_name());


            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    public class ListClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView listText = view.findViewById(R.id.nameTextView);
            String textName = listText.getText().toString();

            Intent intent = new Intent(MainActivity.this, SecondActivity.class);
            intent.putExtra("selected-item", textName);
            startActivity(intent);
        }
    }

    class GetJSON extends AsyncTask<String, String, List<ItemModel>> {

        @Override
        protected List<ItemModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try{
                URL urlStackOverflow = new URL(params[0]);
                connection = (HttpURLConnection) urlStackOverflow.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("items");

                List<ItemModel> itemModelList = new ArrayList<>();
                for(int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ItemModel itemModel = new ItemModel();
                    itemModel.setDisplay_name(finalObject.getString("display_name"));
                    itemModel.setProfile_image(finalObject.getString("profile_image"));
                    itemModel.setLocation(finalObject.getString("location"));

                    List<ItemModel.BadgeCounts> badgeCountsList = new ArrayList<>();

                    JSONObject badges = finalObject.getJSONObject("badge_counts");
                    ItemModel.BadgeCounts badgeCounts = new ItemModel.BadgeCounts();
                    badgeCounts.setBronze(badges.optInt("bronze"));
                    badgeCounts.setGold(badges.optInt("gold"));
                    badgeCounts.setSilver(badges.optInt("silver"));
                    badgeCountsList.add(badgeCounts);
                    itemModel.setBadge_counts(badgeCountsList);
                    itemModelList.add(itemModel);
                }
                return itemModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<ItemModel> s) {
            super.onPostExecute(s);
            ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.layout_item, s);
            listView.setAdapter(adapter);
        }
    }
}

