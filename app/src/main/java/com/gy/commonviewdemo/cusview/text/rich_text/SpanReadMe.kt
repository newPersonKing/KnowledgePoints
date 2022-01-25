package com.gy.commonviewdemo.cusview.text.rich_text

//Span的Flag有下面这些枚举：
//Spanned.SPAN_EXCLUSIVE_EXCLUSIVE：不包含两端start和end所在的端点，即(a,b)
//Spanned.SPAN_EXCLUSIVE_INCLUSIVE：不包含端start，但包含end所在的端点，即(a,b]
//Spanned.SPAN_INCLUSIVE_EXCLUSIVE：包含两端start，但不包含end所在的端点，即[a,b)
//Spanned.SPAN_INCLUSIVE_INCLUSIVE：包含两端start和end所在的端点，即[a,b]

//Span常用方法
//Span中有很多函数用来帮助开发者获取Span相关的状态和数据。
//
//getSpanStart(Object tag)，用来获取一个span开始的位置。
//
//getSpanEnd(Object tag)，用来获取一个span的结束位置。
//
//getSpanFlags(Object tag)用来获取这个span设置的flag。
//
//getSpans(int start, int end, Class type)，用来获取从start到end的位置上所有的特定类型的span，这个方法非常有用，它可以获取Text中，指定类型的Span，这在很多场景下都是非常有用的。
//
//removeSpan(span)，这个方法用来移除指定的Span实例，可以用这个方法来移除Span的展示效果。
//
//nextSpanTransition(int start, int limit, Class type)，这个方法会在你指定的文本范围内，返回下一个你指定的Span类型的开始位置， 与getSpan方法类似，但
// 属于迭代器类型，通过这个方法，我们可以遍历指定的Span类型 ，而且它的效率要比getSpan方法高。

//TextUtils.dumpSpans，这个方法可以将当前Text下的所有Span实例给dump出来，在调试的时候非常有用。
