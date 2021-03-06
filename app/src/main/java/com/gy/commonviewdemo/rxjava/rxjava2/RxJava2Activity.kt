package com.gy.commonviewdemo.rxjava.rxjava2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gy.commonviewdemo.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_rxjava2.*
import java.util.concurrent.TimeUnit

class RxJava2Activity : AppCompatActivity(),View.OnClickListener{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rxjava2)

        btn_create.setOnClickListener(this)
        btn_just.setOnClickListener(this)
        btn_timer.setOnClickListener(this)
        btn_interval.setOnClickListener(this)
        btn_interval_range.setOnClickListener(this)
        btn_range.setOnClickListener(this)
        btn_map.setOnClickListener(this)
        btn_flat_map.setOnClickListener(this)
        btn_concat_map.setOnClickListener(this)
        btn_buffer.setOnClickListener(this)
        btn_scan.setOnClickListener(this)
        btn_window.setOnClickListener(this)
        btn_concat.setOnClickListener(this)
        btn_concat_array.setOnClickListener(this)
        btn_merge.setOnClickListener(this)
        btn_zip.setOnClickListener(this)
        btn_start_with.setOnClickListener(this)
        btn_start_with_array.setOnClickListener(this)
        btn_count.setOnClickListener(this)
        btn_delay.setOnClickListener(this)
        btn_retry.setOnClickListener(this)
        btn_retry_when.setOnClickListener(this)
        btn_retry_until.setOnClickListener(this)
        btn_repeat.setOnClickListener(this)
        btn_of_type.setOnClickListener(this)
        btn_skip.setOnClickListener(this)
        btn_distinct.setOnClickListener(this)
        btn_take.setOnClickListener(this)
        btn_debounce.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.btn_create -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onComplete()
                }.subscribe {
                    Log.i("subscribe_create",it)
                }
            }
            R.id.btn_just -> {
                Observable.just("1","2","3").subscribe {
                    Log.i("subscribe_just","$it")
                }
            }
            R.id.btn_timer -> {
                // ???????????????????????? ??????
                Observable.timer(2000,TimeUnit.MILLISECONDS).subscribe {
                    Log.i("subscribe_timer",it.toString())
                }
            }
            R.id.btn_interval -> {
                Observable.interval(1000,TimeUnit.MILLISECONDS).subscribe {
                    // ??????????????????
                    Log.i("subscribe_interval","Thread:"+Thread.currentThread().name)
                    Log.i("subscribe_interval","value:$it")
                }
            }
            R.id.btn_interval_range -> {
                // ???????????? ??????????????????????????????????????????????????????????????????????????????????????????
                Observable.intervalRange(100,5,2000,2000,TimeUnit.MILLISECONDS).subscribe {
                    // ????????????
                    Log.i("subscribe_intervalRange","Thread:"+Thread.currentThread().name)
                    Log.i("subscribe_intervalRange","value:$it")
                }
            }
            R.id.btn_range -> {
                Observable.range(0,5).subscribe {
                    // ?????????
                    Log.i("subscribe_range","Thread:"+Thread.currentThread().name)
                    Log.i("subscribe_range","value:$it")
                }
            }
            R.id.btn_map -> {
                Observable.just("??????","??????").map {
                    "??????$it"
                }.subscribe {
                    // ???????????????
                    Log.i("subscribe_map","Thread:"+Thread.currentThread().name)
                    Log.i("subscribe_map","value:$it")
                }
            }
            R.id.btn_flat_map -> {
                // todo flatMap???????????? ???????????????????????????????????????
                Observable.just("??????","??????","??????","??????","??????","??????").flatMap { value->
                    Observable.create<String> {
                        Thread.sleep(2000)
                        it.onNext("$value")
                    }
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        // ???????????????
                        Log.i("subscribe_flat_map","Thread:"+Thread.currentThread().name)
                        Log.i("subscribe_flat_map","value:$it")
                    }
            }
            R.id.btn_concat_map -> {
                // todo concatMap ???????????? ???????????????????????????
                Observable.just("??????","??????","??????","??????","??????","??????").concatMap { value->
                    Observable.create<String> {
                        Thread.sleep(2000)
                        it.onNext("$value")
                        // ?????????????????? onComplete
                        it.onComplete()
                    }
                }.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        // ???????????????
                        Log.i("subscribe_concat_map","Thread:"+Thread.currentThread().name)
                        Log.i("subscribe_concat_map","value:$it")
                    }
            }
            R.id.btn_buffer -> {
                // skip ???????????????????????????????????????
                Observable.range(0,10)
                    .buffer(3,1)
                    .subscribe {
                        Log.i("subscribe_buffer","Thread:"+Thread.currentThread().name)
                        Log.i("subscribe_buffer","value:$it")
                    }
            }
            R.id.btn_scan -> {
                Observable.just("??????","??????","??????")
                    .scan { t1, t2 ->
                        "$t1:$t2"
                    }.subscribe {
                        Log.i("subscribe_scan","value:$it")
                    }
            }
            R.id.btn_window -> {

                Observable.just("??????", "?????????", "??????","??????","??????")
                    .window(2)
                    .subscribe {
                        Log.i("subscribe_window","????????????")
                        it.subscribe {
                            Log.i("subscribe_window","value:$it")
                        }
                    }
            }
            R.id.btn_concat -> {
                // concat() ?????????????????????4?????????
                Observable.concat(Observable.just(1, 2, 3),
                    Observable.just(4, 5),
                    Observable.just(6, 7),
                    Observable.just(8, 9))
                    .subscribe {
                        Log.i("subscribe_concat","value:$it")
                    }
            }
            R.id.btn_concat_array -> {
                Observable.concatArray(Observable.just(1, 2, 3, 4),
                    Observable.just(5, 6),
                    Observable.just(7, 8, 9, 10),
                    Observable.just(11, 12, 13),
                    Observable.just(14, 15),
                    Observable.just(16))
                    .subscribe {
                        Log.i("subscribe_concat_array","value:$it")
                    }
            }
            R.id.btn_merge -> {
                // merge ????????? ???????????????????????????
                Observable.merge(Observable.create {
                    Thread.sleep(2000)
                    it.onNext(1)
                    Thread.sleep(2000)
                    it.onNext(2)
                    it.onComplete()
                },
                    Observable.just(5, 6),
                    Observable.just(7, 8, 9, 10),
                    Observable.just(11, 12, 13))
                    .subscribe {
                        Log.i("subscribe_merge","value:$it")
                    }
            }
            R.id.btn_zip -> {

                Observable.zip(Observable.just(1, 2, 3),Observable.just("A", "B", "C", "D", "E"),{a,b->

                    "${a}_$b"
                }).subscribe {
                    Log.i("subscribe_zip","value:$it")
                }

            }
            R.id.btn_start_with -> {
                Observable.just(1, 2, 3)
                    .startWith {
                        it.onNext(10)
                        it.onComplete()
                    }.subscribe {
                        Log.i("subscribe_start_with","value:$it")
                    }
            }
            R.id.btn_start_with_array -> {
                Observable.just(1, 2, 3)
                    .startWithArray(11,12,13).subscribe {
                        Log.i("subscribe_start_with_a","value:$it")
                    }
            }
            R.id.btn_count -> {
                Observable.just(1, 2, 3)
                    .startWithArray(11,12,13)
                    .count()
                    .subscribe { count ->
                        Log.i("subscribe_count","value:$count")
                    }
            }
            R.id.btn_delay -> {
                Observable.just("100")
                    .delay(1000,TimeUnit.MILLISECONDS)
                    .subscribe {
                        Log.i("subscribe_delay","value:$it")
                    }
            }
            R.id.btn_retry -> {
                // ??????????????? ????????????????????? retry ?????? ???????????????????????????
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onError(Throwable("???????????????"))
                }.retry(3).subscribe (
                    {
                        Log.i("subscribe_retry","value:$it")
                    },
                    {
                        Log.i("subscribe_retry_error","value:${it.message}")
                    }
                )
            }
            R.id.btn_retry_when -> {
                var retryCount = 0
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onError(Throwable("???????????????"))
                }.retryWhen { error->
                    error.flatMap {
                        if(retryCount > 3){
                            Observable.error<Any>(it)
                        }else {
                            retryCount++
                            Observable.timer(3,TimeUnit.SECONDS)
                        }

                    }
                }.subscribe {
                    Log.i("subscribe_retry_when","value:$it")
                }
            }
            R.id.btn_retry_until -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onError(Throwable("???????????????"))
                }.retryUntil {
                    // ??????true ????????????
                    return@retryUntil true
                }.subscribe {
                    Log.i("subscribe_retry_when","value:$it")
                }
            }
            R.id.btn_repeat -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onComplete()
                }.delay(3,TimeUnit.SECONDS).repeat()
                    .subscribe {
                        Log.i("subscribe_repeat","value:$it")
                    }
            }
            R.id.btn_of_type -> {
                Observable.create<Any> {
                    it.onNext("???????????????")
                    it.onNext(1)
                    it.onNext("???????????????")
                    it.onComplete()
                }.ofType(String::class.java)
                    .subscribe {
                        Log.i("subscribe_of_type","value:$it")
                    }
            }
            R.id.btn_skip -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("12312")
                    Thread.sleep(1000)
                    it.onNext("???????????????")
                    it.onComplete()
                }
                    .skip(1,TimeUnit.SECONDS) //?????????????????????????????????2
