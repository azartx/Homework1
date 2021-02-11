package com.gameproject.homework9

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import android.widget.Toast
import com.gameproject.homework9.data.WeatherRepository
import com.gameproject.homework9.utils.Constants.actualCity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class WeatherWidget : AppWidgetProvider() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }

    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        compositeDisposable.clear()
    }

    override fun onEnabled(context: Context) {
    }

    override fun onDisabled(context: Context) {
        compositeDisposable.clear()
    }


    private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val views = RemoteViews(context.packageName, R.layout.weather_widget)

        WeatherRepository().getWeather(actualCity)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { newsList ->
                            views.setTextViewText(R.id.countryWidget, newsList.city.name)
                            views.setTextViewText(R.id.dateTimeWidget, newsList.list[0].dt_txt)
                            views.setTextViewText(R.id.degreesWidget, newsList.list[0].main.temp.toInt().toString())
                            appWidgetManager.updateAppWidget(appWidgetId, views)
                        },
                        { error ->
                            Toast.makeText(context, "Widget Weather ERROR: $error", Toast.LENGTH_LONG).show()


                        }
                ).apply { compositeDisposable.add(this) }
    }
}