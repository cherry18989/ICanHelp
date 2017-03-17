package com.a007.robot.icanhelp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.a007.robot.icanhelp.Adapters.CustomFragementAdapter;
import com.a007.robot.icanhelp.R;
import com.a007.robot.icanhelp.view.CustomViewPager;
import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cherry on 2017/3/1.
 */

public class CommunityFragment extends Fragment {
    private CustomViewPager communityPager;
    private PagerSlidingTabStrip mTab;
    private CustomFragementAdapter adapter;


    /**
     * 选中前文本颜色
     */
    private int mTextColorNormal = Color.BLACK;
    /**
     * 选中后文本颜色
     */
    private int mTextColorSelected = Color.parseColor("#ff00bb99");


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_community,container,false);
        initView(view);
        return view;
    }

    private void initView(View view){
        communityPager = (CustomViewPager) view.findViewById(R.id.communityPager);
        mTab = (PagerSlidingTabStrip) view.findViewById(R.id.communityTabs);
        CommunityMineFragment communityMineFragment = new CommunityMineFragment();
        CommunityFriendFragment communityFriendFragment = new CommunityFriendFragment();
        List<Fragment> fragmentList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        fragmentList.add(communityMineFragment);
        fragmentList.add(communityFriendFragment);
        titleList.add("我的动态");
        titleList.add("好友动态");
        setTabsValue();
        adapter = new CustomFragementAdapter(getChildFragmentManager(),fragmentList,titleList);
        communityPager.setAdapter(adapter);
        mTab.setViewPager(communityPager);


    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
       /* // 设置Tab是自动填充满屏幕的
        mTab.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        mTab.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
        mTab.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, dm));
        // 设置Tab Indicator的高度
        mTab.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, dm));
        // 设置Tab标题文字的大小
        mTab.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, dm));
        // 设置Tab Indicator的颜色
        mTab.setIndicatorColor(Color.parseColor("#d83737"));//#d83737   #d83737(绿)
        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
        mTab.setSelectedTextColor(Color.parseColor("#ffffff"));
        // 取消点击Tab时的背景色
        mTab.setTabBackground(0);*/
        mTab.setShouldExpand(true);
        mTab.setTextColor(mTextColorNormal);
        mTab.setIndicatorHeight((int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 5, getResources()
                        .getDisplayMetrics()));
        mTab.setIndicatorColor(mTextColorSelected);
        mTab.setBackgroundColor(Color.WHITE);
        mTab.setDividerColor(Color.TRANSPARENT);
    }
}
