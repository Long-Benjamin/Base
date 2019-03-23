package com.ljt.base.app

import com.ljt.base.base.compoment.AppComponent
import com.ljt.base.base.compoment.DaggerAppComponent
import com.ljt.base.base.compoment.DaggerModuleComponent
import com.ljt.base.base.compoment.ModuleComponent
import com.ljt.base.base.module.ApiModule
import com.ljt.base.base.module.AppModule
import com.ljt.base.base.module.NetModule
import com.ljt.base.commond.UrlConfig

class Injects {

    companion object {

        lateinit var appComponent: AppComponent
        lateinit var netComponent: ModuleComponent

        fun init(mainApplication: MainApplication) {

            appComponent = DaggerAppComponent.builder()
                    .appModule(AppModule(mainApplication))
                    .build()

            netComponent = DaggerModuleComponent.builder()
                    .appComponent(appComponent)
                    .netModule(NetModule(UrlConfig.BASE_URL))
                    .apiModule(ApiModule())
                    .build()
        }

        //这里是在AppContext里面就注入了
        fun appComponent(): AppComponent {
            return appComponent
        }

       /* fun homeComponent(view: HomeContract.View): HomeComponent {
            return DaggerHomeComponent.builder()
                    .moduleComponent(netComponent)
                    .homeModule(HomeModule(view))
                    .build()
        }*/


    }

}