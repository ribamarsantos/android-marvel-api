package com.ribasoftware.marvelproject.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ribasoftware.marvelproject.R;
import com.ribasoftware.marvelproject.api.Character;
import com.ribasoftware.marvelproject.api.ThumbnailCharacter;
import com.ribasoftware.marvelproject.dao.CharacterContract;
import com.ribasoftware.marvelproject.listener.OnCharacterClickListener;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by ribamar on 22/09/16.
 */

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.VH> {


    private List<Character> mCharacters;
    private Context mContext;
    private OnCharacterClickListener mCharacterClickListener;

    private Cursor mCursor;


    public CharacterAdapter(List<Character> mCharacters, Context mContext) {
        this.mCharacters = mCharacters;
        this.mContext = mContext;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        // load layout file
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_character,parent,false);

        // no livro nao usa static no git sim. o fato de ser static é
        // para nao ter que fazer o get Tag e set tag?

        VH viewHolder = new VH(view);
        // to recover this view Holder in the click event
        view.setTag(viewHolder);
        view.setOnClickListener(mClickListener);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        Character character;

        if ( mCursor != null){
            mCursor.moveToPosition(position);
            character = getCharacterFromCursor();
        }else {
            character = mCharacters.get(position);
        }

        Glide.with(mContext)
                .load(character.getThumbnail().getStandardMedium())

                .into(holder.imageThumbNail);

        Glide.with(mContext).load(character.getThumbnail().getStandardMedium())
                .bitmapTransform(new CropCircleTransformation(mContext))
                .into(holder.imageThumbNail);
        holder.txtCharacterName.setText(character.getName());

        //
        holder.txtCharacterName.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/MAINBRG_.TTF"));

    }

    @Override
    public int getItemCount() {
        if ( mCursor != null){
            return mCursor.getCount();
        }else {
            return mCharacters != null ? mCharacters.size() : 0;
        }
    }

    @Override
    public long getItemId(int position) {
        if ( mCursor != null){
            if ( mCursor.moveToPosition(position)){
                return mCursor.getLong(mCursor.getColumnIndex(CharacterContract._ID));
            }
        }
        return super.getItemId(position);
    }

    public Cursor getCursor(){
        return mCursor;
    }

    public void setCursor(Cursor newCursor){
        mCursor = newCursor;
        notifyDataSetChanged();
    }



    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Character character;

            if ( mCharacterClickListener != null){
                VH vh = (VH) view.getTag();

                int position = vh.getAdapterPosition();
                // if cursor is not null then set character by the cursor information
                // which means from database for now otherwise get from the list
                if ( mCursor != null){
                    mCursor.moveToPosition(position);
                    character = getCharacterFromCursor();
                }else{
                    character = mCharacters.get(position);
                }

                mCharacterClickListener.onCharacterClick(character, position);
            }
        }
    };

    public void setCharacterClickListener( OnCharacterClickListener clickListener){
        this.mCharacterClickListener = clickListener;
    }


    // View Holder of CharacterAdapter
    public class VH extends  RecyclerView.ViewHolder{
        ImageView imageThumbNail;
        TextView  txtCharacterName;

        public VH(View itemView) {
            super(itemView);
            imageThumbNail   = (ImageView) itemView.findViewById(R.id.character_item_image_thumbnail);
            txtCharacterName = (TextView)  itemView.findViewById(R.id.character_item_txt_name);


        }
    }

    private Character getCharacterFromCursor(){
        Character character = new Character();

        ThumbnailCharacter thumbnail = new ThumbnailCharacter();

        character.setId(mCursor.getLong(mCursor.getColumnIndex(CharacterContract._ID)));
        character.setIdWeb(mCursor.getInt(mCursor.getColumnIndex(CharacterContract.COL_ID_WEB)));
        character.setName(mCursor.getString(mCursor.getColumnIndex(CharacterContract.COL_NAME)));
        character.setDescription(mCursor.getString(mCursor.getColumnIndex(CharacterContract.COL_DESCRIPTION)));

        thumbnail.setExtension("jpg");
        thumbnail.setPath((mCursor.getString(mCursor.getColumnIndex(CharacterContract.COL_THUMBNAIL))));
        character.setThumbnail(thumbnail);

        character.setModified(mCursor.getString(mCursor.getColumnIndex(CharacterContract.COL_MODIFIED)));

        return character;
    }
}
