package com.jctl.cloud.test;

public class Test {

	public static void main(String[] args) {
		Student student = new Student();
		String xx = Student.get();

	}
}

class Student{
	public static String age ="20";


	public  String getInstens(){
		return age;
	}
	public static String get(){
		return age;
	}
}
