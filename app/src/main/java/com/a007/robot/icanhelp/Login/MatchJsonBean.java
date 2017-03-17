package com.a007.robot.icanhelp.Login;

/**
 * Created by Cherry on 2016/12/23.
 */

public class MatchJsonBean {
     int status;
     String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "JsonBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
