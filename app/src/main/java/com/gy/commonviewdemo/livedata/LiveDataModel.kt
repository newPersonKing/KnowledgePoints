package com.gy.commonviewdemo.livedata

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class LiveDataModel : ViewModel() {

    val liveData = object : MutableLiveData<String>(){

        // 在liveData 活跃状态下
        override fun onActive() {
            super.onActive()
        }

        // 在liveData 不活跃状态
        override fun onInactive() {
            super.onInactive()
        }
    }


    // 清空资源 或者通知 其他组件放弃对自己的引用
    override fun onCleared() {
        super.onCleared()
    }



}

/*livedata初始化 获取数据的写法*/

/*第一种*/
// 缺点
// 1 我们在每次旋转时重新加载，失去了与Activity/Fragment生命周期解耦的好处，因为他们必须从onCreate()或其他生命周期方法中调用该方法。
// 2 多了一个触发的方法。
// 3 引入隐含条件，即参数对同一实例总是相同的。loadContacts()和contacts()方法是耦合的。
//class ContactsViewModel(val getContactsUseCase: GetContactsUseCase) : ViewModel() {
//    private val contactsLiveData = MutableLiveData<Contacts>()
//
//    fun loadContacts(parameters: Parameters) {
//        getContactsUseCase.loadContacts(parameters) { contactsLiveData.value = it }
//    }
//
//    fun contacts(): LiveData<Contacts> = contactsLiveData
//}

/*第二种*/
// 缺点
// 1 不可能为加载函数提供参数。
// 2 我们在构造函数中进行工作。
//class ContactsViewModel(val getContactsUseCase: GetContactsUseCase) : ViewModel() {
//    private val contactsLiveData = MutableLiveData<Contacts>()
//
//    init {
//        getContactsUseCase.loadContacts(Parameters()) { contactsLiveData.value = it }
//    }
//
//    fun contacts(): LiveData<Contacts> = contactsLiveData
//}

/*第三种*/
// livedata 在第一次使用的时候 初始化 并且请求数据
// 不能设置参数
/*class ContactsViewModel(val getContactsUseCase: GetContactsUseCase) : ViewModel() {
  private val contactsLiveData by lazy {
    val liveData = MutableLiveData<Contacts>()
    getContactsUseCase.loadContacts(Parameters()) { liveData.value = it }
    return@lazy liveData
  }

  fun contacts(): LiveData<Contacts> = contactsLiveData
}*/

/*第四种*/
/*class ContactsViewModel(val getContactsUseCase: GetContactsUseCase) : ViewModel() {
  private val contactsLiveData: Map<Parameters, LiveData<Contacts>> = lazyMap { parameters ->
    val liveData = MutableLiveData<Contacts>()
    getContactsUseCase.loadContacts(parameters) { liveData.value = it }
    return@lazyMap liveData
  }

  fun contacts(parameters: Parameters): LiveData<Contacts> = contactsLiveData.getValue(parameters)
}*/
/*fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
  val map = mutableMapOf<K, V>()
  return map.withDefault { key ->
    val newValue = initializer(key)
    map[key] = newValue
    return@withDefault newValue
  }
}*/