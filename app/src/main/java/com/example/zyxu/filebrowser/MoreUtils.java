package com.example.zyxu.filebrowser;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.*;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;


public class MoreUtils extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_utils);



    //strictmode
        if (android.os.Build.VERSION.SDK_INT > 9) {
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
}


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private Snackbar snackbar;
            private boolean snackbarIsPop = false;

            @Override
            public void onClick(View view) {
                snackbar = Snackbar.make(view, "徐子扬 1400012978", Snackbar.LENGTH_SHORT);
                if (snackbarIsPop) {
                    snackbarIsPop = false;
                } else {
                    snackbarIsPop = true;
                    snackbar.show();
                }

            }
        });

        Button btnGetDelta=(Button)findViewById(R.id.btnDelta);
        btnGetDelta.setVisibility(View.GONE);
        Button btnUrlToFile= (Button) findViewById(R.id.btnU2F);
        btnUrlToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //默认值为PKU Helper的apk和sdcard根目录内pkuhelper.apk
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/110161.htm";
                Log.d("urlpath", path);
                File file = new File(path);

                String url = "http://www.linuxidc.com/Linux/2014-12/110161.htm";


                if (urlToFile(url, file)) {
                    Log.d("urlTofile", "done");
                    Snackbar.make(v, "DONE", Snackbar.LENGTH_SHORT).show();
                } else {
                    Log.d("urlTofile", "error");
                    Snackbar.make(v, "ERROR", Snackbar.LENGTH_SHORT).show();
                }

            }
        });

        Button btnImageDownload= (Button) findViewById(R.id.btnImg);

        btnImageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://cdn.duitang.com/uploads/item/201508/28/20150828153351_GjauA.thumb.700_0.jpeg";
                new ImageDownload(MoreUtils.this,url,(ImageView)findViewById(R.id.imageView));
            }
        });

        Button btnPreview=(Button)findViewById(R.id.btnPreview1);
        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MoreUtils.this,LayoutPreview.class);
                intent.putExtra("layout",1);
                startActivity(intent);
            }
        });

        Button btnPreview2=(Button)findViewById(R.id.btnPreview2);
        btnPreview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MoreUtils.this,LayoutPreview.class);
                intent.putExtra("layout",2);
                startActivity(intent);
            }
        });

        Button btnPreview3=(Button)findViewById(R.id.btnPreview3);
        btnPreview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MoreUtils.this,LayoutPreview.class);
                intent.putExtra("layout",3);
                startActivity(intent);
            }
        });

        Button btnPreview4=(Button)findViewById(R.id.btnPreview4);
        btnPreview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MoreUtils.this,LayoutPreview.class);
                intent.putExtra("layout",4);
                startActivity(intent);
            }
        });
    }

    int getDeltaWeek(Calendar t1, Calendar t2){

        return 0;
    }

    boolean urlToFile(String url, java.io.File file){

        URL Url = null;
        HttpURLConnection conn = null;
        try {

            Url=new URL(url);
            conn = (HttpURLConnection) Url.openConnection();
            conn.setRequestMethod("GET");
            // 超时响应时间为5秒
            conn.setConnectTimeout(5 * 1000);
            // 通过输入流获取文件数据
            InputStream inStream = conn.getInputStream();
            // 得到资源的二进制数据，以二进制封装得到数据，具有通用性
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            // 创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            // 每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            // 使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }

            // 把outStream里的数据写入data
            byte[] data = outStream.toByteArray();

            // 创建输出流
            FileOutputStream FileOutStream = new FileOutputStream(file);
            // 写入数据
            FileOutStream.write(data);
            inStream.close();
            outStream.close();
            FileOutStream.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    class ImageDownload{
        Handler handler;
        Bitmap bitmap=null;
        public ImageDownload(final Activity activity, String url, final ImageView imageView){

            handler=new Handler( new Handler.Callback() {

                @Override
                public boolean handleMessage(Message msg) {


                    if (msg.what==1){
                        imageView.setImageBitmap(bitmap);
                        Snackbar.make(imageView,"DONE",Snackbar.LENGTH_SHORT).show();
                        return true;
                    }

                    Snackbar.make(imageView,"ERROR",Snackbar.LENGTH_SHORT).show();
                    return false;

                }

            });


            URL myFileUrl = null;
            try {
                myFileUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setConnectTimeout(0);
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            Message msg=new Message();
            msg.what=1;

            handler.sendMessage(msg);
        }
    }


}
