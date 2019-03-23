package cn.com.iyin.utils

import java.util.regex.Pattern

/**
 * @author longj
 * @date 2018/12/17.
 * @E-mail: longjintang123@163.com
 */
object ValidatorUtil {
    /**
     * 正则表达式：验证用户名
     */
    val REGEX_USERNAME = "^[a-zA-Z]\\w{5,20}$"

    /**
     * 正则表达式：验证密码
     */
    val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,20}$"

    /**
     * 正则表达式：验证手机号
     */
    val REGEX_MOBILE = "^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}\$"

    /**
     * 正则表达式：验证邮箱
     */
    val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 正则表达式：验证汉字
     */
    val REGEX_CHINESE = "^[\u4e00-\u9fa5],{0,}$"

    /**
     * 正则表达式：验证同时包含字母和数字
     */
    val REGEX_LETTER_NUMBER = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,20}\$"

    /**
     * 正则表达式：验证身份证
     */
    val REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)"

    /**
     * 正则表达式：验证URL
     */
    val REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"

    /**
     * 正则表达式：验证IP地址
     */
    val REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)"

    /**
     * 校验用户名
     *
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    fun isUsername(username: String): Boolean {
        return Pattern.matches(REGEX_USERNAME, username)
    }

    /**
     * 校验密码
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    fun isPassword(password: String): Boolean {
        return Pattern.matches(REGEX_PASSWORD, password)
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, mobile)
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    fun isEmail(email: String): Boolean {
        return Pattern.matches(REGEX_EMAIL, email)
    }

    /**
     * 校验汉字
     *
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    fun isChinese(chinese: String): Boolean {
        return Pattern.matches(REGEX_CHINESE, chinese)
    }

    /**
     * 验证同时包含汉字和数字
     *
     * @param string
     * @return 校验通过返回true，否则返回false
     */
    fun isLetterAndNumber(string: String): Boolean {
        return Pattern.matches(REGEX_LETTER_NUMBER, string)
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    fun isIDCard(idCard: String): Boolean {
        return Pattern.matches(REGEX_ID_CARD, idCard)
    }

    /**
     * 校验URL
     *
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    fun isUrl(url: String): Boolean {
        return Pattern.matches(REGEX_URL, url)
    }

    /**
     * 校验IP地址
     *
     * @param ipAddr
     * @return
     */
    fun isIPAddr(ipAddr: String): Boolean {
        return Pattern.matches(REGEX_IP_ADDR, ipAddr)
    }

}
