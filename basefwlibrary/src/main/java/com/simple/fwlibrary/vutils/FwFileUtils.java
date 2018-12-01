package com.simple.fwlibrary.vutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * FileUtils
 */
public class FwFileUtils {
    /**
     * Write object to file
     *  http://www.cnblogs.com/hrlnw/p/3617478.html
     * @param path
     * @param obj
     */
    public static void writeObjectToFile(String path,Object obj)
    {
        File file =new File(path);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            ObjectOutputStream objOut=new ObjectOutputStream(out);
            objOut.writeObject(obj);
            objOut.flush();
            objOut.close();
            FwLog.d("write object success!");
        } catch (IOException e) {
            FwLog.e("write object failed");
            e.printStackTrace();
        }
    }

    /**
     * Read object from file
     * @param path
     * @return
     */
    public static Object readObjectFromFile(String path)
    {
        Object temp=null;
        File file =new File(path);
        if(!file.exists()){
            return null;
        }
        FileInputStream in;
        try {
            in = new FileInputStream(file);
            ObjectInputStream objIn=new ObjectInputStream(in);
            temp=objIn.readObject();
            objIn.close();
            FwLog.d("read object success!");
        } catch (Exception e) {
            FwLog.e("read object failed");
            e.printStackTrace();
        }
        return temp;
    }
}
