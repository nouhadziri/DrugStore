package com.example.nouha.mapharmacie;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
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

import java.util.ArrayList;

import Adapter.CustomAdapter;
import wrapper.ListItemWrapper;


public class AffichageAccount extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affichage_account);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new AffichageFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_affichage_account, menu);
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
    public static class AffichageFragment extends Fragment {

        private CustomAdapter mAdapter;
        private ArrayList<ListItemWrapper> mObjectList;
        private static final String LIST_CONTENT_KEY = "list_content_key";

        public AffichageFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_affichage_account, container, false);
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

            if (savedInstanceState == null) {
                mObjectList = new ArrayList<ListItemWrapper>();
            } else {
                mObjectList = (ArrayList<ListItemWrapper>) savedInstanceState.getSerializable(LIST_CONTENT_KEY);
            }

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            recyclerView.setHasFixedSize(true);

            // use a linear layout manager
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);

            // specify an adapter (see also next example)
            mAdapter = new CustomAdapter(mObjectList);
            recyclerView.setAdapter(mAdapter);
            setHasOptionsMenu(true);

            return rootView;
        }
        @Override
        public void onSaveInstanceState(Bundle outState) {

            outState.putSerializable(LIST_CONTENT_KEY, mObjectList);

            super.onSaveInstanceState(outState);
        }

        public void onOkClicked(ListItemWrapper listItemWrapper) {
            Log.v("slim", "catch ok button click :" + listItemWrapper.getName());
            mObjectList.add(listItemWrapper);
            mAdapter.notifyDataSetChanged();
        }
    }
}
