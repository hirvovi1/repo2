#include "fi_jni_test_Tester.h"
#include <iostream>

JNIEXPORT void JNICALL Java_fi_jni_test_Tester_sayHello
  (JNIEnv* env, jobject thisObject) {
    std::cout << "Hello from C++ !!" << std::endl;
}


