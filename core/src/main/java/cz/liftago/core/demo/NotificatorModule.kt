package cz.liftago.core.demo

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface NotificatorModule {

    @Binds
    @Singleton
    fun bindNotificator(impl: NotificatorImpl): Notificator
}