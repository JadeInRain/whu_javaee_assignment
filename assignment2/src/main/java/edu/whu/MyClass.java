package edu.whu;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class MyClass {
    String s;
    public static MyClass createAnObject(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class<?> myclass = Class.forName(className);
        return (MyClass) myclass.newInstance();
    }
    @initMethod
    public void init(){
        Method[] methods = myclass.class.getDeclaredMethods();
        for(Method method:methods){
            if(method.isAnnotationPresent(initMethod.class)){
                initMethod annotation=method.getAnnotation(initMethod.class);
                String[] value=annotation.value();
                this.s=Integer.parseInt(value[0]);
            }
        }
    }
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MyClass s=MyClass.createAnObject(className);
        s.init();
        System.out.println(s);

    }
}