//                    .skip(2) // ??????????????????
                    .subscribe {
                        Log.i("subscribe_skip","value:$it")
                    }
            }
            R.id.btn_distinct -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("2???????????????")
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onNext("4???12312")
                    it.onComplete()
                }
//                    .distinct()
//                    .distinct {
//                      it.first()  // ???????????????????????????
//                    }
                    .distinctUntilChanged() // ??????????????????????????????
                    .subscribe {
                        Log.i("subscribe_distinct","value:$it")
                    }
            }
            R.id.btn_take -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("2???????????????")
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onNext("4???12312")
                    Thread.sleep(2000)
                    it.onNext("55555")
                    it.onComplete()
                }
//                    .take(2) //??????????????????
//                    .takeLast(1,TimeUnit.SECONDS) // ????????????????????????
                    .take(1,TimeUnit.SECONDS) //?????????????????????
                    .subscribe {
                        Log.i("subscribe_take","value:$it")
                    }
            }
            R.id.btn_debounce -> {
                Observable.create<String> {
                    it.onNext("???????????????")
                    it.onNext("2???????????????")
                    it.onNext("???????????????")
                    it.onNext("???????????????")
                    it.onNext("4???12312")
                    Thread.sleep(2000)
                    it.onNext("55555")
                    it.onComplete()
                }
                    .debounce(2,TimeUnit.SECONDS) //2???????????????????????????
                    .subscribe {
                        Log.i("subscribe_take","value:$it")
                    }
            }

        }
    }

}