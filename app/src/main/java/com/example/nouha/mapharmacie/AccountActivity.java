package com.example.nouha.mapharmacie;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomAdapter;
import wrapper.ListItemWrapper;


public class AccountActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);


        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AccountFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
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
    public static class AccountFragment extends Fragment {

        private EditText mEditTextName;
        private EditText mEditTextLastName;



        JSONParser jsonParser = new JSONParser();

        private static String url_create_user = "http://192.168.1.5:8888/Android/create_user_account.php";

        // JSON Node names
        private static final String TAG_SUCCESS = "success";


        EditText inputName;
        EditText inputLastName;
        EditText inputPhone;
        EditText inputEmail;
        EditText inputSexe;
        EditText inputNaissance;
        Button saveAccount;
        public AccountFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_account, container, false);

            inputName = (EditText)rootView.findViewById(R.id.Name);
            inputLastName = (EditText) rootView.findViewById(R.id.LastName);
            inputPhone = (EditText) rootView.findViewById(R.id.Phone);
            inputEmail = (EditText) rootView.findViewById(R.id.Email);
            inputSexe = (EditText) rootView.findViewById(R.id.Sexe);
            inputNaissance = (EditText) rootView.findViewById(R.id.DateNaissance);
            saveAccount = (Button) rootView.findViewById(R.id.save_account);

            saveAccount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // creating new product in background thread
                    new CreateNewUser().execute();

                }
            });
            return rootView;
        }

        private ListItemWrapper createListItem() {


            ListItemWrapper listItemWrapper = new ListItemWrapper();

            listItemWrapper.setName(inputName.getText().toString());

            listItemWrapper.setLastName(inputLastName.getText().toString());



            return listItemWrapper;
        }
        public interface OnAddListener {

            public void onOkClicked(ListItemWrapper listItemWrapper);


        }
public class CreateNewUser extends AsyncTask<String,String, String>
        {
            /**
             * Before starting background thread Show Progress Dialog
             * */

            protected String doInBackground(String... args) {

            String Name = inputName.getText().toString();
            String LastName = inputLastName.getText().toString();
            String Phone = inputPhone.getText().toString();
            String Email = inputEmail.getText().toString();
            String Sexe = inputSexe.getText().toString();
            String DateNaissance = inputNaissance.getText().toString();

            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("Name", Name));
            params.add(new BasicNameValuePair("LastName", LastName));
            params.add(new BasicNameValuePair("Phone", Phone));
            params.add(new BasicNameValuePair("Email", Email));
                params.add(new BasicNameValuePair("Sexe", Sexe));
                params.add(new BasicNameValuePair("DateNaissance", DateNaissance));

            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_user,
                    "POST", params);

            // check log cat fro response
           Log.d("Create Response", json.toString());

            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    //successfully created user

                    Intent i = new Intent(getActivity().getApplicationContext(), AffichageAccount.class);
                    startActivity(i);

                    // closing this screen
                    getActivity().finish();
                }
                else {
                    // failed to create user
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        }

    }
}
