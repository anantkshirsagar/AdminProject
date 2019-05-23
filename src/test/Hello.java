package test;

public class Hello {
	@MyAnnotation(value=10, myValue="Anant Kshirsagar")
	public void sayHello() {
		System.out.println(" Hello Annotation");
	}
}
