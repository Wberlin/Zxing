package com.wbl.library.decodeImage;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.wbl.library.R;
import com.wbl.library.image.RGBLuminanceSource;

import java.util.Vector;

/**
 * Created by djtao on 2016/9/6.
 */

public class ImageDecodeManager {
    public interface onDecodeFinishListener{
        //解码成功
        void success(Result rawResult);
        //解码失败
        void fail();
    }
    private final DecodeImageThread decodeImageThread;
    public onDecodeFinishListener listener;
    private Bitmap bt=null;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private State state;

    private enum State{
        PREVIEW,
        SUCCESS,
        DONE
    }
    //执行解码工作
    public void executeDecode(onDecodeFinishListener listener){
        restartDecodeImage();
        this.listener=listener;
    }

   public ImageDecodeManager(){

       //启动一个DecodeImageThread：用于解析二维码的子线程
       decodeImageThread=new DecodeImageThread(this,decodeFormats,characterSet);
       decodeImageThread.start();
       state=State.SUCCESS;

   }

    public RGBLuminanceSource buildRGBLuminanceSource(Bitmap bt){
            return new RGBLuminanceSource(bt);

    }
    public static ImageDecodeManager build(){
        return new ImageDecodeManager();
    }

    public void decodeImage(Handler handler,int message){
        if(handler!=null){
            Message msg=handler.obtainMessage(message,bt);
            msg.sendToTarget();
            handler=null;
        }
    }
    //public
    public ImageDecodeManager setSourceImage(Bitmap bt){
        this.bt=bt;
        return this;
    }
    public ImageDecodeManager setDecodeFormat(Vector<BarcodeFormat> decodeFormats){
        this.decodeFormats=decodeFormats;
        return this;
    }
    public ImageDecodeManager setCharacterSet(String characterSet){
        this.characterSet=characterSet;
        return this;
    }

    private void restartDecodeImage() {
        if(state== State.SUCCESS){
            state= State.PREVIEW;
            decodeImage(decodeImageThread.getHandler(), R.id.decode);
        }
    }

}
