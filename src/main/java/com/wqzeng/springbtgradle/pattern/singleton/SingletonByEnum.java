package com.wqzeng.springbtgradle.pattern.singleton;

public class SingletonByEnum {
    /**
     * 构造函数私有化，避免外部创建实例
     */
    private SingletonByEnum() {
    }

    public enum SingletonEnum{
        //唯一一个枚举对象
        INSTANCE;
        private SingletonByEnum singleton;
        SingletonEnum(){
//            真正的对象创建隐蔽在此
            singleton=new SingletonByEnum();
        }

        public SingletonByEnum getInstance(){
            return singleton;
        }
    }

    /**
     * 外面获取实例唯一入口
     * @return
     */
    public static SingletonByEnum getInstance(){
//        通过枚举来完成
        return SingletonEnum.INSTANCE.getInstance();
    }
}
