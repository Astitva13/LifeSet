package com.alphalabs.lifeset;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.SwitchCompat;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.StringTokenizer;

public class InitSettingsActivity extends AppCompatActivity {

    View addActivity_popupView, setTime_popupView, editOptions_popupView;
    PopupWindow addActivity_popupWindow, setTime_popupWindow, editOptions_popupWindow;
    private boolean g1,g2,g3,g4,g5,g6;
    private String details_back;

    private String username,pt_string;
    private int clgYear=0, dailySportsHrs=0, relnStatus=0;
    private float currCG=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to make status bar transparent
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //
        setContentView(R.layout.details_user);
        changeStatusBarColor();

    }

    //Back buton impl. on pg0
    public void back_btnx(View v)
    {
        View view=getLayoutInflater().inflate(R.layout.details_user , null, false);
        view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
        setContentView(view);
    }

    //Chosing time on pg2
    public void timech(View v)
    {
        String tag_temp = v.getTag().toString();
        setTime_popup(v);
        CardView cv = setTime_popupView.findViewById(R.id.setx);
        cv.setTag(tag_temp);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fill_in(v);
            }
        });
    }

    //filling in time in TextView(s) on pg2
    public void fill_in(View v)
    {
        String s = v.getTag().toString();
        EditText tw,ed;
        ed = setTime_popupView.findViewById(R.id.showx);
        setTime_popupWindow.dismiss();
        String timef = ed.getText().toString();
        if (s.equals("a1"))
            tw = findViewById(R.id.breakfast);
        else if (s.equals("a2"))
            tw = findViewById(R.id.lunch);
        else
            tw = findViewById(R.id.dinner);

        tw.setText(timef);
    }

    //Function to check if a time String is valid or not
    public boolean check_time(String time)
    {
        StringTokenizer stk = new StringTokenizer(time, " to"),stk_1,stk_2;
        String time_1 = stk.nextToken(),time1,dur1;
        time1 = time_1.substring(0,time_1.length()-2);
        dur1 = time_1.substring(time_1.length()-2);
        String time_2 = stk.nextToken(),time2,dur2;
        time2 = time_2.substring(0,time_2.length()-2);
        dur2 = time_2.substring(time_2.length()-2);
        stk_1 = new StringTokenizer(time1,".");
        stk_2 = new StringTokenizer(time2,".");
        int ans =Integer.parseInt(stk_1.nextToken())*60+Integer.parseInt(stk_1.nextToken())-Integer.parseInt(stk_2.nextToken())*60-Integer.parseInt(stk_2.nextToken());
        if(dur1.equals("pm")) ans+=12*60;
        if(dur2.equals("pm")) ans-=12*60;
        return ans > 0;
    }

    //Inflates the detailsPane layout
    public View inflate_detailsPane()
    {
        EditText ed = (EditText) addActivity_popupView.findViewById(R.id.detailsx);
        String details = ed.getText().toString();
        ed = (EditText) addActivity_popupView.findViewById(R.id.timex);
        String time = ed.getText().toString();
        if(details.equals("") || time.equals("")) {
            Toast.makeText(this, "Not Added", Toast.LENGTH_LONG).show();
            return null;
        }
        else if(check_time(time))
        {
            Toast.makeText(this, "Time duration should be positive man !", Toast.LENGTH_LONG).show();
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

    //Sets onLongClick action of detailsPane
    void onLongClick_detailsPane(View v)
    {
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Toast.makeText(InitSettingsActivity.this, "Long_Click", Toast.LENGTH_SHORT).show();
                editView_popup(v);
                return true;
            }
        });
    }

    //Resources used by editView_popup and long_press_menu_options methods.
        View options_layout;
        LinearLayout option_layout_parent;
    //

    //Adds the editView layout
    public void editView_popup(View v)
    {
        options_layout = v;
        option_layout_parent = (LinearLayout) v.getParent();

            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            editOptions_popupView = inflater.inflate(R.layout.edit_view_popup, null,false);
            editOptions_popupView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up_fast));
            int width = LinearLayout.LayoutParams.MATCH_PARENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            editOptions_popupWindow = new PopupWindow(editOptions_popupView, width, height, focusable);
            editOptions_popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
            editOptions_popupWindow.setBackgroundDrawable(null);
            editOptions_popupWindow.showAsDropDown(v);
            View container = (View) editOptions_popupWindow.getContentView().getParent();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
            p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            p.dimAmount = 0.8f;
            wm.updateViewLayout(container, p);
            //
    }

    public void long_press_menu_options(View v)
    {
        if(v.getTag().toString().equals("1"))
        {
            ((ViewGroup)options_layout.getParent()).removeView(options_layout);
            editOptions_popupWindow.dismiss();
            addActivity_popup(v);
            String dayname = ((TextView)option_layout_parent.findViewWithTag("dayname")).getText().toString().substring(0,3).toLowerCase();
            int idx = getResources().getIdentifier(dayname, "id", "com.alphalabs.lifeset");
            String det_temp = ((TextView)options_layout.findViewById(R.id.delx)).getText().toString();
            String time_temp = ((TextView)options_layout.findViewById(R.id.timx)).getText().toString();
            ((CheckBox)addActivity_popupView.findViewById(R.id.mon)).setChecked(false);
            ((CheckBox)addActivity_popupView.findViewById(idx)).setChecked(true);
            addActivity_popupView.findViewById(R.id.checkbox_pane).setVisibility(View.GONE);
            ((EditText)addActivity_popupView.findViewById(R.id.detailsx)).setText(det_temp);
            ((EditText)addActivity_popupView.findViewById(R.id.timex)).setText(time_temp);
        }
        else
        {
            ((ViewGroup)options_layout.getParent()).removeView(options_layout);
            editOptions_popupWindow.dismiss();
        }
    }

    //Adds the detailsPane layout
    public void add_detailsPane(View v)
    {
        CheckBox ck = addActivity_popupView.findViewById(R.id.mon);
        View v_temp = inflate_detailsPane();
        if(v_temp!=null) {
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.mon_f);
                placeHolder.addView(v_temp);
            }
            ck = addActivity_popupView.findViewById(R.id.tue);
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.tue_f);
                placeHolder.addView(v_temp);
            }
            ck = addActivity_popupView.findViewById(R.id.wed);
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.wed_f);
                placeHolder.addView(v_temp);
            }
            ck = addActivity_popupView.findViewById(R.id.thu);
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.thu_f);
                placeHolder.addView(v_temp);
            }
            ck = addActivity_popupView.findViewById(R.id.fri);
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.fri_f);
                placeHolder.addView(v_temp);
            }
            ck = addActivity_popupView.findViewById(R.id.sat);
            if (ck.isChecked()) {
                v_temp = inflate_detailsPane();
                onLongClick_detailsPane(v_temp);
                LinearLayout placeHolder = (LinearLayout) findViewById(R.id.sat_f);
                placeHolder.addView(v_temp);
            }

        }
        addActivity_popupWindow.dismiss();
    }

    //Shows add_activity popup
    public void addActivity_popup(View v)
    {
        // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            addActivity_popupView = inflater.inflate(R.layout.add_activity_popup, null,false);
            addActivity_popupView.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up_fast));
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            addActivity_popupWindow = new PopupWindow(addActivity_popupView, width, height, focusable);
            addActivity_popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            addActivity_popupWindow.setBackgroundDrawable(null);
            addActivity_popupWindow.showAsDropDown(v);
            View container = (View) addActivity_popupWindow.getContentView().getParent();
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
            cb = addActivity_popupView.findViewById(R.id.mon);
            cb.setChecked(true);
        } else if (tag == 2) {
            cb = addActivity_popupView.findViewById(R.id.tue);
            cb.setChecked(true);
        } else if (tag == 3) {
            cb = addActivity_popupView.findViewById(R.id.wed);
            cb.setChecked(true);
        } else if (tag == 4) {
            cb = addActivity_popupView.findViewById(R.id.thu);
            cb.setChecked(true);
        } else if (tag == 5) {
            cb = addActivity_popupView.findViewById(R.id.fri);
            cb.setChecked(true);
        } else if (tag == 6) {
            cb = addActivity_popupView.findViewById(R.id.sat);
            cb.setChecked(true);
        }
        EditText ed = addActivity_popupView.findViewById(R.id.timex);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    saveState_activity(view);
                }
            }
        });
    }

    //Shows saveState_activity popup
    public void setTime_popup(View v)
    {
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        setTime_popupView = inflater.inflate(R.layout.time_popup, null);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        setTime_popupWindow = new PopupWindow(setTime_popupView, width, height, focusable);
        setTime_popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
        setTime_popupWindow.setBackgroundDrawable(null);
        setTime_popupWindow.showAsDropDown(v);
        setTime_popupView.findViewById(R.id.endtime).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.check_final).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.starttime).setVisibility(View.VISIBLE);
        View container = (View) setTime_popupWindow.getContentView().getParent();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.8f;
        wm.updateViewLayout(container, p);
    }

    //Saves the state of add_activity popup and displays set_time popup
    public void saveState_activity(View v)
    {
        EditText ed = addActivity_popupView.findViewById(R.id.detailsx);
        details_back =ed.getText().toString();
        CheckBox cb;
        cb = addActivity_popupView.findViewById(R.id.mon);
        g1 = cb.isChecked();
        cb = addActivity_popupView.findViewById(R.id.tue);
        g2 = cb.isChecked();
        cb = addActivity_popupView.findViewById(R.id.wed);
        g3 = cb.isChecked();
        cb = addActivity_popupView.findViewById(R.id.thu);
        g4 = cb.isChecked();
        cb = addActivity_popupView.findViewById(R.id.fri);
        g5 = cb.isChecked();
        cb = addActivity_popupView.findViewById(R.id.sat);
        g6 = cb.isChecked();
        addActivity_popupWindow.dismiss();
        setTime_popup(v);
        CardView cv = setTime_popupView.findViewById(R.id.setx);
        cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donex(v);
            }
        });
    }

    //Changes the view of set_time popup to "End time"
    public void setend(View v)
    {
        setTime_popupView.findViewById(R.id.starttime).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.check_final).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.endtime).setVisibility(View.VISIBLE);
    }

    //Changes the view of set_time popup to "Start time"
    public void setstart(View v)
    {
        setTime_popupView.findViewById(R.id.starttime).setVisibility(View.VISIBLE);
        setTime_popupView.findViewById(R.id.check_final).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.endtime).setVisibility(View.GONE);
    }

    //Changes the view of set_time popup to "Final page"
    public void setfinal(View v)
    {
        EditText ed = setTime_popupView.findViewById(R.id.showx);
        ed.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                setstart(v);
            }
        });
        String f1="am",f2="am";
        TimePicker t1 = setTime_popupView.findViewById(R.id.t1);
        int h1 = t1.getHour(),m1 = t1.getMinute();
        TimePicker t2 = setTime_popupView.findViewById(R.id.t2);
        int h2 = t2.getHour(),m2 = t2.getMinute();
        if(h1>12) {f1="pm";h1%=12;}
        else if(h1==12 && m1!=0) f1="pm";
        if(h2>12) {f2="pm";h2%=12;}
        else if(h2==12 && m2!=0) f2="pm";
        ed.setText(h1+"."+m1+f1+" to "+h2+"."+m2+f2);
        setTime_popupView.findViewById(R.id.starttime).setVisibility(View.GONE);
        setTime_popupView.findViewById(R.id.check_final).setVisibility(View.VISIBLE);
        setTime_popupView.findViewById(R.id.endtime).setVisibility(View.GONE);
    }

    //Dismisses the time_popup, restarts the add_activity popup and puts the saved states back
    public void donex(View v)
    {

        EditText ed = setTime_popupView.findViewById(R.id.showx);
        String timef = ed.getText().toString();
        setTime_popupWindow.dismiss();
        CheckBox cb;
        addActivity_popup(v);
        cb = addActivity_popupView.findViewById(R.id.mon);cb.setChecked(g1);
        cb = addActivity_popupView.findViewById(R.id.tue);cb.setChecked(g2);
        cb = addActivity_popupView.findViewById(R.id.wed);cb.setChecked(g3);
        cb = addActivity_popupView.findViewById(R.id.thu);cb.setChecked(g4);
        cb = addActivity_popupView.findViewById(R.id.fri);cb.setChecked(g5);
        cb = addActivity_popupView.findViewById(R.id.sat);cb.setChecked(g6);
        EditText tw = addActivity_popupView.findViewById(R.id.timex);
        tw.setText(timef);
        EditText ed1 = addActivity_popupView.findViewById(R.id.detailsx);
        ed1.setText(details_back);
    }

    //Checks if username is valid in init_page (set_name)
    public void check_name(View v)
    {
        if(!((EditText)findViewById(R.id.username)).getText().toString().equals(""))
        {
            username = ((EditText)findViewById(R.id.username)).getText().toString();
            View view=getLayoutInflater().inflate(R.layout.activity_init_settings, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
        }
        else
        {
            Toast.makeText(this, "Your name is \'NULL\' , huh really ?", Toast.LENGTH_SHORT).show();
        }
    }


    //Button functions for pg0(Student type) - School(1) and College(2)
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
                        if (yr % 10 == 1) s = "st clgYear";
                        else if (yr % 10 == 2) s = "nd clgYear";
                        else if (yr % 10 == 3) s = "rd clgYear";
                        else s = "th clgYear";
                        ed2.setText(s);
                    }
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

    //Button functions for pg1(College details) - Back(1) and Next(2)
    public void pg1(View v)
    {
        if (v.getTag().toString().equals("1")) {
            View view = getLayoutInflater().inflate(R.layout.activity_init_settings, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in_down));
            setContentView(view);
        } else {
            clgYear = Integer.parseInt(((EditText)findViewById(R.id.collegeyr)).getText().toString());
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

    //Button functions for pg2(Mess Timings) - Back(1) and Next(2)
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
                        if (yr % 10 == 1) s = "st clgYear";
                        else if (yr % 10 == 2) s = "nd clgYear";
                        else if (yr % 10 == 3) s = "rd clgYear";
                        else s = "th clgYear";
                        ed2.setText(s);
                    }
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

    //Button functions for pg3(Time table) - Back(1) and Next(2)
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
            pt_string = "";
            if(((SwitchCompat)findViewById(R.id.pttf)).isChecked()) pt_string+="y";
            else pt_string+="n";
            int id1 = ((RadioGroup)findViewById(R.id.pttime)).getCheckedRadioButtonId();
            if(id1==R.id.morn) pt_string+="m";
            else pt_string+="e";
            int id2 = ((RadioGroup)findViewById(R.id.ptday)).getCheckedRadioButtonId();
            if(id2==R.id.mwf) pt_string+="m";
            else if(id2==R.id.tts) pt_string+="t";
            else pt_string+="d";
            add_entry_days(this);
            View view=getLayoutInflater().inflate(R.layout.additionals_pg4, null, false);
            view.startAnimation(AnimationUtils.loadAnimation(this,R.anim.slide_in_up));
            setContentView(view);
        }
    }

    //Button functions for pg4(Additionals) - Back(1) and Next(2)
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
            try{
            currCG = Float.parseFloat(((EditText)findViewById(R.id.curr_cg)).getText().toString());
            dailySportsHrs = Integer.parseInt(((EditText)findViewById(R.id.sports_hours)).getText().toString());}catch (Exception ex) {}
            int id1 = ((RadioGroup)findViewById(R.id.reln_rgroup)).getCheckedRadioButtonId();
            if(id1==R.id.single) relnStatus=1;
            else relnStatus=2;
            DbConnect db = new DbConnect(this);
            db.truncate_names();
            db.addName(username,clgYear,pt_string,currCG,dailySportsHrs,relnStatus);
            startActivity(new Intent(InitSettingsActivity.this, MainActivity.class));
        }
    }

    //PT switch impl. on pg3
    public void swt(View v)
    {
        SwitchCompat sww = findViewById(R.id.pttf);
        if(!sww.isChecked())
            findViewById(R.id.pt_hidden).setVisibility(View.GONE);
        else
            findViewById(R.id.pt_hidden).setVisibility(View.VISIBLE);
    }

    //Method implementing sequential addition of description gathered in pg3 into Database
    void addDescr(View view_day_layout, int dayid, DbConnect db)
    {
        db.truncate_day(dayid);
        LinearLayout day_layout = (LinearLayout) view_day_layout;
        for(int i=1; i<day_layout.getChildCount();i++)
        {
            LinearLayout la = (LinearLayout)(day_layout).getChildAt(i);
            String details = ((TextView)la.findViewById(R.id.delx)).getText().toString();
            String time_dur = ((TextView)la.findViewById(R.id.timx)).getText().toString();
            db.addActDay(dayid,details,time_dur);
        }
    }

    //Calling of the above method (addDesc) for each day of week
    void add_entry_days(Context context)
    {
        DbConnect db = new DbConnect(context);
        addDescr(findViewById(R.id.mon_f),1,db);
        addDescr(findViewById(R.id.tue_f),2,db);
        addDescr(findViewById(R.id.wed_f),3,db);
        addDescr(findViewById(R.id.thu_f),4,db);
        addDescr(findViewById(R.id.fri_f),5,db);
        addDescr(findViewById(R.id.sat_f),6,db);

    }

    //Making statusbar transparent
    private void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#00000000"));
    }
}
