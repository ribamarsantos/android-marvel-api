package com.ribasoftware.marvelproject.http;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.ribasoftware.marvelproject.api.Character;
import com.ribasoftware.marvelproject.api.CharacterDataWrapper;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.offset;

/**
 * Created by ribamar on 18/09/16.
 */
public class CharacterSearchTask extends AsyncTaskLoader<List<Character>> {

    private List<Character> characters;
    private String query;
    private CharacterDataWrapper dataWrapper;
    // keep the offset number
    private int offSet;
    // keep the total count

    public CharacterSearchTask(Context context, String query) {
        super(context);
        Log.d("RMS", "constructor - CharacterSearchTask");
        this.offSet = 0;
        this.characters = new ArrayList<>();
        this.query = query;
    }

    @Override
    public List<Character> loadInBackground() {

        dataWrapper = CharacterHttp.getAllCharacter(query, offset);
        if (dataWrapper != null && dataWrapper.getData() != null && dataWrapper.getData().getResults().size() > 0) {
            characters = dataWrapper.getData().getResults();
            return characters;
        } else {
            return null;
        }


    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (query != null) {
            forceLoad();
        } else {
            deliverResult(characters);
        }
    }

    @Override
    public void deliverResult(List<Character> data) {
        characters = data;
        super.deliverResult(data);
    }
}
