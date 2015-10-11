package com.example.zyxu.filebrowser;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by zyxu on 15/10/9.
 */
public class FileAdapter extends ArrayAdapter<File> {
    private int resourceId;

    public FileAdapter(Context context, int textViewResourceId, List<File> objects)
    {
        super(context,textViewResourceId,objects);

        resourceId=textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        File file=getItem(position);
        View view;
        ViewHolder viewHolder;
        Log.d("cV",String.valueOf(convertView == null));
        if (convertView==null) {

            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder=new ViewHolder();
            viewHolder.fileImage= (ImageView) view.findViewById(R.id.file_image);
            viewHolder.fileName=(TextView) view.findViewById(R.id.file_name);
            view.setTag(viewHolder);
            //Log.d("v",view.toString());
            //Log.d("rID", String.valueOf(resourceId));
            //Log.d("fileImage",viewHolder.fileImage.toString());

       }
        else {
            view=convertView;
            viewHolder= (ViewHolder) view.getTag();
        }

        //Log.d("viewImageId", String.valueOf(file.getImageId()));
        viewHolder.fileImage.setImageResource(file.getImageId());
        viewHolder.fileName.setText(file.getName());

        return view;
    }

    class ViewHolder{
        ImageView fileImage;
        TextView fileName;
    }

}
