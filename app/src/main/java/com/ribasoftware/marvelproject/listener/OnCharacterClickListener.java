package com.ribasoftware.marvelproject.listener;

/**
 * Created by ribamar on 23/09/16.
 */
import com.ribasoftware.marvelproject.api.Character;

public interface OnCharacterClickListener {
    void onCharacterClick( Character character, int position);
}
