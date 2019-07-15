package com.alphalabs.lifeset;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*pregen code*/super.onCreate(savedInstanceState);

        //to make status bar transparent
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //
        changeStatusBarColor();
        setContentView(R.layout.activity_main);
        DbConnect db = new DbConnect(this);
        ((TextView)findViewById(R.id.txtname)).setText(db.getUname());
        ((TextView)findViewById(R.id.txtdte)).setText(new SimpleDateFormat("EEEE, dd MMM  `yy").format(new Date()));

    }
    //making transparent function
    private void changeStatusBarColor() {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#48000000"));
    }
}