package com.ljt.base.base.compoment

import cn.com.iyin.base.ApiService
import com.ljt.base.base.module.ApiModule
import com.ljt.base.base.module.NetModule
import com.ljt.base.base.scope.NetScope
import dagger.Component

@NetScope
@Component(dependencies = [AppComponent::class], modules = [NetModule::class, ApiModule::class])
interface ModuleComponent {

    fun apiService(): ApiService


}