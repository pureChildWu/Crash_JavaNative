package com.shinfo.anrfileobserver

import android.content.Context
import android.os.FileObserver
import android.util.Log

/**
 *@ClassName:AnrFileObserver
 *@Author:CreatBy wlh
 *@Time:2020/12/30 10点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class AnrFileObserver constructor(path: String?): FileObserver(path, CLOSE_WRITE){

    constructor(context: Context) : this("/data/anr/")
//    constructor(context: Context) : this("${context.dataDir}/crash_info/")

    override fun onEvent(event: Int, path: String?) {
        when(event){
           OPEN ->
               Log.d(javaClass.name,"OPEN ---- path: $path")
            else ->
                Log.d(javaClass.name,"else ---- path: $path")

        }
    }

}
