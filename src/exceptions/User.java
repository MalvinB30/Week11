package exceptions;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Formatter;
import exceptions.ExcessiveFailedLoginException;
import exceptions.InvalidPropertyException;

public class User {

    private String firstName;
    private String lastName;
    private Character gender;
    private String address;
    private String userName;
    private String password; // Disimpan dalam bentuk hash
    private MessageDigest digest;

    private static final int maxLoginAttempts = 3;
    private static int LoginAttempts = 0;

    // Method untuk hashing password
    private String hash(String strToHash) {
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(strToHash.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

    // Constructor
    public User(String firstName, String lastName, Character gender, String address, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.userName = userName;
        this.password = hash(password); // Password di-hash saat inisialisasi
    }

    // Method login
    public boolean login(String username, String password) throws ExcessiveFailedLoginException {
        if (!this.userName.equals(username)) {
            // Asumsi: Username salah juga dianggap sebagai upaya gagal, 
            // tetapi kode di gambar hanya fokus pada password/upaya gagal.
            // Untuk menyalin kode persis: hanya memeriksa attempts saat username cocok.
        }

        if (LoginAttempts >= maxLoginAttempts) {
            // Pengecekan pertama untuk batas login
            throw new ExcessiveFailedLoginException("Anda telah mencapai batas login");
        }

        // Pengecekan kedua untuk batas login (di dalam scope IF username cocok)
        // **CATATAN:** Terdapat sedikit ketidakkonsistenan logika di sini, 
        // saya menyalin *sesuai* gambar.
        // Pengecekan pertama di sini akan selalu FAILED kecuali LoginAttempts sudah >= maxLoginAttempts.
        if (this.userName.equals(username)) {
            if (LoginAttempts == maxLoginAttempts) {
                LoginAttempts++; // Kenaikan ini membuat attempts menjadi 4
                throw new ExcessiveFailedLoginException(); // Melempar dengan pesan default
            } else if (LoginAttempts > maxLoginAttempts) {
                throw new ExcessiveFailedLoginException("Anda telah mencapai batas login"); // Pengecekan jika sudah terlampaui (attempts 4)
            }
        }


        // Logika utama: Verifikasi password
        if (this.password.equals(hash(password))) {
            LoginAttempts = 0; // Reset jika berhasil
            return true;
        } else {
            System.out.println("Password yang anda masukkan salah");
            System.out.print("Kesempatan Anda Login " + (maxLoginAttempts - LoginAttempts));
            System.out.println(" Kali Lagi");
            LoginAttempts++; // Tambah upaya gagal
        }

        return false;
    }

    // Method greeting
    public String greeting() {
        String greet = "Selamat Datang!";
        switch (gender) {
            case 'L':
                greet += " Tuan ";
                break;
            case 'P':
                greet += " Nona ";
                break;
        }
        greet += this.firstName + " " + this.lastName;

        return greet;
    }

    // Method getUsername
    public String getUserName() {
        return userName;
    }
}
