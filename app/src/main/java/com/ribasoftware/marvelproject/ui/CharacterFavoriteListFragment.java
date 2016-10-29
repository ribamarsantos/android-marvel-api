package com.ribasoftware.marvelproject.ui;


import android.content.Context;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ribasoftware.marvelproject.R;
import com.ribasoftware.marvelproject.adapter.CharacterAdapter;
import com.ribasoftware.marvelproject.api.Character;
import com.ribasoftware.marvelproject.api.ThumbnailCharacter;
import com.ribasoftware.marvelproject.dao.CharacterContract;
import com.ribasoftware.marvelproject.dao.CharacterProvider;
import com.ribasoftware.marvelproject.listener.OnCharacterClickListener;

public class CharacterFavoriteListFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private RecyclerView mRecyclerView;
    private CharacterAdapter mAdapter;


    public static CharacterFavoriteListFragment newInstance() {
        CharacterFavoriteListFragment fragment = new CharacterFavoriteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        mAdapter = new CharacterAdapter(null, getContext());
        mAdapter.setCharacterClickListener(mClickListener);
        Log.d("RMS", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_character_favorite_list, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_FavoriteCharacter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        }
        mRecyclerView.setAdapter( mAdapter);

        getLoaderManager().initLoader(0, null, this);

        return view;

    }

//    private LoaderManager.LoaderCallbacks cursorLoader = new LoaderManager.LoaderCallbacks<Cursor>() {


        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            return new CursorLoader(getActivity(),
                    CharacterProvider.CHARACTERS_URI,
                    CharacterContract.LIST_COLUMNS, null, null, null);

        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            mAdapter.setCursor(data);
//
//            if (data != null &&
//                    data.getCount() > 0
//                    ) {
//                selectCharacter(null, 0, data);
//            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.setCursor(null);

        }
//    };

    private void selectCharacter(View view, int position, Cursor cursor) {
        if (cursor.moveToPosition(position)) {
            Character character = new Character();
            character.setId(cursor.getLong(cursor.getColumnIndex(CharacterContract._ID)));
            character.setIdWeb(cursor.getInt(cursor.getColumnIndex(CharacterContract.COL_ID_WEB)));
            character.setName(cursor.getString(cursor.getColumnIndex(CharacterContract.COL_NAME)));
            character.setDescription(cursor.getString(cursor.getColumnIndex(CharacterContract.COL_DESCRIPTION)));
            character.setModified(cursor.getString(cursor.getColumnIndex(CharacterContract.COL_MODIFIED)));

            ThumbnailCharacter thumbnailCharacter = new ThumbnailCharacter();
            thumbnailCharacter.setPath(cursor.getString(cursor.getColumnIndex(CharacterContract.COL_THUMBNAIL)));
            thumbnailCharacter.setExtension("jpg");

            character.setThumbnail(thumbnailCharacter);


        }

    }

    private OnCharacterClickListener mClickListener = new OnCharacterClickListener() {
        @Override
        public void onCharacterClick(Character character, int position) {
            //
            Toast.makeText(getContext(), "Test" + character.getName(), Toast.LENGTH_SHORT).show();
        }
    };


}
