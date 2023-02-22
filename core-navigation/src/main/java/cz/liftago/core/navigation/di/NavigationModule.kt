package cz.liftago.core.navigation.di

import cz.liftago.core.navigation.ActionsDelegate
import cz.liftago.core.navigation.Navigator
import cz.liftago.core.navigation.internal.NavigatorInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigator(
        delegate: ActionsDelegate
    ): Navigator = NavigatorInstance.init(delegate)
}
