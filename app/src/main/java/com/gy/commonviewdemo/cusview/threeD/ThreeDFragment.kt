package com.gy.commonviewdemo.cusview.threeD

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gy.commonviewdemo.R


// 知识点
// 前置条件：在ViewPager中 viewPager 是一个ViewGroup
// 1 当Fragment 被销毁会 只会执行到onDestroyView
// 2 当Fragment 再次被创建的时候，会复用Fragment的实例,生命周期方法直接从onActivityCreated 开始
class ThreeDFragment(var tag:Int) : Fragment(){

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i("Life","MyFragment:$tag,onAttach")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("Life","MyFragment:$tag,onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.three_d_viewpager_item,container,false)
        val tvContent = view.findViewById<TextView>(R.id.tv_content)
        tvContent.text = "这是$tag 个"
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("Life","MyFragment:$tag,onActivityCreated")
    }

    override fun onStart() {
        super.onStart()
        Log.i("Life","MyFragment:$tag,onStart")
    }

    // onResume 在Fragment 被实例化的时候 就会跟着Activity的生命周期 执行对应的方法
    override fun onResume() {
        super.onResume()
        Log.i("Life","MyFragment:$tag,onResume")
        // 在viewPager中 只有在页面可见的情况下 onResume 和 onPause 才是真正的Fragment可见与否

        // 只有页面可见的时候 才能统计
        if(isStartStatistic){

        }
    }

    override fun onPause() {
        super.onPause()
        Log.i("Life","MyFragment:$tag,onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.i("Life","MyFragment:$tag,onStop")
    }

    // 在ViewPager中滑动 销毁时 只会调用到 onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("Life","MyFragment:$tag,onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("Life","MyFragment:$tag,onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Log.i("Life","MyFragment:$tag,onDetach")
    }

    // 1 重新创建
    // 2 缓存中取
    // 3 切换页面
    var isStartStatistic = false
    override fun setMenuVisibility(menuVisible: Boolean) {
        super.setMenuVisibility(menuVisible)
        // setMenuVisibility 这个方法 在页面切换的时候 adapter 会主动调用这个方法 menuVisible 代表当前Fragment 是否是选中的
        isStartStatistic = menuVisible
        Log.i("life","setMenuVisibility$tag===$menuVisible")
    }


}