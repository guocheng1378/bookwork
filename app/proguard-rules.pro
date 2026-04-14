# 保留 DataStore 相关类
-keep class androidx.datastore.** { *; }

# 保留 Compose 相关
-dontwarn androidx.compose.**

# 保留小说阅读器核心类
-keep class com.novelreader.data.model.** { *; }

# 保留 BuildConfig
-keep class com.novelreader.BuildConfig { *; }

# Kotlin
-dontwarn kotlin.**
-keep class kotlin.Metadata { *; }

# 序列化相关
-keepattributes *Annotation*, Signature, Exception
