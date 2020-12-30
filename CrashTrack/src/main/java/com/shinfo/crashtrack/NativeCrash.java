package com.shinfo.crashtrack;

/**
 * @ClassName:NativeCrash
 * @Author:CreatBy wlh
 * @Time:2020/12/30 04点
 * @Email:m15904921255@163.com
 * @Desc:TODO
 */
public class NativeCrash {

    static {
        System.loadLibrary("bugly");
    }
    public static native void initNativeCrash(String path);
}
