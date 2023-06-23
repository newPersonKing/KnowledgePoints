package com.gy.commonviewdemo.viewpager

import android.icu.lang.UCharacter.GraphemeClusterBreak.L
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_viewpager.*
import kotlinx.android.synthetic.main.activity_viewpager.viewpager
import kotlinx.android.synthetic.main.activity_viewpager_update.*

// FragmentPagerAdapter 设置 BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 的理解
// 1 如果设置了 这个behavior 那么 在执行instantiateItem 对于不是当前要显示的item 就不会执行setUserVisibleHint（false） 而是执行setMaxLifecycle(fragment, Lifecycle.State.STARTED)
// 2 当一个Fragment 从可见到不可见 执行的方法包括 setMenuVisibility(false) -》setMaxLifecycle(mCurrentPrimaryItem, Lifecycle.State.STARTED)
// 3 当一个Fragment 从不可见到可见 执行的方法包括 setMenuVisibility(true) -》setMaxLifecycle(fragment, Lifecycle.State.RESUMED)
// 4 对于步骤2 初始值setMaxLifecycle 设置的是RESUMED 从RESUMED-》STARTED 会执行onPause
// 5 对于步骤3 相当于STARTED -》 RESUMED 直接执行 onResume


// ViewPager fragment 刷新
class ViewPagerUpdateActivity : AppCompatActivity(){


    private val mHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewpager_update)

        // setAdapter => requestLayout=> onMeasure => populate
        // 在populate 方法中创建每一个需要展示或者缓存的item ItemInfo 方法名 addNewItem
        val fragments = mutableListOf<MyFragment>().apply {
            add(MyFragment(0))
            add(MyFragment(1))
            add(MyFragment(2))
            add(MyFragment(3))
        }
        viewpager.adapter = ViewPagerFragmentUpdateAdapter(supportFragmentManager,fragments)

        mHandler.postDelayed({

//            viewpager.adapter =  ViewPagerFragmentUpdateAdapter(supportFragmentManager, mutableListOf<MyFragment>().apply {
//                add(MyFragment(0))
//                add(MyFragment(1))
//                add(MyFragment(2))
//                add(MyFragment(3))
//                add(MyFragment(4))
//            })
        },5000)

        click.setOnClickListener {
            //第一种方式直接notifyDataSetChanged
            // 问题 调用 notifyDataSetChanged 最终会调用到getItemPosition ，默认返回 POSITION_UNCHANGED,也就是认为没有改变
            // 尝试改变返回值为 POSITION_NONE ，发现Myfagment 是会销毁重建，但是重建的fragment还是从缓存里面取出的，所以数据还是没刷新

//            fragments.clear()
//            fragments.apply {
//                add(MyFragment(2))
//                add(MyFragment(3))
//                add(MyFragment(4))
//                add(MyFragment(5))
//                add(MyFragment(6))
//            }
//            viewpager.adapter?.notifyDataSetChanged()

            // 第二种方式 移除所有fragment 再次设置
//            fragments.clear()
//            fragments.apply {
//                add(MyFragment(2))
//                add(MyFragment(3))
//                add(MyFragment(4))
//                add(MyFragment(5))
//                add(MyFragment(6))
//            }
//            viewpager.adapter?.let {
//                (it as ViewPagerFragmentUpdateAdapter).clear(viewpager)
//            }
//            viewpager.adapter = ViewPagerFragmentUpdateAdapter(supportFragmentManager,fragments)
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}


class ViewPagerFragmentUpdateAdapter(val fragmentManager: FragmentManager,val fragments:List<MyFragment>) : FragmentPagerAdapter(fragmentManager){

    override fun getCount(): Int {

        return fragments.size
    }

    override fun getItem(position: Int): Fragment {
        Log.i("life","ViewPagerFragmentAdapter:getItem$position")
        return fragments[position]
    }

    override fun getItemPosition(o: Any): Int {
        return POSITION_NONE
    }

    private var mCurTransaction : FragmentTransaction? = null
    fun clear(container:ViewGroup) {
        if (this.mCurTransaction == null) {
            this.mCurTransaction = fragmentManager.beginTransaction();
        }

        for(i in fragments.indices){
            val itemId = getItemId(i)
            val name = makeFragmentName(container.id, itemId);
            val fragment = fragmentManager.findFragmentByTag(name);
            if (fragment != null) {//根据对应的ID，找到fragment，删除
                mCurTransaction?.remove(fragment);
            }
        }
        mCurTransaction?.commitNowAllowingStateLoss();
    }

    /**
     * 等同于FragmentPagerAdapter的makeFragmentName方法，
     */
    private fun  makeFragmentName(viewId:Int,  id:Long):String {
        return "android:switcher:$viewId:$id";
    }
}