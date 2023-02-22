package cz.liftago.steamthresher.navigation.di

import android.content.Context
import android.content.Intent
import cz.liftago.core.navigation.ActionsDelegate
import cz.liftago.core.navigation.actions.FeatureActivityAction
import cz.liftago.core.navigation.utils.putNavigationAction
import cz.liftago.feature.activity.FeatureActivity
import cz.liftago.steamthresher.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActionDelegateModule {
    @Singleton
    @Provides
    fun provideActionsDelegate(
        @ApplicationContext
        context: Context
    ): ActionsDelegate = ActionsDelegate { action ->
        val intent = when (action) {
            is FeatureActivityAction ->
                Intent(context, FeatureActivity::class.java)

            else ->
                Intent(context, MainActivity::class.java)
        }
        // Always propagate action
        intent.putNavigationAction(action)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}