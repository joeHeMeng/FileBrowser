package com.example.zyxu.filebrowser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FileBrowser extends AppCompatActivity {

    private List<File> fileList= new ArrayList<>();
    private String pathNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_browser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent=getIntent();
        pathNow=intent.getStringExtra("path");
        toolbar.setSubtitle(pathNow);
        //独立小按钮功能
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Toast.makeText(FileBrowser.this,"添加文件夹（未实现）",Toast.LENGTH_SHORT).show();
            }
        });



        //扫描SD卡并准备好fileList数组
        initFiles();
        ListView listView=(ListView)findViewById(R.id.listViewFiles);
        listView.setAdapter(new FileAdapter(FileBrowser.this,R.layout.file_item,fileList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                File file = fileList.get(position);
                java.io.File jf = new java.io.File(file.getFilePath());
                if (jf.isDirectory()) {
                    Toast.makeText(FileBrowser.this, "This is a directory", Toast.LENGTH_SHORT).show();
                    Intent intentNew = new Intent(FileBrowser.this, FileBrowser.class);
                    intentNew.putExtra("path", file.getFilePath());
                    startActivity(intentNew);
                } else if (jf.isFile()) {
                    Toast.makeText(FileBrowser.this, "This is a file", Toast.LENGTH_SHORT).show();
                    Intent intentOpen = setOpenIntent(file.getFilePath());
                    startActivity(intentOpen);
                }
            }
        });
    }

//读取当前文件夹
    private void initFiles(){
        Log.d("path", pathNow);
        java.io.File path= new java.io.File(pathNow);
        //Log.d("pathFile", path.list().length);

        if (path.list()!=null)
        {
            for (java.io.File file:path.listFiles())
            {
                String fileName=file.getName();
                String filePath=file.getAbsolutePath();
                int imageId=0;
                if (file.isFile()) {
                    imageId = R.drawable.mimetypes_96;
                }
                else if (file.isDirectory()) {
                    imageId=R.drawable.places_orange_96;
                }

                File f=new File(fileName, imageId, filePath);
                fileList.add(f);
            }
        }

    }

    public static Intent setOpenIntent(String filePath){

        java.io.File file = new java.io.File(filePath);
        if(!file.exists()) return null;
        //取得后缀名，并转为小写
        String end=file.getName().substring(file.getName().lastIndexOf(".") + 1,file.getName().length()).toLowerCase();
        //根据后缀名设置intent
        if(end.equals("mp3")||end.equals("mid")||end.equals("ogg")||end.equals("wav")){
            return getAudioFileIntent(filePath);
        }else if(end.equals("mp4")){
            return getVideoFileIntent(filePath);
        }else if(end.equals("jpg")||end.equals("gif")||end.equals("png")||
                end.equals("jpeg")||end.equals("bmp")){
            return getImageFileIntent(filePath);
        }else if(end.equals("apk")){
            return getApkFileIntent(filePath);
        }else if(end.equals("txt")) {
            return getTextFileIntent(filePath);
        }else if(end.equals("html")||end.equals("htm")) {
            return getHtmlFileIntent(filePath);
        }else if(end.equals("pdf")){
            return getPdfFileIntent(filePath);
        }else{
            return getAllIntent(filePath);
        }
    }

    //打开奇怪的文件
    public static Intent getAllIntent(String param) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri,"*/*");
        return intent;
    }
    //APK
    public static Intent getApkFileIntent(String param ) {

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri,"application/vnd.android.package-archive");
        return intent;
    }

    //Video
    public static Intent getVideoFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    //Audio
    public static Intent getAudioFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    //HTML
    public static Intent getHtmlFileIntent( String param ){

        Uri uri = Uri.parse(param ).buildUpon().encodedAuthority("com.android.htmlfileprovider").scheme("content").encodedPath(param ).build();
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setDataAndType(uri, "text/html");
        return intent;
    }

    //Image
    public static Intent getImageFileIntent( String param ) {

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }


    //TXT
    public static Intent getTextFileIntent( String param){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri2 = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri2, "text/plain");
        return intent;
    }
    //PDF
    public static Intent getPdfFileIntent( String param ){

        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new java.io.File(param ));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }
}
