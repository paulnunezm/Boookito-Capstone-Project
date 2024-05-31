# Capstone-Project
Bookito is an Android personal books library organizer in which I've used the full gamut of the Android framework, including a Material user experience and the use of a variety of Google Play services:

- Created a book search Activity that uses Retrofit to make the request to the Goodreads API, an AsyncTaskLoader to manage the requests and loading the data, and a RecyclerView to display it.
- Implemented an MVP architecture and style it with the Material design principles.
- Configured Firebase services as Realtime database, AdMob and analytics.


## Libraries used in this project:
- **[Retrofit](https://github.com/square/retrofit)** For making request to rest apis.
- **[Firebase](ttps://firebase.google.com)** For the database, authentication, analytics and adMob.
- **[Firebase-ui](https://github.com/firebase/FirebaseUI-Android)** For login view and adapters.
- **[Picasso](https://github.com/square/picasso)** To perform image request and loading it to the views.

## Setting it up.
In your root folder create a gradle.properties file and inside add the following line:


```
GOODREADS_API_KEY = "{with you Goodreads api key here}"
```

Set your `keystore.jks` in the project's root folder, and remove the dummy one.

And for signing:
```
KEY_ALIAS=your_alias

KEY_PASSWORD your_key_password

STORE_PASSWORD=your_store_password
```

And that's all. Now you're good to go.
