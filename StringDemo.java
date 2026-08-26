public class StringDemo {
    public static void main(String args[])
    {
    // Tao chuoi bang nhieu cach khac nhau
        String str1 = new String("Chuoi trong java la nhung Objects.");
        String str2 = "Chung duoc xay dung bang nhieu cach khac nhau.";
        String str3 = new String(str2);
        System.out.println(str1);
        System.out.println(str2);
        System.out.println(str3);
    }
}
