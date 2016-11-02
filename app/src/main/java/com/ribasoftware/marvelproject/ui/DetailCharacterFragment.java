package com.ribasoftware.marvelproject.ui;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ribasoftware.marvelproject.R;
import com.ribasoftware.marvelproject.api.Character;
import com.ribasoftware.marvelproject.api.Url;
import com.ribasoftware.marvelproject.dao.CharacterContract;
import com.ribasoftware.marvelproject.dao.CharacterProvider;
import com.ribasoftware.marvelproject.util.MarvelUtil;

public class DetailCharacterFragment extends Fragment {
    private static final String EXTRA_CHARACTER = "character";

    private Character mCharacter;

    private ShareActionProvider mShareActionProvider;

    //Views

    private TextView txtDescription;
    private TextView txtName;
    private TextView txtLastUpdate;
    private ImageButton btnUrlWiki;
    private ImageButton btnUrlDetail;
    private ImageView imgThumbnail;
    private RecyclerView recyclerviewComics;
    private FloatingActionButton fltactbtnAddFavorite;


    public static DetailCharacterFragment newInstance(Character character) {
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CHARACTER, character);

        DetailCharacterFragment fragment = new DetailCharacterFragment();
        fragment.setArguments(args);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // if its from phone get
        if (getResources().getBoolean(R.bool.phone)){
            mCharacter = getActivity().getIntent().getParcelableExtra(EXTRA_CHARACTER);
        }else {
            mCharacter = (Character) getArguments().getParcelable(EXTRA_CHARACTER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        this.btnUrlDetail       = (ImageButton) view.findViewById(R.id.btnDetailMore);
        this.btnUrlWiki         = (ImageButton) view.findViewById(R.id.btnWiki);
        this.txtDescription     = (TextView) view.findViewById(R.id.detail_text_character_description);
        this.txtName            = (TextView) view.findViewById(R.id.detail_text_character_name);
        this.imgThumbnail       = (ImageView) view.findViewById(R.id.detail_character_thumbnail);
        this.fltactbtnAddFavorite = (FloatingActionButton) view.findViewById(R.id.fabAddFavorite);

        this.fltactbtnAddFavorite.setOnClickListener( mClickListener);

        updateUI();


        return view;
    }
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            toggleFavorite();
        }
    };
    private void toggleFavorite() {
        if (mCharacter != null ){
            // verify if the Character is already a Favorite
           boolean isFavorite = isFavorite();

            if (!isFavorite) {
                long id = insertCharacter();
                if (id > 0) {
                    mCharacter.setId(id);
                }
            }

            //TODO - SetAction -> delete from Favorite

            Snackbar.make(getView(),
                    isFavorite ? R.string.msg_exist : R.string.msg_saved,
                    Snackbar.LENGTH_LONG)
                    .show();
        }

    }

    private long insertCharacter(){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CharacterContract.COL_ID_WEB , mCharacter.getIdWeb());
        contentValues.put(CharacterContract.COL_NAME   , mCharacter.getName());
        contentValues.put(CharacterContract.COL_DESCRIPTION   , mCharacter.getDescription());
        contentValues.put(CharacterContract.COL_THUMBNAIL    , mCharacter.getThumbnail().getPath());
        contentValues.put(CharacterContract.COL_MODIFIED  , mCharacter.getModified());


        Uri uri = getActivity().getContentResolver().insert(CharacterProvider.CHARACTERS_URI, contentValues);
        //TODO mensagem de erro ao falhar
        return ContentUris.parseId(uri);
    }

    private void updateUI() {
            getActivity().setTitle(mCharacter.getName());
            this.txtName.setText(mCharacter.getName());
            this.txtDescription.setText(mCharacter.getDescription());
            if ( mCharacter.getUrls() != null && mCharacter.getUrls().size() > 0) {

                for (Url url : mCharacter.getUrls()) {
                    if (url.getType().equalsIgnoreCase(MarvelUtil.URL_TYPE_DETAIL)) {
                        //this.txtUrlDetail.setMovementMethod(LinkMovementMethod.getInstance());
                        this.btnUrlDetail.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri uri = Uri.parse("http://www.example.com");
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                        //this.txtUrlDetail.setText(url.getUrl());
                    } else if (url.getType().equalsIgnoreCase(MarvelUtil.URL_TYPE_WIKI)) {
                        //this.txtUrlWiki.setText(url.getUrl());
                    }
                }
        Glide.with(this).load(mCharacter.getThumbnail().getStandardFantastic()).into(imgThumbnail);
        }

    }

    private boolean isFavorite(){
        Cursor cursor = getContext().getContentResolver()
                .query(CharacterProvider.CHARACTERS_URI,
                        new String[]    {CharacterContract._ID},
                        CharacterContract.COL_ID_WEB + " = ?",
                        new String[] { String.valueOf(mCharacter.getIdWeb())},
                        null
                );
        return cursor.getCount() > 0;

    }

}
