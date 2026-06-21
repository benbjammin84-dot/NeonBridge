NeonBridge — Work In Progress
==============================

Branch: ci/fix-gradle-wrapper
PR: https://github.com/benbjammin84-dot/NeonBridge/pull/2

Summary of changes made (local + pushed):
- Removed zero-byte `gradle/wrapper/gradle-wrapper.jar` (rely on workflow-generated wrapper).
- Fixed invalid XML in `app/src/main/res/layout/activity_main.xml`.
- Updated `app/src/main/AndroidManifest.xml` to remove `package` attribute and set `android:exported`.
- Removed explicit `proguard-base` dependency to avoid duplicate classes.
- Added dependencies: `org.libsodium:libsodium-jni:1.0.18`, `androidx.security:security-crypto:1.1.0`.
- Added minimal UI stubs: `app/src/main/java/com/neonbridge/ui/BridgePrompt.kt` and `MeterView.kt`.
- Adjusted `VaultManager` to use `MasterKey` + `EncryptedSharedPreferences` and Base64 storage.

Local blockers / notes:
- Your machine needs `JAVA_HOME` set to Android Studio's bundled JDK and the Android SDK present.
- I added a `local.properties` locally earlier; do NOT commit it.
- CI runs have been triggered; check Actions for the latest run and logs.

How to resume locally (PowerShell):
1) Set Android SDK (adjust path if different):
   PS> $env:ANDROID_HOME='C:\Users\family\AppData\Local\Android\Sdk'
   PS> [Environment]::SetEnvironmentVariable('ANDROID_HOME',$env:ANDROID_HOME,'User')

2) Set Java (adjust path to your Android Studio JDK):
   PS> $env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
   PS> [Environment]::SetEnvironmentVariable('JAVA_HOME',$env:JAVA_HOME,'User')

3) From repo root:
   PS> cd C:\Users\family\NeonBridge
   PS> Set-Content -Path .\local.properties -Value 'sdk.dir=C:/Users/family/AppData/Local/Android/Sdk'
   PS> .\gradlew.bat clean assembleDebug --no-daemon

If build succeeds and you want the wrapper committed:
   PS> gradle wrapper --gradle-version 8.7
   PS> git add gradle/wrapper/gradle-wrapper.jar gradle/wrapper/gradle-wrapper.properties gradlew gradlew.bat
   PS> git commit -m "chore(ci): add generated Gradle wrapper"
   PS> git push origin HEAD

If you need me to keep working, tell me: "implement UI", "commit wrapper and rerun CI", or "pause — do nothing".
