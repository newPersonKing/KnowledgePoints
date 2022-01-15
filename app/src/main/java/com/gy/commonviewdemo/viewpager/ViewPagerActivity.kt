package com.gy.commonviewdemo.viewpager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_viewpager.*

// FragmentPagerAdapter 设置 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 的理解
// 1 如果设置了 这个behavior 那么 在执行instantiateItem 对于不是当前要显示的item 就不会执行setUserVisibleHint（false） 而是执行setMaxLifecycle(fragment, Lifecycle.State.STARTED)
// 2 当一个Fragment 从可见到不可见 执行的方法包括 setMenuVisibility(false) -》setMaxLifecycle(mCurrentPrimaryItem, Lifecycle.State.STARTED)
// 3 当一个Fragment 从不可见到可见 执行的方法包括 setMenuVisibility(true) -》setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
// 4 对于步骤2 初始值setMaxLifecycle 设置的是RESUMED 从RESUMED-》STARTED 会执行onPause
// 5 对于步骤3 相当于STARTED -》 RESUMED 直接执行 onResume


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