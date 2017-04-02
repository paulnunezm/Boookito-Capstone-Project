package com.nunez.bookito.homescreenWidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.nunez.bookito.R;

/**
 * Created by paulnunez on 4/1/17.
 */

public class BooksWidgetProvider extends AppWidgetProvider {
  public static final String CLICKED_ITEM_ACTION  = "widget_action_clicked";
  public static final String CLICKED_ITEM_BOOK_ID = "widget_item_clicked_book_id";

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
      String title = intent.getStringExtra(CLICKED_ITEM_BOOK_ID);
      Toast.makeText(context, "Touched view " + title, Toast.LENGTH_LONG).show();

    }

    super.onReceive(context, intent);
  }


}
