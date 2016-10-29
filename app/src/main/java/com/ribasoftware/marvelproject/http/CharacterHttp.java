package com.ribasoftware.marvelproject.http;

import android.util.Log;

import com.google.gson.Gson;
import com.ribasoftware.marvelproject.api.AuthAPI;
import com.ribasoftware.marvelproject.api.CharacterDataWrapper;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ribamar on 16/09/16.
 */
public class CharacterHttp {

    public static final HttpUrl API_BASE_URL    = HttpUrl.parse("http://gateway.marvel.com/v1/public");
    private static final String PATH_CHARACTER = "characters";

    // URL's params
    public static final String TIME_STAMP_KEY   = "ts";
    public static final String HASH_KEY         = "hash";
    public static final String API_KEY          = "apikey";
    public static final String NAME_STARTS_WITH = "nameStartsWith";
    // limit is 100
    public static final String LIMIT            = "limit";
    //Order the result set by a field or fields.
    // Add a "-" to the value sort in descending order.
    // Multiple values are given priority in the order in which they are passed.
    public static final String ORDER_BY         = "orderBy";

    public static CharacterDataWrapper getAllCharacter(String query, int offset){
        // characters list to return
        Log.d("RMS", "getAllCharacter - HTTP");
        CharacterDataWrapper dataWrapper = null;
        OkHttpClient client = new OkHttpClient();
        AuthAPI authAPI     = new AuthAPI();

        if ( query == null) query = "";

        HttpUrl url = API_BASE_URL.newBuilder()
                .addPathSegment(PATH_CHARACTER)
                .addQueryParameter(TIME_STAMP_KEY, authAPI.getTimeStamp())
                .addQueryParameter(API_KEY, authAPI.getPublicKey() )
                .addQueryParameter(HASH_KEY, authAPI.getMd5Key())
                //.addQueryParameter(LIMIT, "100")
                .addQueryParameter(NAME_STARTS_WITH, query.replaceAll("[^A-Za-z0-9 ]", "").trim())
                .build();

        Request request =  new Request.Builder().url(url).build();

        Response response = null;

        try {

            response = client.newCall(request).execute();

            String json = response.body().string();

            Gson gson = new Gson();

            dataWrapper = gson.fromJson(json, CharacterDataWrapper.class);


        }catch ( Exception e){
            if ( dataWrapper != null) Log.d("RMS","erro return code -> " + String.valueOf(dataWrapper.getCode()));
            e.printStackTrace();
        }
        return dataWrapper;

    }
}