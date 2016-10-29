package com.ribasoftware.marvelproject.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ribasoftware.marvelproject.R;
import com.ribasoftware.marvelproject.api.Character;

public class CharacterDetailActivity extends AppCompatActivity {

    public static final String EXTRA_CHARACTER = "character";
    private Character mCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_detail);





    }
}
