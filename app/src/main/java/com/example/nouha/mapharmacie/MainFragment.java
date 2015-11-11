package com.example.nouha.mapharmacie;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import recyclerview.FeedListActivity;

/**
 * Created by Nouha on 07/03/2015.
 */
public class MainFragment extends Fragment {

    Button myaccount;
    Button myDrugStore;
    Button myHelp;
    Button myprescription;
    Button mytreatement;


    @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            myaccount  = (Button)  rootView.findViewById(R.id.my_account);
            myDrugStore = (Button) rootView.findViewById(R.id.my_pharmacy);
            myHelp =(Button) rootView.findViewById(R.id.my_help);
            myprescription =(Button) rootView.findViewById(R.id.my_prescription);
            mytreatement = (Button) rootView.findViewById(R.id.my_treatement);


            myaccount.setOnClickListener(onClickListener);
            myDrugStore.setOnClickListener(onClickListener);
            myHelp.setOnClickListener(onClickListener);
            myprescription.setOnClickListener(onClickListener);
            mytreatement.setOnClickListener(onClickListener);



           // myaccount.setOnClickListener(new View.OnClickListener() {
             //   public void onClick(View arg0) {

                    // Start AccountActivity.class
            //                    Intent myIntent = new Intent(getActivity(),
  //                          AccountActivity.class);
    //                startActivity(myIntent);
      //          }
        //    });

            return rootView;
        }
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.my_account:
                {
                    Intent myIntent = new Intent(getActivity(), AccountActivity.class);
                    startActivity(myIntent);
                }
                    break;
                case R.id.my_treatement:
                {
                    Intent treatement = new Intent(getActivity(), FeedListActivity.class);
                    startActivity(treatement);


                }
                break;
                case R.id.my_pharmacy:
                {
                    //DO something
                    Intent myMap = new Intent(getActivity(), MapsActivity.class);
                    startActivity(myMap);

                }

                    break;
                case R.id.my_help:
                {
                    //DO something
                    Intent help = new Intent(getActivity(), SendEmail.class);
                    startActivity(help);

                }
                break;

                case R.id.my_prescription:
                {
                    Intent prescription = new Intent(getActivity(), PhotoIntentActivity.class);
                    startActivity(prescription);
                }

                break;

            }

        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {

           return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
