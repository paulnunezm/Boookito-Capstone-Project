# Capstone-Project
An Android books library organizer for the Android Developer Nanodegree Course. 

## Setting it up.
In your root folder create a gradle.properties file and inside add the following line:

```
GOODREADS_API_KEY = "{with you Goodreads api key here}"
```

And for signing:
```
KEY_ALIAS = "..."

KEY_PASSWORD = "..."

STORE_FILE_PATH = "..."

STORE_PASSWORD =  "..."
```

And that's all. Now you're good to go.


###Libraries
- **[Retrofit](https://github.com/square/retrofit)** For making request to rest apis.
- Firebase For the database, authentication, analytics and adMob.
- **[Firebase-ui](https://github.com/firebase/FirebaseUI-Android)** For login view and adapters.
- **[Picasso](https://github.com/square/picasso)** To perform image request and loading it to the views.