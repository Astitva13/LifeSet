package com.alphalabs.lifeset;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

public class InitSettingsActivity extends AppCompatActivity {

    View popupView,popupView1;
    PopupWindow popupWindow,popupWindow1;
    private boolean g1,g2,g3,g4,g5,g6;
    private String details_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to make status bar transparent
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //

        View view=getLayoutInflater().inflate(R.layout.activity_init_settings, null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
        setContentView(view);
        changeStatusBarColor();

    }

    public void timech(View v)
    {
        String tag_temp = v.getTag().toString();
        time_popup(v);
        CardView cv = popupView1.findViewById(R.id.setx);
        cv.setTag(tag_temp);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fill_in(v);
            }
        });
    }

    public void fill_in(View v)
    {
        String s = v.getTag().toString();
        EditText tw = null,ed;
        ed = popupView1.findViewById(R.id.showx);
        popupWindow1.dismiss();
        String timef = ed.getText().toString();

        if(s.equals("a1"))
            tw = findViewById(R.id.breakfast);
        else if(s.equals("a2"))
            tw = findViewById(R.id.lunch);
        else if(s.equals("a3"))
            tw = findViewById(R.id.dinner);

        tw.setText(timef);
    }

    public View infl()
    {
        EditText ed = (EditText) popupView.findViewById(R.id.detailsx);
        String details = ed.getText().toString();
        ed = (EditText) popupView.findViewById(R.id.timex);
        String time = ed.getText().toString();
        if(details.equals("") || time.equals("")) {
            Toast.makeText(this, "Not Added", Toast.LENGTH_LONG).show();
            return null;
        }
        else{
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.activ_linear, null,false);
            // Add the new row before the add field button.
            TextView tv = rowView.findViewById(R.id.timx);
            tv.setText(time);
            tv = rowView.findViewById(R.id.delx);
            tv.setText(details);
            return rowView;
        }
    }

    public void addx(View v)
    {

        try {
            CheckBox ck = popupView.findViewById(R.id.mon);
            if(infl()!=null) {
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.mon_f);
                    placeHolder.addView(infl());
                }
                ck = popupView.findViewById(R.id.tue);
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.tue_f);
                    placeHolder.addView(infl());
                }
                ck = popupView.findViewById(R.id.wed);
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.wed_f);
                    placeHolder.addView(infl());
                }
                ck = popupView.findViewById(R.id.thu);
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.thu_f);
                    placeHolder.addView(infl());
                }
                ck = popupView.findViewById(R.id.fri);
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.fri_f);
                    placeHolder.addView(infl());
                }
                ck = popupView.findViewById(R.id.sat);
                if (ck.isChecked()) {
                    LinearLayout placeHolder = (LinearLayout) findViewById(R.id.sat_f);
                    placeHolder.addView(infl());
                }
            }
            popupWindow.dismiss();
        }
        catch (Exception ex) {
            Toast.makeText(this, ""+ex, Toast.LENGTH_LONG).show();
        }

    }

    public void add_act(View v)
    {
        // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            popupView = inflater.inflate(R.layout.add_activity_popup, null,false);
            popupView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up_fast));
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            popupWindow = new PopupWindow(popupView, width, height, focusable);
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
        EditText ed = popupView.findViewById(R.id.timex);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    set_time(view);
                }
            }
        });
    }

    public void time_popup(View v)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView1 = inflater.inflate(R.layout.time_popup, null);
        popupView1.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up_fast));
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow1 = new PopupWindow(popupView1, width, height, focusable);
        popupWindow1.showAtLocation(v, Gravity.CENTER, 0, 0);
        popupWindow1.setBackgroundDrawable(null);
        popupWindow1.showAsDropDown(v);
        popupView1.findViewById(R.id.endtime).setVisibility(View.GONE);
        popupView1.findViewById(R.id.check_final).setVisibility(View.GONE);
        popupView1.findViewById(R.id.starttime).setVisibility(View.VISIBLE);
        View container = (View) popupWindow1.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.8f;
        wm.updateViewLayout(container, p);
    }

    public void set_time(View v)
    {
        EditText ed = popupView.findViewById(R.id.detailsx);
        details_back =ed.getText().toString();
        CheckBox cb;
        cb = popupView.findViewById(R.id.mon);
        g1 = cb.isChecked();
        cb = popupView.findViewById(R.id.tue);
        g2 = cb.isChecked();
        cb = popupView.findViewById(R.id.wed);
        g3 = cb.isChecked();
        cb = popupView.findViewById(R.id.thu);
        g4 = cb.isChecked();
        cb = popupView.findViewById(R.id.fri);
        g5 = cb.isChecked();
        cb = popupView.findViewById(R.id.sat);
        g6 = cb.isChecked();
        popupWindow.dismiss();
        time_popup(v);
        CardView cv = popupView1.findViewById(R.id.setx);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donex(v);
            }
        });
    }

    public void setend(View v)
    {
        popupView1.findViewById(R.id.starttime).setVisibility(View.GONE);
        popupView1.findViewById(R.id.check_final).setVisibility(View.GONE);
        popupView1.findViewById(R.id.endtime).setVisibility(View.VISIBLE);
    }

    public void setstart(View v)
    {
        popupView1.findViewById(R.id.starttime).setVisibility(View.VISIBLE);
        popupView1.findViewById(R.id.check_final).setVisibility(View.GONE);
        popupView1.findViewById(R.id.endtime).setVisibility(View.GONE);
    }

    public void finalx(View v)
    {
        EditText ed = popupView1.findViewById(R.id.showx);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setstart(v);
            }
        });
        String f1="am",f2="am";
        TimePicker t1 = popupView1.findViewById(R.id.t1);
        int h1 = t1.getHour(),m1 = t1.getMinute();
        TimePicker t2 = popupView1.findViewById(R.id.t2);
        int h2 = t2.getHour(),m2 = t2.getMinute();
        if(h1>12) {f1="pm";h1%=12;}
        else if(h1==12 && m1!=0) f1="pm";
        if(h2>12) {f2="pm";h2%=12;}
        else if(h2==12 && m2!=0) f2="pm";
        ed.setText(h1+"."+m1+f1+" to "+h2+"."+m2+f2);
        popupView1.findViewById(R.id.starttime).setVisibility(View.GONE);
        popupView1.findViewById(R.id.check_final).setVisibility(View.VISIBLE);
        popupView1.findViewById(R.id.endtime).setVisibility(View.GONE);
    }

    public void donex(View v)
    {

        EditText ed = popupView1.findViewById(R.id.showx);
        String timef = ed.getText().toString();
        popupWindow1.dismiss();
        CheckBox cb;
        add_act(v);
        cb = popupView.findViewById(R.id.mon);cb.setChecked(g1);
        cb = popupView.findViewById(R.id.tue);cb.setChecked(g2);
        cb = popupView.findViewById(R.id.wed);cb.setChecked(g3);
        cb = popupView.findViewById(R.id.thu);cb.setChecked(g4);
        cb = popupView.findViewById(R.id.fri);cb.setChecked(g5);
        cb = popupView.findViewById(R.id.sat);cb.setChecked(g6);
        EditText tw = popupView.findViewById(R.id.timex);
        tw.setText(timef);
        EditText ed1 = popupView.findViewById(R.id.detailsx);
        ed1.setText(details_back);
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
            EditText ed1 = findViewById(R.id.collegeyr_text);
            ed1.setVisibility(View.GONE);
            ed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed1_temp = findViewById(R.id.collegeyr);
                    if(hasFocus) ed1_temp.requestFocus();
                }
            });
            ed1 = findViewById(R.id.collegeyr);
            ed1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        EditText ed1_temp = findViewById(R.id.collegeyr), ed2 = findViewById(R.id.collegeyr_text);
                        ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        ed2.setVisibility(View.VISIBLE);
                        if(ed1_temp.getText().toString().equals("")) {
                            ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ed2.setVisibility(View.GONE);
                        }
                        else{
                            ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ed2.setVisibility(View.VISIBLE);
                            int yr = Integer.parseInt(ed1_temp.getText().toString());
                            String s = "";
                            if (yr % 10 == 1) s = "st year";
                            else if (yr % 10 == 2) s = "nd year";
                            else if (yr % 10 == 3) s = "rd year";
                            else s = "th year";
                            ed2.setText(s);
                        }
                    }catch (Exception ex) {}
                    return false;
                }
            });
            ed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed1_temp = findViewById(R.id.collegeyr);
                    if(ed1_temp!=null && !hasFocus && ed1_temp.getText().toString().equals(""))
                    {
                        ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                }
            });
        }
    }

    public void pg1(View v)
    {
        try {
            if (v.getTag().toString().equals("1")) {
                View view = getLayoutInflater().inflate(R.layout.activity_init_settings, null, false);
                view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_down));
                setContentView(view);
            } else {
                View view = getLayoutInflater().inflate(R.layout.mess_timings_pg2, null, false);
                view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_up));
                setContentView(view);
                EditText ed = view.findViewById(R.id.breakfast);
                ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) timech(v);
                    }
                });
                ed = view.findViewById(R.id.lunch);
                ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) timech(v);
                    }
                });
                ed = view.findViewById(R.id.dinner);
                ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View v, boolean hasFocus) {
                        if (hasFocus) timech(v);
                    }
                });
            }
        }
        catch (Exception ex) {
            Toast.makeText(this, "" + ex, Toast.LENGTH_SHORT).show();
            ex.printStackTrace();
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
            et.setText("IIIT Hyderabad");EditText ed1 = findViewById(R.id.collegeyr_text);
            ed1.setVisibility(View.GONE);
            ed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed1_temp = findViewById(R.id.collegeyr);
                    if(hasFocus) ed1_temp.requestFocus();
                }
            });
            ed1 = findViewById(R.id.collegeyr);
            ed1.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    try {
                        EditText ed1_temp = findViewById(R.id.collegeyr), ed2 = findViewById(R.id.collegeyr_text);
                        ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
                        ed2.setVisibility(View.VISIBLE);
                        if(ed1_temp.getText().toString().equals("")) {
                            ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ed2.setVisibility(View.GONE);
                        }
                        else{
                            ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                            ed2.setVisibility(View.VISIBLE);
                            int yr = Integer.parseInt(ed1_temp.getText().toString());
                            String s = "";
                            if (yr % 10 == 1) s = "st year";
                            else if (yr % 10 == 2) s = "nd year";
                            else if (yr % 10 == 3) s = "rd year";
                            else s = "th year";
                            ed2.setText(s);
                        }
                    }catch (Exception ex) {}
                    return false;
                }
            });
            ed1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    EditText ed1_temp = findViewById(R.id.collegeyr);
                    if(ed1_temp!=null && !hasFocus && ed1_temp.getText().toString().equals(""))
                    {
                        ed1_temp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    }
                }
            });
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
            EditText ed = view.findViewById(R.id.breakfast);
            ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    timech(v);
                }
            });
            ed = view.findViewById(R.id.lunch);
            ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    timech(v);
                }
            });
            ed = view.findViewById(R.id.dinner);
            ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    timech(v);
                }
            });
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
