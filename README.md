![KigeBanner](https://raw.githubusercontent.com/BadKiko/kige/master/images/kigelogo.png)

## What is Kige?
Kige - is an image-picker for android written for Jetpack Compose. Its give you ability to choose image from your gallery. It uses Modal Bottom Sheet State which added in Jetpack Compose Material 3 1.2.0-alpha02

Project in develop!

Maded with  ♥ by Kiko

![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)

## Screenshots

|  ![Preview](https://github.com/BadKiko/kige/blob/master/preview/photo_2024-02-13_00-11-04.jpg)     |   ![Preview](https://github.com/BadKiko/kige/blob/master/preview/photo_2024-02-13_00-15-13.jpg)   |
|-------|------|


## Implementation
First of all, you need to add jitpack repo (in project settings.gradle)

> For Groovy:

```
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```

> For Kotlin DSL:

```
repositories {
  ...
  maven(url="https://jitpack.io")
}
```
And add library in build.gradle dependency

[![](https://jitpack.io/v/BadKiko/kige.svg)](https://jitpack.io/#BadKiko/kige)

> For Groovy:

```
implementation 'com.github.BadKiko:kige:version'
```

> For Kotlin DSL:

```
implementation("com.github.BadKiko:kige:version")
```

## Usage

Add to manifest

```
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

For get image-picker you can execute in @Composable function:

```
var image by remember { mutableStateOf<Painter?>(null) }

val rememberKigeState = rememberKigeState()

KigePicker(rememberKigeState) {
    image = it
}

image?.let {
    Image(
        modifier = Modifier.fillMaxSize(),
        painter = it,
        contentDescription = "",
        contentScale = ContentScale.FillBounds
    )
}
```
