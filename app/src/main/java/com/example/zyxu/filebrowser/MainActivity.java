package com.example.zyxu.filebrowser;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;
import java.io.File;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            private Snackbar snackbar;
            private boolean snackbarIsPop=false;
            @Override
            public void onClick(View view) {
                snackbar=Snackbar.make(view, "徐子扬 1400012978", Snackbar.LENGTH_SHORT);
                if (snackbarIsPop) {
                    snackbarIsPop=false;
                }
                else{
                    snackbarIsPop=true;
                    snackbar.show();
                }

            }
        });

        Button buttonBrowser = (Button) findViewById(R.id.btnBrowser);
        buttonBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取
                String SDPath= Environment.getExternalStorageDirectory().getAbsolutePath();
                Intent i = new Intent(MainActivity.this, FileBrowser.class);
                i.putExtra("path",SDPath);
                startActivity(i);
            }
        });

        Button buttonJump= (Button) findViewById(R.id.btnJump);
        buttonJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText editText=(EditText)findViewById(R.id.editText);

                String path = editText.getText().toString();
                File f=new File(path);
                if (f.exists()) {
                    Snackbar.make(v,"转入指定文件夹",Snackbar.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, FileBrowser.class);
                    i.putExtra("path", path);
                    startActivity(i);
                }
                else{
                    Snackbar.make(v, "Wrong Path", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Button btnMore= (Button) findViewById(R.id.btnMORE);
        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MoreUtils.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
