package com.wqzeng.test;

import com.wqzeng.springbtgradle.model.dto.Animal;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 可序列化test
 * 静态变量不参与序列化
 */
public class AnimalSerializableTest {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    Animal animal=new Animal("hello kitty",2,"kitty");
    @Test
    public void testSerializable() {
        FileOutputStream fileOutputStream = null;
        FileInputStream fileInputStream = null;
        try {
            //将obj写入文件
            fileOutputStream = new FileOutputStream("temp");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(animal);
            fileOutputStream.close();
            //通过文件读取obj
            fileInputStream = new FileInputStream("temp");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Animal a2 = (Animal) objectInputStream.readObject();
            fileInputStream.close();
            //打印结果和序列化之前相同
            logger.info("after serializable:{}",a2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}