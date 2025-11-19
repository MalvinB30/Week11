package Contoh;

public class CustomizeException {
    public static void main(String[] args) {
        try {
            int[] arr = new int[4];
            int i = arr[4]; // Ini akan melempar ArrayIndexOutOfBoundsException
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
