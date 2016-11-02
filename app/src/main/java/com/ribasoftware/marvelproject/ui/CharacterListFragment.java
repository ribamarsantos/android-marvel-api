package com.ribasoftware.marvelproject.ui;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ribasoftware.marvelproject.R;
import com.ribasoftware.marvelproject.adapter.CharacterAdapter;
import com.ribasoftware.marvelproject.api.Character;
import com.ribasoftware.marvelproject.http.CharacterSearchTask;
import com.ribasoftware.marvelproject.listener.OnCharacterClickListener;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;


public class CharacterListFragment extends Fragment {
    // ID of loader manager
    private static final int ID_LOADER = 0;
    private static final String QUERY_PARAM = "search_param";
    // characters to be loades on the Adapter
    private List<Character> mCharacters;

    private CharacterAdapter mAdapter;
    //Views
    private SearchView mSearchView;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private View mLoading;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        if (mCharacters == null) {
            mCharacters = new ArrayList();
        }
        mAdapter = new CharacterAdapter(mCharacters, getContext());
        mAdapter.setCharacterClickListener(mClickListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_character_list, container, false);

        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recyclerview_Character);
        mEmptyView = layout.findViewById(R.id.empty_result);
        mEmptyView.setVisibility(View.GONE);
        mLoading = layout.findViewById(R.id.loading);

        setLayout();

        SlideInLeftAnimationAdapter alphaAdapter = new SlideInLeftAnimationAdapter(mAdapter);
        alphaAdapter.setDuration(2000);

        mRecyclerView.setAdapter(alphaAdapter);

        // ini loader verifies is the loader with ID ID_LOADER is already instance then don't call again
        getActivity().getSupportLoaderManager().initLoader(ID_LOADER, null, loaderCallbacks);
        return layout;
    }

    private void setLayout() {
        if (getResources().getBoolean(R.bool.phone)) {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
            } else {
                mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            }
        }

        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        // inflate the menu_search layout in the menu options
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);

        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        mSearchView.setOnQueryTextListener(searchListener);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    // ===================
    //Listener SearchView
    //====================
    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mLoading.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            startCharacterLoader(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }


    };

    private void startCharacterLoader(String query) {
        Bundle params = new Bundle();
        params.putString(QUERY_PARAM, query);
        getActivity().getSupportLoaderManager().restartLoader(ID_LOADER, params, loaderCallbacks);
        if (mSearchView != null)
            mSearchView.clearFocus();
    }

    // Callback of LoaderManager

    private LoaderManager.LoaderCallbacks<List<Character>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Character>>() {


        @Override
        public Loader<List<Character>> onCreateLoader(int id, Bundle args) {
            String query = args != null ? args.getString(QUERY_PARAM) : null;
            return new CharacterSearchTask(getContext(), query);
        }

        @Override
        public void onLoadFinished(Loader<List<Character>> loader, List<Character> characters) {
            if (characters != null && characters.size() > 0) {
                mCharacters.clear();
                mCharacters.addAll(characters);
                mAdapter.notifyDataSetChanged();
                mEmptyView.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
            mLoading.setVisibility(View.GONE);
        }

        @Override
        public void onLoaderReset(Loader<List<Character>> loader) {

        }
    };


    private OnCharacterClickListener mClickListener = new OnCharacterClickListener() {
        @Override
        public void onCharacterClick(Character character, int position) {
            // to work with table version 4.4.2
            // it was necessary to create -sw420dp intead of sw600dp

            // using bools.xml to verify if its phone or tablet

            if (getResources().getBoolean(R.bool.phone)) {
                Intent it = new Intent(getActivity(), CharacterDetailActivity.class);

                it.putExtra(CharacterDetailActivity.EXTRA_CHARACTER, character);
                startActivity(it);
            } else {
                DetailCharacterFragment detailCharacterFragment = DetailCharacterFragment.newInstance(character);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.placeholderDetail, detailCharacterFragment)
                        .commit();
            }
        }
    };


}
