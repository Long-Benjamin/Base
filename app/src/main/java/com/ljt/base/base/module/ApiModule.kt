package com.ljt.base.base.module

import cn.com.iyin.base.ApiService
import com.ljt.base.base.scope.NetScope
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit


@Module
class ApiModule {

    @Provides
    @NetScope
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create<ApiService>(ApiService::class.java)
    }

}