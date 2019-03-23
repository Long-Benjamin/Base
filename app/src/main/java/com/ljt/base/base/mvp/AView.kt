package com.ljt.base.base.mvp

import android.app.Activity
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent

interface AView : IView,LifecycleProvider<ActivityEvent>{

    fun getActivity(): Activity

}