# ZayviusAds
Libraries that are open source

Version release 1.0.0

## Gradle Dependency

### settings.gradle
```java
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url 'https://jitpack.io' }
    }
}
```

### build.gradle
```java
dependencies {
	        implementation 'com.github.zayviusdigital:ZayviusAds:1.0.0'
	}
```
## AndroidManifest

```java
#Admob
        <meta-data
            android:name="com.google.android.gms.ads.APPLICATION_ID"
            android:value="ca-app-pub-3940256099942544~3347511713"/>
```
## Initialize

```java
#Admob
ZayviusAdsInitialize.Admob(this);
```
## GDPR

```java
#Admob
 ZayviusAdsGDPR.Admob(this);
```
## Banner

```java
#Admob
Button tes_i = findViewById(R.id.tes_inter);
ZayviusAdsBanner.banner(this, banner, "ca-app-pub-3940256099942544/6300978111");
```

## Interstitial

```java
#Admob
ZayviusAdsInterstitial.loadInterstitial(MainActivity.this,"ca-app-pub-3940256099942544/1033173712",interval);
```


## Developer
Muhammad Zayvius Alsyazani

[Zayvius Digital](https://zayviusdigital.com/)
