package cz.liftago.core.navigation.internal.di

import cz.liftago.core.navigation.blocks.ActionHandler
import cz.liftago.core.navigation.internal.ActionHandlerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ActionHandlersManagerModule {

    @Provides
    @Singleton
    fun provideActionHandlersManager(handlers: Set<@JvmSuppressWildcards ActionHandler>):
            ActionHandlerManager = ActionHandlerManager(handlers)
}
