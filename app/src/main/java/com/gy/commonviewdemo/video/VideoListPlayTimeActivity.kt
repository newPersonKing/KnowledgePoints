package com.gy.commonviewdemo.video

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gy.commonviewdemo.R
import kotlinx.android.synthetic.main.activity_video_list.*

// https://juejin.cn/post/6844904081182425101
// 列表视频 播放时机
class VideoListPlayTimeActivity  : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_list)

        rv_video_list.layoutManager = LinearLayoutManager(this)
        rv_video_list.adapter = VideoListAdapter()

        rv_video_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when(newState){
                    RecyclerView.SCROLL_STATE_IDLE ->{
                        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                        val firstPosition = layoutManager.findFirstVisibleItemPosition()
                        val lastPosition  = layoutManager.findLastVisibleItemPosition()
                        for ( i in firstPosition .. lastPosition){
                           val itemView = layoutManager.findViewByPosition(i)
                           if(itemView?.findViewById<AppCompatImageView>(R.id.img_video) != null){
                               isCanBePlayedByRect(itemView.findViewById<AppCompatImageView>(R.id.img_video))
                           }
                        }
                    }
                }
            }

            //定义个阈值 停止播放判定使用
            private val STOP_FLAG_PX_VALUE = 5
            private val MAIN_PAGE_BOTTOM_TAB_RECT_TOP = 50
            private var mRvInitFlag = false
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (!mRvInitFlag) {
                    // 第一次初始化数据回调
//                    playFirstVideoView();
                    mRvInitFlag = true;
                    return;
                }

                var position = 0
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if(dy > 0){
                    position = layoutManager.findFirstVisibleItemPosition()
                }else {
                    position = layoutManager.findLastVisibleItemPosition()
                }

                val itemView= layoutManager.findViewByPosition(position)

                if(itemView!=null){
                    val videoView = itemView.findViewById<View>(R.id.img_video)
                    val rect = Rect();
                    val gRect =  Rect();
                    videoView.getLocalVisibleRect(rect);
                    videoView.getGlobalVisibleRect(gRect);
                    //item 从下往上划出屏幕 的判定条件
                    val stopFlagForDownToUp = (dy > 0 && rect.bottom - rect.top <= STOP_FLAG_PX_VALUE);
                    //item 从上往下划出屏幕的 判定条件 ----有底部tab
                    val  stopFlagForUptoDown1 = (dy < 0 && gRect.top > (MAIN_PAGE_BOTTOM_TAB_RECT_TOP - STOP_FLAG_PX_VALUE));
                    //item 从上往下划出屏幕的 判定条件 ----没有底部tab
                    val stopFlagForUptoDown2 = (dy < 0 && rect.top != 0);
//                    if (stopFlagForDownToUp || stopFlagForUptoDownHasParent || stopFlagForUptoDownNoParent) {
//                        videoPlayer.release();
//                    }

                }

            }

        })
    }

    private fun isCanBePlayedByRect(videoView: View): Boolean {

        var rect = Rect()
        //这里就可以取出来视频播放区域的坐标轴了
        videoView.getLocalVisibleRect(rect);
        val videoHeight = videoView.getHeight();
        //符合这个条件就意味着 整个视频播放区域 都是完整的呈现在视频中的
        return (rect.top == 0 && rect.bottom == videoHeight);

    }

}

class VideoListAdapter : RecyclerView.Adapter<VideoListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoListViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.video_list_item,parent,false)
        return VideoListViewHolder(itemView);
    }

    override fun onBindViewHolder(holder: VideoListViewHolder, position: Int) {
        val indexTv =  holder.itemView.findViewById<TextView>(R.id.index)
        indexTv.text = "第${position}个"
    }

    override fun getItemCount(): Int  = 200

}

class VideoListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {

}


