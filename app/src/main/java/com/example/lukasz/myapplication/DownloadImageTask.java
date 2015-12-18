package com.example.lukasz.myapplication;

import android.animation.Animator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by lukasz on 15.12.15.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;

    public DownloadImageTask(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {

    }


    @Override
    protected Bitmap doInBackground(String... url) {
        Bitmap bitmap = null;

        try {

            InputStream inputStream = new URL(url[0]).openStream();

            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {

        imageView.setImageBitmap(result);

    }
}
