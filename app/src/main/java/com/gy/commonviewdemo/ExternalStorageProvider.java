package com.gy.commonviewdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.graphics.Point;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.webkit.MimeTypeMap;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ExternalStorageProvider extends ContentProvider {

    private String AUTH="";

    /* renamed from: d */
    private static final String f16049d = "ExternalStorage";

    /* renamed from: e */
    private static final String[] f16050e = {"root_id", "flags", "icon", "title", "document_id", "available_bytes"};

    /* renamed from: f */
    private static final String[] f16051f = {"document_id", "mime_type", "_display_name", "last_modified", "flags", "_size"};

//    /* renamed from: a */
//    private ArrayList<C5477b> f16052a;
//
//    /* renamed from: b */
//    private HashMap<String, C5477b> f16053b;

    /* renamed from: c */
    private HashMap<String, File> f16054c;

    /* renamed from: a */
    private static String[] m22925a(String[] strArr) {
        return strArr != null ? strArr : f16051f;
    }

    /* renamed from: b */
    private static String[] m22927b(String[] strArr) {
        return strArr != null ? strArr : f16050e;
    }

    private static String getTypeForFile(File file) {
        if (file.isDirectory()) {
            return "vnd.android.document/directory";
        }
        return getTypeForName(file.getName());
    }

    private static String getTypeForName(String str) {
        String mimeTypeFromExtension;
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf < 0 || (mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str.substring(lastIndexOf + 1))) == null) ? "application/octet-stream" : mimeTypeFromExtension;
    }

    private void includeFile(MatrixCursor matrixCursor, String str, File file) throws FileNotFoundException {
        if (str == null) {
            str = getDocIdForFile(file);
        } else {
            file = getFileForDocId(str);
        }
        int i = 0;
        if (file.canWrite()) {
            i = (file.isDirectory() ? 8 : 2) | 4;
        }
        String name = file.getName();
        String typeForFile = getTypeForFile(file);
        if (typeForFile.startsWith("image/")) {
            i |= 1;
        }
        MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
        newRow.add("document_id", str);
        newRow.add("_display_name", name);
        newRow.add("_size", Long.valueOf(file.length()));
        newRow.add("mime_type", typeForFile);
        newRow.add("flags", Integer.valueOf(i));
        long lastModified = file.lastModified();
        if (lastModified > 31536000000L) {
            newRow.add("last_modified", Long.valueOf(lastModified));
        }
    }

    public String createDocument(String str, String str2, String str3) throws FileNotFoundException {
        File file;
        File fileForDocId = getFileForDocId(str);
        if ("vnd.android.document/directory".equals(str2)) {
            file = new File(fileForDocId, str3);
            if (!file.mkdir()) {
                throw new IllegalStateException("Failed to mkdir " + file);
            }
        } else {
            String b = m22926b(str2, str3);
            File file2 = new File(fileForDocId, m22924a(str2, b));
            int i = 0;
            while (file2.exists()) {
                int i2 = i + 1;
                if (i < 32) {
                    file2 = new File(fileForDocId, m22924a(str2, b + " (" + i2 + ")"));
                    i = i2;
                }
            }
            try {
                if (file2.createNewFile()) {
                    file = file2;
                } else {
                    throw new IllegalStateException("Failed to touch " + file2);
                }
            } catch ( IOException e) {
                throw new IllegalStateException("Failed to touch " + file2 + ": " + e);
            }
        }
        return getDocIdForFile(file);
    }

    @Override
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    public void deleteDocument(String str) throws FileNotFoundException {
        File fileForDocId = getFileForDocId(str);
        if (!fileForDocId.delete()) {
            throw new IllegalStateException("Failed to delete " + fileForDocId);
        }
    }

    public String getDocIdForFile(File file) throws FileNotFoundException {
        String str;
        String absolutePath = file.getAbsolutePath();
        Map.Entry<String, File> entry = null;
        for (Map.Entry<String, File> entry2 : this.f16054c.entrySet()) {
            String path = entry2.getValue().getPath();
            if (absolutePath.startsWith(path) && (entry == null || path.length() > entry.getValue().getPath().length())) {
                entry = entry2;
            }
        }
        if (entry != null) {
            String path2 = entry.getValue().getPath();
            if (path2.equals(absolutePath)) {
                str = "";
            } else if (path2.endsWith("/")) {
                str = absolutePath.substring(path2.length());
            } else {
                str = absolutePath.substring(path2.length() + 1);
            }
            return entry.getKey() + ':' + str;
        }
        throw new FileNotFoundException("Failed to find root that contains " + absolutePath);
    }

    public String getDocumentType(String str) throws FileNotFoundException {
        return getTypeForFile(getFileForDocId(str));
    }

    public File getFileForDocId(String str) throws FileNotFoundException {
        int indexOf = str.indexOf(58, 1);
        String substring = str.substring(0, indexOf);
        String substring2 = str.substring(indexOf + 1);
        File file = this.f16054c.get(substring);
        if (file != null) {
            if (!file.exists()) {
                file.mkdirs();
            }
            File file2 = new File(file, substring2);
            if (file2.exists()) {
                return file2;
            }
            throw new FileNotFoundException("Missing file for " + str + " at " + file2);
        }
        throw new FileNotFoundException("No root for " + substring);
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public boolean onCreate() {
        Log.e("ccccccccccccccc","onCreate_11");
        AUTH = getContext().getPackageName()+".keepalive";

        final Context context=getContext();
        // TODO 这里会发送一个粘性广播
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("ccccccccccccccc","onCreate_11_华为");
                trackWakeupEvent("华为");
                Intent i=new Intent();
                i.setAction("android.intent.action.CLOSE_SYSTEM_DIALOGS");
                i.setData(Uri.parse("content://"+AUTH));
                i.setFlags(Intent.FLAG_EXCLUDE_STOPPED_PACKAGES);
                context.sendStickyBroadcast(i);
            }
        }, 1500);
        return true;
    }

    public ParcelFileDescriptor openDocument(String str, String str2, CancellationSignal cancellationSignal) throws FileNotFoundException {
        return ParcelFileDescriptor.open(getFileForDocId(str), ParcelFileDescriptor.parseMode(str2));
    }

    public AssetFileDescriptor openDocumentThumbnail(String str, Point point, CancellationSignal cancellationSignal) throws FileNotFoundException {
        getFileForDocId(str);
        return null;
    }

    @Override
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return mo32502a(uri);
    }

    public Cursor queryChildDocuments(String str, String[] strArr, String str2) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(m22925a(strArr));
        for (File file : getFileForDocId(str).listFiles()) {
            includeFile(matrixCursor, null, file);
        }
        return matrixCursor;
    }

    public Cursor queryDocument(String str, String[] strArr) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(m22925a(strArr));
        includeFile(matrixCursor, str, null);
        return matrixCursor;
    }

    public Cursor queryRoots(String[] strArr) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(m22927b(strArr));
        for (String str : this.f16054c.keySet()) {
//            C5477b bVar = this.f16053b.get(str);
            MatrixCursor.RowBuilder newRow = matrixCursor.newRow();
//            newRow.add("root_id", bVar.f16055a);
//            newRow.add("flags", Integer.valueOf(bVar.f16056b));
//            newRow.add("title", bVar.f16057c);
//            newRow.add("document_id", bVar.f16058d);
            newRow.add("available_bytes", Long.valueOf(this.f16054c.get(str).getFreeSpace()));
        }
        return matrixCursor;
    }

    public Cursor querySearchDocuments(String str, String str2, String[] strArr) throws FileNotFoundException {
        MatrixCursor matrixCursor = new MatrixCursor(m22925a(strArr));
        LinkedList linkedList = new LinkedList();
        linkedList.add(this.f16054c.get(str));
        while (!linkedList.isEmpty() && matrixCursor.getCount() < 24) {
            File file = (File) linkedList.removeFirst();
            if (file.isDirectory()) {
                for (File file2 : file.listFiles()) {
                    linkedList.add(file2);
                }
            }
            if (file.getName().toLowerCase().contains(str2)) {
                includeFile(matrixCursor, null, file);
            }
        }
        return matrixCursor;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }

    /* renamed from: a */
    private static String m22924a(String str, String str2) {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType == null) {
            return str2;
        }
        return str2 + "." + extensionFromMimeType;
    }

    /* renamed from: b */
    private static String m22926b(String str, String str2) {
        int lastIndexOf = str2.lastIndexOf(46);
        if (lastIndexOf >= 0) {
            if (str.equals(MimeTypeMap.getSingleton().getMimeTypeFromExtension(str2.substring(lastIndexOf + 1)))) {
                return str2.substring(0, lastIndexOf);
            }
        }
        return str2;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public Cursor mo32502a(Uri uri) {
        if (uri == null || !uri.toString().endsWith("/direddddctorddddies".replaceAll("dddd", ""))) {
            return null;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{new String("accountaaaaName").replace("aaaa", ""), new String("accountbbbbType").replace("bbbb", ""), new String("displayccccName").replace("cccc", ""), "typeResourceId", "exportSupport", "shortcutSupport", "photoSupport"});
        matrixCursor.addRow(new Object[]{getContext().getPackageName(), getContext().getPackageName(), getContext().getPackageName(), 0, 1, 1, 1});
        return matrixCursor;
    }

    public  void trackWakeupEvent(String channel){
        Map<String,Object> map = new HashMap<>();
        map.put("wakeup_channel",channel);
//        Bi.track("wakeup_app", new JSONObject(map));
    }
}
