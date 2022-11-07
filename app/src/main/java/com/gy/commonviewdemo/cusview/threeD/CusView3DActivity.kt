package com.gy.commonviewdemo.cusview.threeD

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.cus_view_3d.*

class CusView3DActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cus_view_3d)

        vp_3d.setPageTransformer(false,BliPageTransformer())
        vp_3d.adapter = ViewPagerAdapter(supportFragmentManager)


        rotate_view.setOnClickListener {
            rotate_view.startAnimation()
        }

    }


     class ViewPagerAdapter(manager:FragmentManager) : FragmentPagerAdapter(manager) {

         override fun getCount(): Int = 5

         override fun getItem(position: Int): Fragment {
             return ThreeDFragment(position)
         }

     }

}