package com.ribasoftware.marvelproject.dao;

import android.provider.BaseColumns;

/**
 * Created by ribamar on 06/10/16.
 */

public interface ComicContract extends BaseColumns {
    String TB_COMIC    = "Comic";

    String COL_ID_WEB      = "idweb";
    String COL_TITLE       = "title";
    String COL_DESCRIPTION = "description";
    String COL_PAGERCOUNT  = "pager_count";
    String COL_MODIFIED    = "modified";
    String COL_ISBN        = "isbn";
    String COL_EAN         = "ean";



}

/*
"id": 42882,
        "digitalId": 26110,
        "title": "Lorna the Jungle Girl (1954) #6",
        "issueNumber": 6,
        "variantDescription": "",
        "description": null,
        "modified": "2015-10-15T11:13:52-0400",
        "isbn": "",
        "upc": "",
        "diamondCode": "",
        "ean": "",
        "issn": "",
        "format": "Comic",
        "pageCount": 32,
        "textObjects": [],
        "resourceURI": "http://gateway.marvel.com/v1/public/comics/42882",
        "urls": [
*/