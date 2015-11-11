package com.example.nouha.mapharmacie;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PharmacyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pharmacy);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PharmacyFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pharmacy, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PharmacyFragment extends Fragment {

        // Progress Dialog
        private ProgressDialog pDialog;
        // Creating JSON Parser object
        JSONParser jParser = new JSONParser();

        ArrayList<HashMap<String, String>> productsList;

        // url to get all products list
        private static String url_all_pharmacy = "http://192.168.1.3:8888/Android/get_all_pharmacy.php";

        // JSON Node names
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_PHARMACY = "pharmacy";
        private static final String TAG_TYPE = "type";
        private static final String TAG_NAME = "name";

        // products JSONArray
        JSONArray pharmacy = null;
        public PharmacyFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_pharmacy, container, false);
            return rootView;
        }
        class LoadAllProducts extends AsyncTask<String, String, String> {

            /**
             * Before starting background thread Show Progress Dialog
             * */


            /**
             * getting All pharmacy from url
             * */
            protected String doInBackground(String... args) {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                // getting JSON string from URL
                JSONObject json = jParser.makeHttpRequest(url_all_pharmacy, "GET", params);

                // Check your log cat for JSON reponse
                Log.d("All Pharmacy: ", json.toString());

                try {
                    // Checking for SUCCESS TAG
                    int success = json.getInt(TAG_SUCCESS);

                    if (success == 1) {
                        // products found
                        // Getting Array of Products
                        pharmacy = json.getJSONArray(TAG_PHARMACY);

                        // looping through All Products
                        for (int i = 0; i < pharmacy.length(); i++) {
                            JSONObject c = pharmacy.getJSONObject(i);

                            // Storing each json item in variable
                            String type = c.getString(TAG_TYPE);
                            String name = c.getString(TAG_NAME);

                            // creating new HashMap
                            HashMap<String, String> map = new HashMap<String, String>();

                            // adding each child node to HashMap key => value
                            map.put(TAG_TYPE, type);
                            map.put(TAG_NAME, name);

                            // adding HashList to ArrayList
                            productsList.add(map);
                        }
                    } else {
                        // no products found
                        // Launch Add New product Activity
                        //il manque l'affichage de la liste de pharmacie

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            /**
             * After completing background task Dismiss the progress dialog
             * **/
            protected void onPostExecute(String file_url) {

            }

        }
    }
}
