package com.a007.robot.icanhelp.util;

/**
 * Created by Administrator on 2017/3/10 0010.
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import java.util.Locale;



/**
 * 通用网络状态的工具类
 */
public class NetworkUtils {
    private static final String TAG = "Networks";
    /**
     * 是否网络连通，不管什么网络
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
             if (context != null) {
                    ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                             .getSystemService(Context.CONNECTIVITY_SERVICE);
                     NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
                     if (mNetworkInfo != null) {
                             return mNetworkInfo.isAvailable();
                         }
                 }
             return false;
         }


    /**
     * ni.getTypeNmae()可能取值如下
     *  WIFI，表示WIFI联网
     *  MOBILE，表示GPRS、EGPRS 3G网络
     *  WIFI和(E)GPRS不能共存，如果两个都打开，系统仅支持WIFI
     * @return true，使用WIFI并且
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 是否连通了GPRS/3G
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        try {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo = mConnectivityManager
                    .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isConnected();
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否飞行模式
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isAirplaneModeOn(Context context) {
        try {
            int modeIdx = Settings.System.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0);
            return modeIdx == 1;
        } catch (SecurityException e) {
            return false;
        }
    }

    public static final int NETWORK_TYPE_UNKNOWN = -1;
    public static final int NETWORK_TYPE_NET_WORK_DISABLED = 0;
    public static final int NETWORK_TYPE_WAP = 1;// wap
    public static final int NETWORK_TYPE_NET = 2;// net
    public static final int NETWORK_TYPE_WIFI = 3;// wifi
    public static final int NETWORK_TYPE_CM_NET = 4;// cmnet

    public static String getNetworkTypeString(Context context) {
        int nettype = NetworkUtils.checkNetworkType(context);
        String result = "";
        switch (nettype) {
            case NetworkUtils.NETWORK_TYPE_NET:
                result = "3gnet";
                break;
            case NetworkUtils.NETWORK_TYPE_WAP:
                result = "wap";
                break;
            case NetworkUtils.NETWORK_TYPE_WIFI:
                result = "wifi";
                break;
            case NetworkUtils.NETWORK_TYPE_NET_WORK_DISABLED:
                result = "网络处于关闭状态";
                break;
            case NetworkUtils.NETWORK_TYPE_UNKNOWN:
                result = "未知类型";
                break;
            case NetworkUtils.NETWORK_TYPE_CM_NET:
                result = "cmnet";
        }
        return result;
    }

    public static int checkNetworkType(Context context) {
        final String CTWAP = "ctwap";
        final String CMWAP = "cmwap";
        final String WAP_3G = "3gwap";
        final String NET_3G = "3gnet";
        final String NET_CM_3G = "cmnet";
        final String UNIWAP = "uniwap";

        try {
            final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            final NetworkInfo info = connectivityManager.getActiveNetworkInfo();
            if (info == null || !info.isAvailable()) {
                return NETWORK_TYPE_NET_WORK_DISABLED;
            } else {
                int netType = info.getType();
                int retType = NETWORK_TYPE_UNKNOWN;
                if (netType == ConnectivityManager.TYPE_WIFI) {
                    retType = NETWORK_TYPE_WIFI;
                } else if (netType == ConnectivityManager.TYPE_MOBILE) {
                    String netMode = info.getExtraInfo();
                    if (netMode != null) {
                        netMode = netMode.toLowerCase(Locale.getDefault());
                        if (netMode.equals(NET_3G)) {
                            retType = NETWORK_TYPE_NET;
                        } else if (netMode.equals(NET_CM_3G)) {
                            retType = NETWORK_TYPE_CM_NET;
                        } else {
                            if (netMode.equals(CTWAP) || netMode.equals(CMWAP) || netMode.equals(WAP_3G) || netMode.equals(UNIWAP)) {
                                return NETWORK_TYPE_WAP;
                            }
                        }
                    }
                }
                return retType;
            }
        } catch (Exception ex) {
            return NETWORK_TYPE_UNKNOWN;
        }
    }
}
