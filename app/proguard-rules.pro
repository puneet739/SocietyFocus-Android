# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/jikoobaruah/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-allowaccessmodification
-repackageclasses
-useuniqueclassmembernames

-keepattributes SourceFile, LineNumberTable

#Zircon
#===================================
-keepclassmembers class com.zircon.app.model.** {
    <fields>;
}
-keep public class com.zircon.app.model.** {
    public void set*(***);
    public *** get*();
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
#-----------------------------------


# Android Support Libs
#===================================
-dontwarn android.support.v4.**
-dontwarn android.support.design.widget.**
-dontwarn org.apache.http.**
-dontwarn android.net.http.AndroidHttpClient
#-----------------------------------

# Android AppCompat
#===================================
-keep public class android.support.v7.widget.** { *; }
#-keep public class android.support.v7.internal.widget.** { *; }
#-keep public class android.support.v7.internal.view.menu.** { *; }

-keepclassmembers class android.support.v7.widget.Toolbar {
    *** mTitleTextView;
}
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
-keep class android.support.v4.widget.DrawerLayout {
    *** get*();
    void set*(***);
}
#-----------------------------------


# Retrofit
#===================================
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }


-dontwarn com.squareup.okhttp.**
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }

-dontwarn okio.**

#-----------------------------------


# GOOGLE PLAY SERVICES
#===================================
-keep class * extends java.util.ListResourceBundle {
    protected Object[][] getContents();
}
-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}
-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-dontwarn com.google.android.gms.**
#-----------------------------------


# GSON
#===================================
-keepattributes *Annotation*,EnclosingMethod

-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-----------------------------------


# keep viewpager
#===================================
-keep class * extends android.support.v4.view.ViewPager{ *; }
#-----------------------------------

# new relic
#===================================
-keep class com.newrelic.** { *; }
-dontwarn com.newrelic.**
-keepattributes Exceptions, Signature, InnerClasses, LineNumberTable
#-----------------------------------


##Warnings to be removed. Otherwise maven plugin stops, but not dangerous
#-dontwarn android.support.**
#-dontwarn com.sun.xml.internal.**
#-dontwarn com.sun.istack.internal.**
#-dontwarn org.codehaus.jackson.**
#-dontwarn org.springframework.**
#-dontwarn java.awt.**
#-dontwarn javax.security.**
#-dontwarn java.beans.**
#-dontwarn javax.xml.**
#-dontwarn java.util.**
#-dontwarn org.w3c.dom.**
#-dontwarn com.google.common.**
#-dontwarn com.octo.android.robospice.persistence.**
#-dontwarn com.octo.android.robospice.SpiceService
