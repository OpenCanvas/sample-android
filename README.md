## Description
This is a sample project that demonstrates how to use the [OpenCanvas][0] platform to build a location based Android app.

## Dependencies

##### Android Library Projects
You must have [Google Play Services][1],  [Android Maps Utils][2] and [Volley][3] added 
as library project dependencies in your project.

##### API Keys ######
You will also need to register for a [maps api key][4] and an [OpenCanvas app key][5].
Once you obtain the required keys, replace them in the `/res/values/api_keys.xml` file.

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="maps_api_key">Your maps api key goes here</string>
    <string name="opencanvas_api_key">Your OpenCanvas app key goes here</string>
</resources>
```

##### Included jar libraries
The sample app is also using the [Android Support Library v4][6] and the [Google Gson][7] 
library which are included in the source code.

## Sample
The app contains two screens. The first is a list of routes (see `Route` class) that are downloaded using 
the OpenCanvas API. The second is a map that displays the route on the map, as well as 
the POIs (see `Place` class) that are related to this route.

[0]: http://www.opencanvas.co/
[1]: http://developer.android.com/google/play-services/setup.html
[2]: https://github.com/googlemaps/android-maps-utils
[3]: https://android.googlesource.com/platform/frameworks/volley/
[4]: https://developers.google.com/maps/documentation/android/start#getting_the_google_maps_android_api_v2
[5]: http://opencanvas.co/developers
[6]: http://developer.android.com/tools/support-library/setup.html
[7]: https://code.google.com/p/google-gson/
