package com.shinfo.crash_javaandndk

import android.app.Application
import android.os.FileObserver
import com.shinfo.anrfileobserver.AnrFileObserver
import com.shinfo.crashtrack.CrashHandler
import com.shinfo.crashtrack.CrashHandler.Companion.init
import com.shinfo.crashtrack.NativeCrash
import java.io.File

/**
 *@ClassName:App
 *@Author:CreatBy wlh
 *@Time:2020/12/30 10点
 *@Email:m15904921255@163.com
 *@Desc:TODO
 */
class App : Application(){

    var anrFileObserver: FileObserver? = null
    override fun onCreate() {
        super.onCreate()

        CrashHandler.init(this)

        val file = File(dataDir, "native_crash")
        if (!file.exists())
            file.mkdirs()
        NativeCrash.initNativeCrash(file.absolutePath)

        anrFileObserver = AnrFileObserver(this)
        anrFileObserver?.startWatching()
    }



}