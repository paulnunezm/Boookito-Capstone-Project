package com.nunez.bookito.homescreenWidget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by paulnunez on 4/1/17.
 */

/**
 * This service allows the remote adapter a {@link android.widget.RemoteViewsService.RemoteViewsFactory}
 * implementation to request {@link android.widget.RemoteViews}.
 */
public class BooksWidgetService extends RemoteViewsService {
  @Override
  public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new BooksRemoteViewsFactory(this.getApplicationContext(), intent);
  }
}
