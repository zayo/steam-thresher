package cz.liftago.steamthresher.navigation.di

import cz.liftago.core.navigation.blocks.ActionHandler
import cz.liftago.steamthresher.navigation.DefaultActionHandler
import cz.liftago.steamthresher.navigation.FeatureComposeActionHandler
import cz.liftago.steamthresher.navigation.FeatureFragmentActionHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ActionHandlersModule {

    @Singleton
    @Binds
    @IntoSet
    fun provideDefaultActionHandler(handler: DefaultActionHandler): ActionHandler

    @Singleton
    @Binds
    @IntoSet
    fun provideFeatureComposeActionHandler(handler: FeatureComposeActionHandler): ActionHandler

    @Singleton
    @Binds
    @IntoSet
    fun provideFeatureFragmentActionHandler(handler: FeatureFragmentActionHandler): ActionHandler
}