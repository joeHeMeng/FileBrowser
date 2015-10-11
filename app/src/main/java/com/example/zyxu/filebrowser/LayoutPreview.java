package com.example.zyxu.filebrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class LayoutPreview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent=getIntent();
        int layoutNum=intent.getIntExtra("layout",1);
        switch (layoutNum){
            case 1:setContentView(R.layout.button_item);break;
            case 2:setContentView(R.layout.lecture_item);break;
            case 3:setContentView(R.layout.treehole_item);break;
            case 4:setContentView(R.layout.notify_item);break;
        }

    }

}
