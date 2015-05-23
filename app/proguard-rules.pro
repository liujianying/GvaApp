#-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-dontskipnonpubliclibraryclassmembers

-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}


-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}



-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }


#-libraryjars libs/core.jar
-dontwarn com.google.zxing.**
-keep class com.google.zxing.** { *; }

#-libraryjars libs/flame.jar
-dontwarn com.pocketdigi.utils.**
-keep class com.pocketdigi.utils.** { *; }

#-libraryjars libs/pinyin4j-2.5.0.jar



-keep public class android.webkit.WebView { *; }
-keep public class android.webkit.WebViewClient { *; }

#-libraryjars libs/Android_SDK_v1.2.jar
-dontwarn com.tencent.weibo.**
-keep class com.tencent.weibo.** { *; }

#-libraryjars libs/alipaysdk.jar
-dontwarn com.alipay.android.app.**
-dontwarn com.alipay.sdk.**
-keep class com.alipay.android.app.** { *; }
-keep class com.alipay.sdk.** { *; }

#-libraryjars libs/libammsdk.jar
-dontwarn com.tencent.a.a.a.a.**
-dontwarn com.tencent.mm.**
-dontwarn com.tencent.wxop.stat.**
-keep class com.tencent.a.a.a.a.** { *; }
-keep class com.tencent.mm.** { *; }
-keep class com.tencent.wxop.stat.** { *; }


#-libraryjars libs/gson-2.2.4.jar
#-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.* { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.gson.** { *;}
-keep class com.test.model.response.** {*;}


-dontwarn android.support.**
-dontwarn com.alibaba.fastjson.**
#-libraryjars libs/fastjson-1.1.42.jar
-keepattributes Signature
-keep class com.alibaba.fastjson.** { *; }


#-libraryjars libs/dm_offerwall_2.2.3.jar
-dontwarn cn.dm.**
-keep class cn.dm.** { *; }
-keep class cn.dm.android.ui.interaction.** {*;}
-keepattributes *Annotation*


#-libraryjars libs/yjfsdk.jar
-dontwarn com.eadver.offer.**
-dontwarn org.apache.commons.codec**
-keep class com.eadver.offer.** { *; }
-keep class org.apache.commons.codec.** { *; }

#-libraryjars libs/dianru_sdk_v1.0.2.jar
-dontwarn com.dianru.**
-keep class com.dianru.** { *; }


#-libraryjars libs/baidu_api_3_4.jar
-dontwarn com.baidu.mobstat.**
-keep class com.baidu.mobstat.** { *;}
-keep class * extends com.baidu.mobstat.**
-keep class com.baidu.kirin.** {*; }
-keep class com.baidu.*.*

-keep class org.adver.score.**{*;}

-keepattributes Exceptions,InnerClasses
-keep class io.rong.** { *;}

-keep class **.R$* {*;}

-keep class org.adver.score.**{*;}


-dontwarn com.thoughtworks.xstream.**
-keep class com.thoughtworks.xstream.**{*;}

-dontwarn org.xmlpull.**
-keep class org.xmlpull.**{*;}


-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R{*;}
-dontwarn cn.sharesdk.**
-dontwarn **.R$*
-keep class m.framework.**{*;}

-dontwarn com.facebook.**
-keep class com.facebook.** { *;}

-dontwarn com.ydh.gva.localtion.**
-keep class com.ydh.gva.localtion.** { *;}


-dontwarn com.hp.hpl.sparta.**
-dontwarn net.sourceforge.pinyin4j.**
-dontwarn demo.**
-keep class demo.** { *; }
-keep class com.hp.hpl.sparta.** { *; }
-keep class net.sourceforge.pinyin4j.** { *; }


#-libraryjars libs/Android_2DMapApi_V2.4.0.jar
#-libraryjars libs/AMap_Services_V2.3.1.jar
-dontwarn com.amap.api.**
-keep class com.amap.api.** { *; }


#-libraryjars libs/Android_Location_V1.3.0.jar
-dontwarn com.aps.**
-dontwarn com.amap.api.location.core.**
-dontwarn com.amap.api.location.**
-keep class com.aps.** { *; }
-keep class com.amap.api.location.core.** { *; }
-keep class com.amap.api.location.** { *; }


