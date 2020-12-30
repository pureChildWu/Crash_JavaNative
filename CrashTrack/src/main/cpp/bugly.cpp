

#include <jni.h>
#include <android/log.h>
#include "breakpad/src/client/linux/handler/minidump_descriptor.h"
#include "breakpad/src/client/linux/handler/exception_handler.h"

bool DumpCallback(const google_breakpad::MinidumpDescriptor& descriptor,
                  void* context,
                  bool succeeded) {
    printf("Dump path: %s\n", descriptor.path());
    return false;
}

extern "C"
JNIEXPORT void JNICALL
Java_com_shinfo_crashtrack_Test_test(JNIEnv *env, jclass clazz) {

    __android_log_print(ANDROID_LOG_INFO,"native","xxxxxxx");
    int *p = NULL;
    *p = 10;

}

extern "C"
JNIEXPORT void JNICALL
Java_com_shinfo_crashtrack_NativeCrash_initNativeCrash(JNIEnv *env, jclass clazz, jstring path_) {
    const char* path = env -> GetStringUTFChars(path_,0);


    google_breakpad::MinidumpDescriptor descriptor(path);
    static google_breakpad::ExceptionHandler eh(descriptor, NULL, DumpCallback,
                                                NULL, true, -1);
    env->ReleaseStringUTFChars(path_,path);
}