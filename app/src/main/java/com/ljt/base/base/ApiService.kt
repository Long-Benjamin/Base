package cn.com.iyin.base

import com.ljt.base.base.bean.UserInfo
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService{

    // 签名列表
   /* @GET(UrlConfig.SIGN_LIST)
    fun getSignList(@Query("blockStatusType") blockStatusType: String,
                        @Query("pageSize") pageSize: String,
                        @Query("pageNum") pageNum: String): Observable<Result<ContractBean>>*/


    @POST("/login/normal")
    fun login(@Query("account") account: String,
              @Query("bizCode") bizCode: String,
              @Query("clientId") clientId: String,
              @Query("password") password: String): Observable<Result<UserInfo>>




}