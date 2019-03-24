package com.ljt.base.ui.draw

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver
import android.widget.ImageButton
import butterknife.BindView
import butterknife.ButterKnife
import com.ljt.base.R
import com.ljt.base.base.ui.BaseActivity

class MainDrawActivity : BaseActivity() {

    @BindView(R.id.drawer_layout)
    lateinit var dlMenu: DrawerLayout
    @BindView(R.id.titel_back)
    lateinit var titelBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_draw)
        ButterKnife.bind(this)

        initView()

    }

    private fun initView() {

        titelBack.setOnClickListener {
            dlMenu.openDrawer(Gravity.LEFT)
        }

        dlMenu.addDrawerListener(object : DrawerLayout.DrawerListener{
            override fun onDrawerStateChanged(p0: Int) {

            }

            override fun onDrawerSlide(p0: View, p1: Float) {

            }

            override fun onDrawerClosed(p0: View) {

            }

            override fun onDrawerOpened(p0: View) {

            }

        })

    }




}
