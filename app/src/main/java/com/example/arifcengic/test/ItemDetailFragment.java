package com.example.arifcengic.test;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "code"; //ARR


    /**
     * The dummy content this fragment is presenting.
     */
    private Item mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Content.item_map.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.Title);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the Violation Details
        if (mItem != null) {
            //ARR <------------------------------------------------------------------------
            ((TextView) rootView.findViewById(R.id.item_detail0)).setText(mItem.Code);
            ((TextView) rootView.findViewById(R.id.item_detail1)).setText(mItem.Description);
            ((TextView) rootView.findViewById(R.id.item_detail2)).setText(mItem.Ordinance);
            ((TextView) rootView.findViewById(R.id.item_detail3)).setText(mItem.Fine);

            ((TextView) rootView.findViewById(R.id.item_detail4)).setText(mItem.Pen1);
            ((TextView) rootView.findViewById(R.id.item_detail5)).setText(mItem.Pen2);
            ((TextView) rootView.findViewById(R.id.item_detail6)).setText(mItem.Pen3);
            ((TextView) rootView.findViewById(R.id.item_detail7)).setText(mItem.Pen4);
            //ARR ------------------------------------------------------------------------->
        }

        return rootView;
    }
}
