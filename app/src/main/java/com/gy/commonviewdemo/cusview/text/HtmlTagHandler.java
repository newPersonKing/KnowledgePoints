
package com.gy.commonviewdemo.cusview.text;

import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;

import org.xml.sax.Attributes;


public class HtmlTagHandler implements WrapperTagHandler {


    /*拦截 自定义处理方式*/
    @Override
    public boolean handleTag(boolean opening, String tag, Editable output, Attributes attributes) {
//        if (opening) {
//
//            if(tag.equals("p")){
//                startBlockElement(output,attributes,1);
//            }else{
//                return false;
//            }
//
//        } else {
//            // closing tag
//            if(tag.equals("p")){
//                endBlockElement(output);
//            }else{
//                return false;
//            }
//
//        }

        return false;
    }

    /*p 标签 暂时不处理样式 */
    private static void startBlockElement(Editable text, Attributes attributes, int margin) {
        final int len = text.length();
        if (margin > 0) {
            appendNewlines(text, margin);
            start(text, new Newline(margin));
        }
    }

    private static void endBlockElement(Editable text) {
        Newline n = getLast(text, Newline.class);
        if (n != null) {
            appendNewlines(text, n.mNumNewlines);
            text.removeSpan(n);
        }
    }

    private static <T> T getLast(Spanned text, Class<T> kind) {
        /*
         * This knows that the last returned object from getSpans()
         * will be the most recently added.
         */
        T[] objs = text.getSpans(0, text.length(), kind);

        if (objs.length == 0) {
            return null;
        } else {
            return objs[objs.length - 1];
        }
    }

    private static void appendNewlines(Editable text, int minNewline) {
        final int len = text.length();

        if (len == 0) {
            return;
        }

        int existingNewlines = 0;
        for (int i = len - 1; i >= 0 && text.charAt(i) == '\n'; i--) {
            existingNewlines++;
        }

        for (int j = existingNewlines; j < minNewline; j++) {
            text.append("\n");
        }
    }

    private static void start(Editable text, Object mark) {
        int len = text.length();
        text.setSpan(mark, len, len, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private static class Newline {

        private int mNumNewlines;

        public Newline(int numNewlines) {
            mNumNewlines = numNewlines;
        }
    }



    /*https://blog.csdn.net/qq_36009027/article/details/84371825*/
}
