package com.alphalabs.lifeset;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

public class InitSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to make status bar transparent
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //


        View view=getLayoutInflater().inflate(R.layout.activ_linear, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
        setContentView(view);
        changeStatusBarColor();
    }

    public void add_act(View v)
    {
        // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.add_activity_popup, null);
            popupView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up_fast));
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
            popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            popupWindow.setBackgroundDrawable(null);
            popupWindow.showAsDropDown(v);
            View container = (View) popupWindow.getContentView().getParent();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.8f;
            wm.updateViewLayout(container, p);
        //

        int tag = Integer.parseInt(v.getTag().toString());
        CheckBox cb;
        if (tag == 1) {
            //Toast.makeText(this, "mon", Toast.LENGTH_SHORT).show();
            cb = popupView.findViewById(R.id.mon);
            cb.setChecked(true);
        } else if (tag == 2) {
            cb = popupView.findViewById(R.id.tue);
            cb.setChecked(true);
        } else if (tag == 3) {
            cb = popupView.findViewById(R.id.wed);
            cb.setChecked(true);
        } else if (tag == 4) {
            cb = popupView.findViewById(R.id.thu);
            cb.setChecked(true);
        } else if (tag == 5) {
            cb = popupView.findViewById(R.id.fri);
            cb.setChecked(true);
        } else if (tag == 6) {
            cb = popupView.findViewById(R.id.sat);
            cb.setChecked(true);
        }
    }

    public void pg0(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            Toast.makeText(this, "Currently Unavailable", Toast.LENGTH_LONG).show();
        }
        else
        {
            View view=getLayoutInflater().inflate(R.layout.college_details_pg1, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
            EditText et = findViewById(R.id.collegename);
            et.setText("IIIT Hyderabad");
        }
    }

    public void pg1(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            View view=getLayoutInflater().inflate(R.layout.activity_init_settings, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_down));
            setContentView(view);
        }
        else
        {
            View view=getLayoutInflater().inflate(R.layout.mess_timings_pg2, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
        }
    }

    public void pg2(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            View view=getLayoutInflater().inflate(R.layout.college_details_pg1, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_down));
            setContentView(view);
            EditText et = findViewById(R.id.collegename);
            et.setText("IIIT Hyderabad");
        }
        else
        {
            View view=getLayoutInflater().inflate(R.layout.timetable_pg3, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
            findViewById(R.id.pt_hidden).setVisibility(View.GONE);
        }
    }

    public void pg3(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            View view=getLayoutInflater().inflate(R.layout.mess_timings_pg2, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_down));
            setContentView(view);
        }
        else
        {
            View view=getLayoutInflater().inflate(R.layout.additionals_pg4, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
        }
    }


    public void pg4(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            View view=getLayoutInflater().inflate(R.layout.timetable_pg3, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_down));
            setContentView(view);
        }
        else
        {
            Toast.makeText(this, "hello2", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void swt(View v)
    {
        SwitchCompat sww = findViewById(R.id.pttf);
        if(!sww.isChecked())
            findViewById(R.id.pt_hidden).setVisibility(View.GONE);
        else
            findViewById(R.id.pt_hidden).setVisibility(View.VISIBLE);
    }

    //making transparent function
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#00000000"));
    }
}
