package com.wbl.zxing.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * Created by djtao on 2016/9/6.
 */

public class BitmapUtil {




    public static Bitmap getBitmap(String path){

        try {
            File f=new File(path);
            if(!f.exists()||f.isDirectory()){
                return null;
            }
            BufferedInputStream in=new BufferedInputStream(new FileInputStream(
                    new File(path)));

            BitmapFactory.Options options=new BitmapFactory.Options();
            //如果将其设为true的话，在decode时将会返回null,通过此设置可以去查询一个bitmap的属性，比如bitmap的长与宽，而不占用内存大小。
            options.inJustDecodeBounds=true;
            //通过设置此值可以用来降低内存消耗，默认为ARGB_8888: 每个像素4字节. 共32位。
            //如果不需要透明度，可把默认值ARGB_8888改为RGB_565,节约一半内存。
            options.inPreferredConfig=Bitmap.Config.RGB_565;
            options.inSampleSize=4;
            BitmapFactory.decodeStream(in, null, options);


            in=new BufferedInputStream(new FileInputStream(new File(path)));
            options.inJustDecodeBounds=false;
            Bitmap bt=BitmapFactory.decodeStream(in,null,options);
            in.close();
            return bt;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }



    }
}
