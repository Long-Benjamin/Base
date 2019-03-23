package com.ljt.base.base.module

import android.app.Application
import android.content.Context
import com.ljt.base.app.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(app: MainApplication) {

    var application: MainApplication

    init {
        application = app
    }

    @Singleton
    @Provides
    fun provideMainApplication(): MainApplication {
        return application
    }

    @Singleton
    @Provides
    fun provideApplication(): Application {
        return application
    }

    @Singleton
    @Provides
    fun provideContext(): Context {
        return application
    }


}