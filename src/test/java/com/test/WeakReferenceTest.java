package com.test;

import java.lang.ref.WeakReference;

import com.jarvis.cache.serializer.HessianSerializer;
import com.jarvis.cache.serializer.ISerializer;
import com.jarvis.lib.util.BeanUtil;

public class WeakReferenceTest {

    private static final ISerializer<Object> serializer=new HessianSerializer();

    public static void main(String[] args) {
        Simple s=new Simple();
        s.setName("tt");
        s.setAge(10);
        s.setSex(1);
        WeakReference<Simple> ref=new WeakReference<Simple>(s);
        s=null;
        try {
            WeakReference<Simple> ref2=(WeakReference<Simple>)BeanUtil.deepClone(ref, serializer);
            System.out.println(ref2.get());
        } catch(Exception e1) {
            e1.printStackTrace();
        }
        int i=0;
        while(ref.get() != null) {
            System.out.println(String.format("Get str from object of WeakReference: %s, count: %d", ref.get(), ++i));
            if(i % 10 == 0) {
                System.gc();
                System.out.println("System.gc() was invoked!");
            }
            try {
                Thread.sleep(500);
            } catch(InterruptedException e) {

            }
        }
        System.out.println("object a was cleared by JVM!");
        try {
            WeakReference<Simple> ref2=(WeakReference<Simple>)BeanUtil.deepClone(ref, serializer);
            System.out.println(ref2.get());
        } catch(Exception e1) {
            e1.printStackTrace();
        }
    }

}
