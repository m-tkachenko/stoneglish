package pl.salo.stoneglish.di

import android.content.Context
import androidx.appcompat.app.AlertDialog
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import pl.salo.stoneglish.R

@Module
@InstallIn(ActivityComponent::class)
object AuthModule {
    @Provides
    fun provideProgressDialog(@ActivityContext appContext: Context): AlertDialog {
        val builder = AlertDialog.Builder(appContext)
        builder.setCancelable(false)
        builder.setView(R.layout.loading_dialog_layout)
        return builder.create()
    }
}