package com.ljt.base.base.bean

data class UserInfo(var accountId: String,
                    var api_token: String,
                    var linkmanName: String,
                    var linkmanPhone: String,
                    var loginName: String,
                    var passwordErrorTimes: Int,
                    var session_token: String)