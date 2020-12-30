# Crash_JavaNative
The Java Native method and Android method are implemented  Anr monitoring

## 前言

最近去面试了一把，被问到如何实现一个线上日志监控系统，小编之前学习过 但是确实忘了，所以为了让自己更好的记住
特意写一个 java Exception native Exception 的监听  并做一个存储 文件的上传没有实现  
有想法的朋友可以自己去实现

Kotlin 团队为 Android 开发提供了一套超越标准语言功能的工具：

- [Kotlin Android 扩展](https://www.kotlincn.net/docs/tutorials/android-plugin.html)是一个编译器扩展， 可以让你摆脱代码中的 `findViewById()` 调用，并将其替换为合成的编译器生成的属性。
- [Anko](http://github.com/kotlin/anko) 是一个提供围绕 Android API 的 Kotlin 友好的包装器的库 ，以及一个可以用 Kotlin 代码替换布局 .xml 文件的 DSL。

项目框架使用了androidx的库

## （整个过程比较简单，所以我也就介绍的比较简单）
## 实现java Exception的思路
    
    1. 实现 Thread 提供的 UncaughtExceptionHandler接口 这里我们的实现类为`CrashHandler`(模仿源码`KillingUncaughtException`的命名)
    2. 通过 `Thread.setDefaultUncaughtExceptionHandler(CrashHandler())`//替换源码中已经实现的类
    3. 当java 方法发生异常 会回掉 `CrashHandler`中的 `override fun uncaughtException(t: Thread, e: Throwable) {}`方法
    4. 将异常的堆栈信息 写入到目录 data/包名/crash_info 目录下

## 实现native Exception的思路  
    
    native 的实现思路和 java的实现思路一致 linux已经为我们提供了可以实现监听的点
    breakpad 也是利用这个原理来做的
    我将异常的堆栈信息 写入到目录 data/包名/native_crash 目录下

## 通过继承 FileObserver 监听文件目录的改变 实现文件上传 (通过此实现 还可以实现/data/anr/ ***.trace 的文件监听 来达到线上anr监控的日志实现)

## 笔者在做测试时发现 FileObserver 在监听高版本 anr目录下 无法回掉onEvent方法  
在这里我再提供一种思路来实现 有兴趣的可以去尝试
## 通过 [AnrWatchDog](https://github.com/SalomonBrys/ANR-WatchDog)) 的思路,也就是 一下几点：
  ##ANRWatchDog继承自`Thread`，所以它最重要的就是run方法。
  ##在`run`方法中实现无限循环
  ##`_tick`自动加上5000毫秒并往`Handler`发消息
  ##睡眠5000毫秒
  ##如果5000毫秒之内主线程还没有处理`Runnable`，将`_tick`置0，则进入ANR异常。
  具体动的可以去看它实现的具体代码 上面我已经提供了链接
  
  
## 此开源项目仅做学习交流使用, 不可用于任何商业用途，如果你觉得不错, 对你有帮助, 欢迎点个 star 谢谢
