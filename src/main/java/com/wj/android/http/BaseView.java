package com.wj.android.http;

/**
 * 作者：wangwnejie on 2017/9/21 20:51
 * 邮箱：wang20080990@163.com
 */

public interface BaseView {

    void start(int requestId);

    void error(Throwable t, int requestId);

    void end(int requestId);

    boolean isLoadingEnable(int requestId);

}
