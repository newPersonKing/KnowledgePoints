package com.gy.commonviewdemo.viewpager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_viewpager.*


// ViewPager中Fragment 的生命周期 跟 网上搜的图不一致
class ViewPagerActivity : AppCompatActivity(){

    override fun onStart() {
        super.onStart()
        Log.i("Life","ViewPagerActivity:onStart")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager)

        val adapter = ViewPagerFragmentAdapter(supportFragmentManager)
        // setAdapter => requestLayout=> onMeasure => populate
        // 在populate 方法中创建每一个需要展示或者缓存的item ItemInfo 方法名 addNewItem
        viewpager.adapter = adapter
        Log.i("Life","ViewPagerActivity:onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i("Life","ViewPagerActivity:onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i("Life","ViewPagerActivity:onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Life","ViewPagerActivity:onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Life","ViewPagerActivity:onDestroy")
    }


}


class ViewPagerFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager){

    override fun getCount(): Int {

        return 5
    }

    override fun getItem(position: Int): Fragment {
        Log.i("life","ViewPagerFragmentAdapter:getItem$position")
        return MyFragment(position)
    }


}