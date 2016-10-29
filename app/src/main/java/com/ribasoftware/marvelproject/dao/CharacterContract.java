package com.ribasoftware.marvelproject.dao;

import android.provider.BaseColumns;

/**
 * Created by ribamar on 06/10/16.
 */
public interface CharacterContract extends BaseColumns {

    String TB_CHARACTER    = "Character";

    String COL_ID_WEB      = "idweb";
    String COL_NAME        = "name";
    String COL_DESCRIPTION = "description";
    String COL_MODIFIED    = "modified";
    String COL_THUMBNAIL   = "thumbnail";

    String [] LIST_COLUMNS = new String[]{
            CharacterContract._ID,
            CharacterContract.COL_ID_WEB,
            CharacterContract.COL_NAME,
            CharacterContract.COL_DESCRIPTION,
            CharacterContract.COL_MODIFIED,
            CharacterContract.COL_THUMBNAIL

    };

//    private ComicsCharacter comics;


}


