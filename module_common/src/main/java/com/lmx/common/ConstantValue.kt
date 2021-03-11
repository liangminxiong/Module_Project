package com.lmx.common

/**
 * Created by lmx on 2021/1/20
 * Describe:
 */
object ConstantValue {

    /**
     * 域名
     */
    const val URL_BASE = "https://www.wanandroid.com"

    /**
     * banner的url
     */
    const val URL_BANNER = "/banner/json"

    /**
     * article的url
     */
    const val URL_ARTICLE = "/article/list/{page}/json"

    /**
     * 体系的url
     */
    const val URL_TREE = "/tree/json"

    /**
     * 体系文章的url
     */
    const val URL_TREE_ARTICLE = "/article/list/{page}/json"

    /**
     * 项目分类的url
     */
    const val URL_PROJECT_CATEGORY = "/project/tree/json"

    /**
     * 项目文章的url
     */
    const val URL_PROJECT_ARTICLE = "/project/list/{page}/json"

    /**
     * 公众号的url
     */
    const val URL_WX = "/wxarticle/chapters/json"

    /**
     * 公众号文章的url
     */
    const val URL_WX_ARTICLE = "/wxarticle/list/{authorId}/{page}/json"

    /**
     * 注册的url
     */
    const val URL_REGISTER = "/user/register"

    /**
     * 登录的url
     */
    const val URL_LOGIN = "/user/login"

    /**
     * 退出登录的url
     */
    const val URL_LOGOUT = "/user/logout/json"

    /**
     * 收藏文章的url
     */
    const val URL_COLLECT = "/lg/collect/{id}/json"

    /**
     * 取消收藏文章的url
     */
    const val URL_UNCOLLECT = "/lg/uncollect_originId/{id}/json"

    /**
     * 收藏文章列表的url
     */
    const val URL_COLLECT_LIST = "/lg/collect/list/{page}/json"

    /**
     * 搜索热词的url
     */
    const val URL_HOT_KEY = "/hotkey/json"

    /**
     * 搜索的url
     */
    const val URL_SEARCH = "/article/query/{page}/json"

    /**
     * 反馈地址
     */
    const val URL_FEEDBACK = "https://github.com/milovetingting/WanAndroid/issues"

    /**
     * 每页数量
     */
    const val PAGE_SIZE = 20

    /**
     * key-link
     */
    const val KEY_LINK = "link"

    /**
     * key-title
     */
    const val KEY_TITLE = "title"

    /**
     * key-keyword
     */
    const val KEY_KEYOWRD = "keyword"

    /**
     * 夜间模式
     */
    const val KEY_NIGHT_MODE = "night_mode"

    /**
     * key-user
     */
    const val KEY_USER = "user"

    /**
     * 设置文件的保存名称
     */
    const val CONFIG_SETTINGS = "settings"

    /**
     * Cookie文件的保存名称
     */
    const val CONFIG_COOKIE = "cookie"

    /**
     * 搜索结果正则表达式
     */
    const val REGEX = "<em class='highlight'>(.+)</em>"

    /**
     * 首页Fragment的路由
     */
    const val ROUTE_HOME = "/home/home"

    /**
     * 体系一级分类的路由
     */
    const val ROUTE_TREE_ROOT = "/tree/root"

    /**
     * 体系二级分类的路由
     */
    const val ROUTE_TREE_CHILD = "/tree/child"

    /**
     * 项目的路由
     */
    const val ROUTE_PROJECT = "/project/root"

    /**
     * 公众号的路由
     */
    const val ROUTE_WX = "/wx/root"

    /**
     * 用户的路由
     */
    const val ROUTE_USER = "/user/root"

    /**
     * 登录的路由
     */
    const val ROUTE_LOGIN = "/user/login"

    /**
     * 文章的路由
     */
    const val ROUTE_ARTICLE = "/article/view"

    /**
     * 搜索的路由
     */
    const val ROUTE_SEARCH = "/search/search"
}