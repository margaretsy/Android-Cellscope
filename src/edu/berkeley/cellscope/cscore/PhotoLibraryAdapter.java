package edu.berkeley.cellscope.cscore;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PhotoLibraryAdapter extends BaseAdapter {
	protected Activity activity;
	protected ArrayList<File> files;
	protected ArrayList<String> titles;
	protected BitmapCache cache;
	
	protected static LayoutInflater inflater;
 
    public PhotoLibraryAdapter(Activity a, ArrayList<File> fileList, ArrayList<String> fileNames, BitmapCache imageCache) {
        activity = a;
        files = fileList;
        titles = fileNames;
        cache = imageCache;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
 
    public int getCount() {
        return files.size();
    }
 
    public Object getItem(int position) {
        return position;
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.path); // title
        ImageView thumbnail =(ImageView)vi.findViewById(R.id.list_image); // thumb image
        title.setText(titles.get(position));
        int width = (int) vi.getResources().getDimension(R.dimen.thumbnail_width);
        int height = (int) vi.getResources().getDimension(R.dimen.thumbnail_height);
        ImageLoader.loadPhotoThumbnail(thumbnail, files, position, width, height, cache);
        //thumbnail.setImageBitmap(ImageLoader.loadThumbnailImage(files.get(position).getPath(), width, height));
        //Uri imageUri = Uri.fromFile(files.get(position));
       // thumbnail.setImageURI(imageUri);
        return vi;
    }

}