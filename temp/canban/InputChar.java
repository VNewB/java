import java.io.*;
class InputChar { 
	public static void main(String args[]){ 
	char ch = ‘’;
	try{

		ch = (char) System.in.read();

	}
	catch(Exception e){

		System.out.println(“Nhập lỗi!”);

	}
		System.out.println(“Ky tu vua nhap:” + ch);
	}
}
