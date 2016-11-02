package com.ribasoftware.marvelproject.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ribasoftware.marvelproject.R;

public class MainFragment extends Fragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // FragmentPageAdapter with 2 pages
        CharactersPageAdapter charactersPageAdapter = new CharactersPageAdapter(getActivity().getSupportFragmentManager());
        // View page
        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        // load the adapter to the view page
        viewPager.setAdapter(charactersPageAdapter);
        // load the view page to the tabLayout
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    // content of viewpage
    // 0 - favoriteCharacterListFragment
    // 1 - CharacterListFragmet - get Character from Marvel API see api package

    class CharactersPageAdapter extends FragmentPagerAdapter {

        private static final int FAVORITE_CHARACTER_PAGE = 0;
        private static final int SEARCH_CHARACTER_PAGE = 1;

        public CharactersPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case FAVORITE_CHARACTER_PAGE: {
                    CharacterFavoriteListFragment favoriteListFragment = new CharacterFavoriteListFragment();
                    return favoriteListFragment;
                }
                case SEARCH_CHARACTER_PAGE: {
                    CharacterListFragment characterListFragment = new CharacterListFragment();
                    return characterListFragment;
                }
                default: {
                    throw new RuntimeException("invalid option");
                }
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // page title of each page using resource
            // this will help to set anylanguage default is write in English
            if (position == 1) return getString(R.string.tab_search);
            else return getString(R.string.tab_favorites);
        }
    }


}
