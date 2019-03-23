package com.ljt.base.base.ui

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.util.*

class FragmentsManager {


    private var FRAGMENT_ID: Int
    private var fragmentManager: FragmentManager
    private var currentFragment: Fragment = Fragment()
    private var fragmentStack = Stack<Fragment>()

    constructor(fragmentActivity: BaseActivity, containerId: Int){
        fragmentManager = fragmentActivity.supportFragmentManager
        FRAGMENT_ID = containerId
    }

    fun replaceFragment(fragment: Fragment) {
        fragmentManager.beginTransaction().replace(FRAGMENT_ID, fragment).commit()
        currentFragment = fragment

    }


    /**
     * @param fragment: 需要显示页面，判断是否已经包含在栈中
     */
    fun <F> addFragment(fragment: F) where F : Fragment {

        if (currentFragment == fragment)return

        if (fragmentStack.contains(fragment)){

            fragmentManager.beginTransaction()
                    .show(fragment)
                    .commit()

        }else{

            fragmentManager.beginTransaction()
                    .add(FRAGMENT_ID, fragment,fragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()

            fragmentStack.add(fragment)

        }

        fragmentManager.beginTransaction()
                .hide(currentFragment)
                .commit()

        currentFragment = fragment

    }


    fun popFragment(){
        fragmentStack.clear()

    }

}