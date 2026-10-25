class StringArray
{ public static void main(String args[])
{ String str[] = {"Mot", "Hai", "Ba", "Bon" };
System.out.print("Mang goc: ");
for(int i=0; i < str.length; i++)

System.out.print(str[i] + " ");
System.out.println("\n");

// Thay doi chuoi
str[0] = "Bon"; str[1] = "Ba";
str[2] = "Hai"; str[3] = "Mot";
System.out.print("Mang thay doi:");
for(int i=0; i < str.length; i++)

System.out.print(str[i] + " ");

System.out.print("\n");
}
}