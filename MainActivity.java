package com.example.lalamove.imageslist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public ArrayList<String> driverPicPaths;

    public static LruCache<String, Bitmap> getmMemoryCache() {
        return mMemoryCache;
    }

    public static LruCache<String, Bitmap> mMemoryCache;
    //static final File storageDir = new File(Environment.getExternalStorageDirectory() + "/Driver_Photos/");

    ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };

        driverPicPaths = new ArrayList<>();
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Driver_Photos/");
        // extract all the files
        File[] fileList = storageDir.listFiles();
        if (fileList != null) {
            for (File f: fileList) {
                String fullPath = f.getAbsolutePath();
                driverPicPaths.add(fullPath);
            }
        }

        imageAdapter = new ImageAdapter(this, driverPicPaths);

        ListView listView = (ListView) findViewById(R.id.lvList);
        if (imageAdapter != null ) {
            listView.setAdapter(imageAdapter);
        }
    }


}
