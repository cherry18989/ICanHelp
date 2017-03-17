package com.a007.robot.icanhelp.profileActivity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.util.ImgLoader;
import com.a007.robot.icanhelp.util.ImgLoaderSave;

import org.w3c.dom.Text;

public class PersonalInfoActivity extends Activity {
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private ImageView picture;
    private TextView name;
    private TextView nick;
    private TextView gender;
    private TextView school;
    private TextView address;
    private String imgPath = "http://114.55.101.163:8007/FinaRobot/user_image/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_personal_info);
        sp = getSharedPreferences("user",MODE_APPEND);
        editor = sp.edit();
        initView();
        initData();
    }
    public void initView(){
        picture = (ImageView) findViewById(R.id.picture);
        name = (TextView) findViewById(R.id.name);
        nick = (TextView) findViewById(R.id.nick);
        gender = (TextView) findViewById(R.id.gender);
        school = (TextView) findViewById(R.id.school);
        address = (TextView) findViewById(R.id.address);
    }
    public void initData(){
        String faceImage = sp.getString("faceImage","");
        if(faceImage!=""){
            new ImgLoaderSave().showImgByAsync(picture,faceImage);
        }
        name.setText(sp.getString("realName",""));
        nick.setText(sp.getString("realName",""));
        gender.setText(sp.getString("gender","").equals("0")?"女":"男");
        school.setText(sp.getString("school",""));
        address.setText(sp.getString("city",""));
    }
}
