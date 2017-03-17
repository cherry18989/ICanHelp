package com.a007.robot.icanhelp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.a007.robot.icanhelp.Adapters.CustomFragementAdapter;
import com.a007.robot.icanhelp.fragment.CommunityFragment;
import com.a007.robot.icanhelp.fragment.HelpFragment;
import com.a007.robot.icanhelp.fragment.MainFragment;
import com.a007.robot.icanhelp.fragment.ProfileFragment;
import com.a007.robot.icanhelp.view.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    private static RelativeLayout[] relatives;
    private static TextView[] texts;
    private static ImageView[] images;
    private final static int[] imagesNormal = { R.drawable.icon_main_study,
            R.drawable.icon_main_community, R.drawable.icon_main_message,
            R.drawable.icon_main_profile };
    private final static int[] imagesPressed = {
            R.drawable.icon_main_study_selected,
            R.drawable.icon_main_community_selected,
            R.drawable.icon_main_message_selected,
            R.drawable.icon_main_profile_selected };
    private final static int[] relativeLayouts = { R.id.main, R.id.community,
            R.id.help, R.id.profile };
    private final static int[] bottomText = { R.id.studytext,
            R.id.communitytext, R.id.messagetext, R.id.profiletext };
    private final static int[] bottomImage = { R.id.mainimg,
            R.id.communityimg, R.id.helpimg, R.id.profileimg };
    private static int textColorNormal = Color.parseColor("#ff64717f");
    private static int textColorPressed = Color.parseColor("#ff31363d");


    private CustomViewPager pager;
    private CustomFragementAdapter adapter;
    private MainFragment main;
    private CommunityFragment community;
    private HelpFragment help;
    private ProfileFragment profile;
    private List<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
        setCurrentView(0);
    }

    private void initView(){
        pager = (CustomViewPager) findViewById(R.id.pager);
        main = new MainFragment();
        community = new CommunityFragment();
        help = new HelpFragment();
        profile = new ProfileFragment();
        fragmentList.add(main);
        fragmentList.add(community);
        fragmentList.add(help);
        fragmentList.add(profile);
        adapter = new CustomFragementAdapter(getSupportFragmentManager(),fragmentList);
        pager.setAdapter(adapter);
        relatives = new RelativeLayout[4];
        texts = new TextView[4];
        images = new ImageView[4];
        for (int i = 0; i < 4; i++) {
            relatives[i] = (RelativeLayout) findViewById(relativeLayouts[i]);
            texts[i] = (TextView) findViewById(bottomText[i]);
            images[i] = (ImageView) findViewById(bottomImage[i]);
            relatives[i].setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main:
                setCurrentView(0);
                break;
            case R.id.community:
                setCurrentView(1);
                break;
            case R.id.help:
                setCurrentView(2);
                break;
            case R.id.profile:
                setCurrentView(3);
                break;
            default:
                break;
        }
    }

    private void setCurrentView(int index) {
        pager.setCurrentItem(index,false);
        clearTextsAndImages();
        texts[index].setTextColor(textColorPressed);
        images[index].setImageResource(imagesPressed[index]);
    }
    /**
     * 重置所有文本和图片
     */
    private void clearTextsAndImages() {
        for (int i = 0; i < 4; i++) {
            texts[i].setTextColor(textColorNormal);
            images[i].setImageResource(imagesNormal[i]);
        }

    }

    @Override
    public void onBackPressed() {

    }
}
