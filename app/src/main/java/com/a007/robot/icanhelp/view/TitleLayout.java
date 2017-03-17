package com.a007.robot.icanhelp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.a007.robot.icanhelp.R;

/**
 * Created by Administrator on 2017/3/3 0003.
 */

public class TitleLayout extends LinearLayout implements View.OnClickListener{
    TextView turnBack;
    TextView confirm;
    public TitleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.title,this);
        turnBack = (TextView) findViewById(R.id.turnBack);
        confirm = (TextView) findViewById(R.id.confirm);
        turnBack.setOnClickListener(this);
        confirm.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.turnBack){
            ((Activity)getContext()).finish();
        }else if(view.getId()==R.id.confirm){

        }
    }
}
