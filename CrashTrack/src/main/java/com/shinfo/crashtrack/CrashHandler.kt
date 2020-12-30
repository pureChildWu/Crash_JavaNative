package com.shinfo.crashtrack

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.*


/**
 *@ClassName:CrashHandler
 *@Author:CreatBy wlh
 *@Time:2020/12/30 00点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */

class CrashHandler : Thread.UncaughtExceptionHandler{

    private lateinit var dir: File
    private lateinit var pm: PackageManager
    private lateinit var packageInfo: PackageInfo
    private lateinit var sb: StringBuilder



    val FILE_NAME_SUFFIX = ".trace"

    companion object{

        var defaultUncaughtExceptionHandler: Thread.UncaughtExceptionHandler? = null
        var context: Context? = null

        fun init(context: Context){
            this.context = context
            defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()//记录原来的
            Thread.setDefaultUncaughtExceptionHandler(CrashHandler())//替换自己的
        }
    }

    init {

        /**
         * 创建文件
         */
        dir = File(context?.dataDir, "crash_info")
        if (!dir.exists())
            dir.mkdirs()

        pm = context?.packageManager!!
        packageInfo = pm.getPackageInfo(context?.packageName.toString(), PackageManager.GET_ACTIVITIES)
        sb = StringBuilder()
    }


    /**
     * 程序一场会回掉的方法
     */
    @SuppressLint("SimpleDateFormat")
    override fun uncaughtException(t: Thread, e: Throwable) {
        Log.i(javaClass.name,"uncaughtException")
        val time = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        val file = File(dir, "${time}$FILE_NAME_SUFFIX")
        /**
         * 将日志信息输出到文件中
         */
        val printWriter = PrintWriter(FileWriter(file))

        printWriter.println(time)
        printWriter.println("Thread:${t.name}")
        printWriter.println(getPhoneInfo())
        /**
         * 将崩溃的堆栈信息输出到文件中
         */
        e.printStackTrace(printWriter)
        /**
         * 让系统的异常处理累继续往下处理
         */
        printWriter.flush()
        printWriter.close()
        defaultUncaughtExceptionHandler?.uncaughtException(t,e)
    }

    /**
     * 拼接手机参数
     */
    fun getPhoneInfo(): String{
        sb.clear();
        //app版本
        sb.append("App Version: ");
        sb.append(packageInfo.versionName);
        sb.append("_");
        sb.append(packageInfo.versionCode.toString()  + "\n");

        //Android版本号
        sb.append("OS Version: ");
        sb.append(Build.VERSION.RELEASE);
        sb.append("_");
        sb.append(Build.VERSION.SDK_INT.toString() + "\n");

        //手机制造商
        sb.append("Vendor: ");
        sb.append(Build.MANUFACTURER + "\n");

        //手机型号
        sb.append("Model: ");
        sb.append(Build.MODEL + "\n");

        //CPU架构
        sb.append("CPU: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            sb.append(Arrays.toString(Build.SUPPORTED_ABIS));
        } else {
            sb.append(Build.CPU_ABI);
        }
        return sb.toString();
    }
}