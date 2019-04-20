package com.ljt.base.base.intercepter


import com.ljt.base.commond.Config
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import java.io.IOException

/**
 * Created by roly on 2018/2/4.
 *
 *
 * 刷新token
 */

class TokenAuthenticator : Authenticator {

    @Throws(IOException::class)
    override fun authenticate(route: Route?, response: Response): Request? {

        //防止后台一直返回401
        if (++Config.RETRY_401 >= 12) {
            Timber.e("retry 401 >= 12!!!")
            sendNeedLoginEvent()
            return null
        }

        //去掉参数之后继续之前的请求
        val request = response.request()
        val fixedUrl = UrlHelper.fixUrl(request.url().toString())
        return request.newBuilder().url(fixedUrl).build()
    }

    private fun sendNeedLoginEvent() {
        clearUserInfo()
        //        EventBus.getDefault().post(new RefreshTokenEvent());
        Config.RETRY_401 = 0
    }

    private fun clearUserInfo() {

    }

    /*private UserInfo retrieveNewToken(String oldRefreshToken) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.BASE_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        UserInfo userInfo = null;
        try {
            TokenService service = retrofit.create(TokenService.class);
            Call<UserInfo> call = service.refreshToken(oldRefreshToken);
            userInfo = call.execute().body();
            Config.RETRY_401 = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userInfo;
    }*/

}
