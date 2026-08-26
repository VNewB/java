public class StrOps {
    public static void main(String args[])
    { 
        String str1 = "Java la chon lua so mot cho lap trinh ung dung Web.";
        String str2 = new String(str1);
        String str3 = "Java ho tro doi tuong String de xu ly chuoi";
        int result, idx;
        System.out.println("str1:" + str1);
        System.out.println("str2:" + str2);
        System.out.println("str3:" + str3);
        System.out.println("Chieu dai cua chuoi str1 la:" + str1.length());
        // Hien thi chuoi str1, moi lan mot ky tu.
        System.out.println();
        for(int i=0;i < str1.length(); i++)
        System.out.println();
        if(str1.equals(str2))
            System.out.println("str1 == str2");
        else
            System.out.println("str1 != str2");
        if(str1.equals(str3))
            System.out.println("str1 == str3");
        else
            System.out.println("str1 != str3");
        result = str1.compareTo(str3);
        if(result == 0)
            System.out.println("str1 = str3 ");
        else
        if(result < 0)
            System.out.println("str1 < str3");
        else
            System.out.println("str1 > str3");
        
            // Tao chuoi moi cho str4
        String str4 = "Mot Hai Ba Mot";
        idx = str4.indexOf("Mot");
        System.out.println("str4:" + str4);
        System.out.println("Vi tri xuat hien dau tien cua chuoi con 'Mot' trong str4: " + idx);
        idx = str4.lastIndexOf("Mot");
        System.out.println("Vi tri xuat hien sau cung cua chuoi con 'Mot' trong str4:" + idx);
    }
}
