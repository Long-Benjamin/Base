package com.ljt.base.base.mvp

import android.support.v4.app.Fragment
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent

interface FView :IView, LifecycleProvider<FragmentEvent> {

    fun getFragment(): Fragment

}