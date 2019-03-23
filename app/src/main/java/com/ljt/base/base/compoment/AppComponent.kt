package com.ljt.base.base.compoment

import android.app.Application
import android.content.Context
import com.ljt.base.app.MainApplication
import com.ljt.base.base.module.AppModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {

    fun context(): Context
    fun application(): Application
    fun mainApplication(): MainApplication

    fun inject(mainApplication: MainApplication)

}