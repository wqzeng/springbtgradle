package com.wqzeng.springbtgradle.pattern.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 原型模式：通过深拷贝实现，spring bean就是默认原型模式 scope="prototype"
 * 1.重写clone方法来实现深拷贝；
 * 2.通过对象序列化实现深拷贝
 */
public abstract class Shape implements Cloneable, Serializable {
    private String id;
    protected String type;
    private Shape friend;//引用类型

    abstract void draw();

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Shape getFriend() {
        return friend;
    }

    public void setFriend(Shape friend) {
        this.friend = friend;
    }

    public Object clone() {
        Object clone;
        Shape cloneShape = null;
        try {
            clone = super.clone();
            Shape friend = (Shape) this.friend.clone();
            cloneShape = (Shape) clone;
            cloneShape.friend = friend;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return cloneShape;
    }

    public Shape clone1() throws IOException {
        Shape cloneShape = null;
        try (
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
                ObjectInputStream ois = new ObjectInputStream(bis);
        ){
            oos.writeObject(this);
            cloneShape =(Shape) ois.readObject();
        } catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return cloneShape;
    }
}