-dontwarn org.androidannotations.**
##--泛型类型转换错误---##
-keepattributes Signature
##--显示StackTraces里的行数--##
-keepattributes SourceFile,LineNumberTable
##--JAVASCRIPT--##
-keepattributes *JavascriptInterface*
-keep public class * extends android.support.v4.app.Fragment
-keep public class com.benmu.drop.activity.bean.**{*;}
-keep public class com.sun.**

#保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

##--保护代码中序列号的类---##
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
    public <fields>;
}
-keep class android.app.**{*;}
-keep class java.nio.**{*;}
-keep class java.lang.**{*;}

#对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
    *;
}
#eros
-keep class com.benmu.framework.**{*;}


# 支付
-keepclassmembers class * {
    @android.webkit.JavascriptInterface <methods>;
}

-keepattributes JavascriptInterface
-keepattributes *Annotation*

-dontwarn com.razorpay.**
-keep class com.razorpay.** {*;}

-optimizations !method/inlining/*

-keepclasseswithmembers class * {
  public void onPayment*(...);
}
# weex混淆
-keep class com.taobao.weex.WXDebugTool{*;}


-keep class com.taobao.weex.devtools.common.LogUtil{*;}
-keepclassmembers class ** {
  @com.taobao.weex.ui.component.WXComponentProp public *;
}
-keep class com.taobao.weex.bridge.**{*;}
-keep class com.taobao.weex.dom.**{*;}
-keep class com.taobao.weex.adapter.**{*;}
-keep class com.taobao.weex.common.**{*;}
-keep class * implements com.taobao.weex.IWXObject{*;}
-keep class com.taobao.weex.ui.**{*;}
-keep class com.taobao.weex.ui.component.**{*;}
-keep class com.taobao.weex.utils.**{
    public <fields>;
    public <methods>;
    }
-keep class com.taobao.weex.view.**{*;}
-keep class com.taobao.weex.module.**{*;}
-keep public class * extends com.taobao.weex.common.WXModule{*;}
-keep public class * extends com.taobao.weex.ui.component.WXComponent{*;}
-keep class * implements com.taobao.weex.ui.IExternalComponentGetter{*;}

#fastJson
-keep class com.alibaba.fastjson.**{*;}
-dontwarn com.alibaba.fastjson.**
#友盟分享混淆
-dontwarn com.umeng.**
-keep class javax.**{*;}
-dontwarn com.alibaba.weex.**
-dontwarn android.test.**
#google
-dontwarn com.google.android.gms.common.**
-dontwarn okio.**
-dontwarn org.junit.**

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

