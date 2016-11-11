package com.example.codetribe.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Codetribe on 11/3/2016.
 */
public class WordAdopter extends ArrayAdapter<Word> {


    private int ColorResource;

    public WordAdopter(Context context, ArrayList<Word> words, int colorResource) {
        super(context, 0, words);
        ColorResource = colorResource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listView = convertView;

        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        TextView defult = (TextView) listView.findViewById(R.id.textView);
        TextView translate = (TextView) listView.findViewById(R.id.textView2);
        ImageView imageView = (ImageView) listView.findViewById(R.id.imageView);
        Word currentWord = getItem(position);
        defult.setText(currentWord.getDefaultTranslation());
        translate.setText(currentWord.getMiwokTranslation());
        if (currentWord.hasImage()) {

            Glide.with(getContext()).load(currentWord.getImageRource()).error(currentWord.getImageRource()).crossFade().into(imageView);
//           Picasso.with(getContext()).load(currentWord.getImageRource()).error(currentWord.getImageRource()).into(imageView, new Callback() {
//               @Override
//               public void onSuccess() {
//                   Toast.makeText(getContext(),"Loading...",Toast.LENGTH_SHORT).show();
//               }
//
//               @Override
//               public void onError() {
//
//               }
//           });
            // imageView.setImageResource(currentWord.getImageRource());
            //imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        View textContainer = listView.findViewById(R.id.linearText);
        int color = ContextCompat.getColor(getContext(), ColorResource);
        textContainer.setBackgroundColor(color);
        return listView;
    }
}
