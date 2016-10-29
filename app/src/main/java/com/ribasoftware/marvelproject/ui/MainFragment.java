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


    public MainFragment() {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        CharactersPageAdapter charactersPageAdapter = new CharactersPageAdapter(getActivity().getSupportFragmentManager());

        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
        viewPager.setAdapter(charactersPageAdapter);

        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();

        return fragment;
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
            if (position == 1) return getString(R.string.tab_search);
            else return getString(R.string.tab_favorites);
        }
    }


}
