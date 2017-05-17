package com.example.arifcengic.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    //ARR <-----------------------------------------------
    private List<Item> items = new ArrayList<>();
    private int fileIndex = 0;
    RecyclerView recyclerView;
    List<Integer> dataFileResourceIds;
    private static final String TAG = "ItemListActivity";
    //ARR ----------------------------------------------->

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());



        //ARR <---------------------------------------------
        dataFileResourceIds = new ArrayList<Integer>();

        try {

            //create list of available data file ids for additional requirement
            for(Field f : R.raw.class.getFields()) {
                String filename = f.getName();
                if (filename.startsWith("ptviol")) dataFileResourceIds.add(f.getInt(f));
            }

            LoadViolations(R.raw.ptviol_c); //Picked ptviol_c to start
        }  catch(Exception e){
            Log.e(TAG, "Error loading ptviol data");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bonus requirement to change between available ptviol files
                fileIndex++;
                if (fileIndex >= dataFileResourceIds.size()) fileIndex = 0; //reset file index,start over
                int rId = dataFileResourceIds.get(fileIndex);

                try {
                    LoadViolations(rId);
                    recyclerView.swapAdapter(new SimpleItemRecyclerViewAdapter(items), false);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                //get file name from resource id
                String fileName  = getResources().getResourceEntryName(rId);
                //show user name of the file is viewing
                Snackbar.make(view, "Switching to " +  fileName, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });
        //ARR --------------------------------------------->

        recyclerView = (RecyclerView) findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

    }

    //ARR <----------------------------------------------------------
    private void LoadViolations(int resourceId) throws IOException {

        InputStream inputStream = getResources().openRawResource(resourceId);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;

        //For bonus requirement we'll just reuse items and items-map
        //No caching so file is processed  each time user asks for it
        this.items.clear();
        Content.item_map.clear();

        while((line = bufferedReader.readLine()) != null) {
            if (line.startsWith("#")) continue;
            Item item = new Item(line);

            this.items.add(item);
            Content.item_map.put(item.Code, item);
        }

    }
    //ARR ------------------------------------------------------------>

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this.items));
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Item> mValues;

        public SimpleItemRecyclerViewAdapter(List<Item> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);
            holder.mIdView.setText(mValues.get(position).Fine);
            holder.mContentView.setText(mValues.get(position).Description);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.Code);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.Code);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mIdView;
            public final TextView mContentView;
            public Item mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (TextView) view.findViewById(R.id.id);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }
}
