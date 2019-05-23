package test;

import java.lang.reflect.Method;

public class TestHello {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Hello h = new Hello();
		h.sayHello();
		Method method = h.getClass().getMethod("sayHello");
		MyAnnotation my = method.getAnnotation(MyAnnotation.class);
		System.out.println(my.value() + " => " +my.myValue());
		
	}
}
