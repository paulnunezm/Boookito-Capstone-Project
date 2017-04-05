package com.nunez.bookito.homescreenWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.nunez.bookito.R;
import com.nunez.bookito.entities.Book;
import com.nunez.bookito.repositories.FirebaseNodes;
import com.nunez.bookito.repositories.FirebaseRepo;

/**
 * Created by paulnunez on 4/1/17.
 */

public class BooksWidgetProvider extends AppWidgetProvider {
  private static final String TAG = "BooksWidgetProvider";
  public static final String CLICKED_ITEM_ACTION  = "widget_action_clicked";
  public static final String CLICKED_ITEM_BOOK_ID = "widget_item_clicked_book_id";
  public static final String CLICKED_ITEM_BOOK_TITLE = "widget_item_clicked_book_title";

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {

      // Set up the intent that starts the BooksWidgetServe, which will
      // provide the views for this collection.
      Intent intent = new Intent(context, BooksWidgetService.class);

      // Add the app widget ID to the intent extras.
      intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
      intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

      // Instantiate the RemoteViews object for the app widget layout.
      // using the "RemoteViews adapter".
      RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_bookito);

      // Set up the RemoteViews object to use a RemoteViews adapter.
      // This adapter connects
      // to a RemoteViewsService  through the specified intent.
      // This is how you populate the data.
      rv.setRemoteAdapter(appWidgetId, R.id.widget_list, intent);


      // The empty view is displayed when the collection has no items.
      // It should be in the same layout used to instantiate the RemoteViews
      // object above.
      rv.setEmptyView(R.id.widget_list, R.id.widget_empty_view);

      // This section makes it possible for items to have individualized behavior.
      // It does this by setting up a pending intent template. Individuals items of a collection
      // cannot set up their own pending intents. Instead, the collection as a whole sets
      // up a pending intent template, and the individual items set a fillInIntent
      // to create unique behavior on an item-by-item basis.
      Intent clickIntent = new Intent(context, BooksWidgetProvider.class);
      // Set the action for the intent.
      // When the user touches a particular view, it will have the effect of
      // broadcasting TOAST_ACTION.
      clickIntent.setAction(BooksWidgetProvider.CLICKED_ITEM_ACTION);
      clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
      intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
      PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, clickIntent,
          PendingIntent.FLAG_UPDATE_CURRENT);
      rv.setPendingIntentTemplate(R.id.widget_list, toastPendingIntent);

      appWidgetManager.updateAppWidget(appWidgetId, rv);
      super.onUpdate(context, appWidgetManager, appWidgetIds);
      appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.widget_list);
    }


  }

  // Called when the BroadcastReceiver receives an Intent broadcast.
  // This is where we can figure out wich element on the widget was click.
  @Override
  public void onReceive(Context context, Intent intent) {
    AppWidgetManager mgr = AppWidgetManager.getInstance(context);
//    Toast.makeText(context, "Touched view -> onReceive", Toast.LENGTH_SHORT).show();


    if (intent.getAction().equals(CLICKED_ITEM_ACTION)) {
      String bookId = intent.getStringExtra(CLICKED_ITEM_BOOK_ID);

      final FirebaseRepo firebaseRepo = FirebaseRepo.getInstance();
      firebaseRepo.setContext(context);
      firebaseRepo.setFirebaseAnalytics(FirebaseAnalytics.getInstance(context));

      // First we get the book from the corresponding id.
      FirebaseRepo.GetBookFromListListener listener = new FirebaseRepo.GetBookFromListListener() {
        @Override
        public void onReceivedBook(Book book) {
          // then we send it to "my books" list.
          firebaseRepo.moveBook(FirebaseNodes.WISHLIST, FirebaseNodes.MY_BOOKS, book);
          firebaseRepo.logEvent("moved_book", "from_widget");
        }

        @Override
        public void onError() {
          Log.d(TAG, "onError: Database error");
        }
      };

      firebaseRepo.getBookFromIdInList(FirebaseNodes.WISHLIST, bookId, listener);
    }

    super.onReceive(context, intent);
  }


}
