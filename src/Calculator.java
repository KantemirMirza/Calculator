import java.util.*;

public class Calculator {
    static Map<String, Integer> romanToArabic = new HashMap<>();
    static {
        romanToArabic.put("I", 1);
        romanToArabic.put("II", 2);
        romanToArabic.put("III", 3);
        romanToArabic.put("IV", 4);
        romanToArabic.put("V", 5);
        romanToArabic.put("VI", 6);
        romanToArabic.put("VII", 7);
        romanToArabic.put("VIII", 8);
        romanToArabic.put("IX", 9);
        romanToArabic.put("X", 10);
    }
    static Map<String, Integer> arabicToRoman = new HashMap<>();
    static {
        arabicToRoman.put("١", 1);
        arabicToRoman.put("٢", 2);
        arabicToRoman.put("٣", 3);
        arabicToRoman.put("٤", 4);
        arabicToRoman.put("٥", 5);
        arabicToRoman.put("٦", 6);
        arabicToRoman.put("٧", 7);
        arabicToRoman.put("٨", 8);
        arabicToRoman.put("٩", 9);
        arabicToRoman.put("١٠", 10);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.print("Hesap Makinesi seç: (arap/roma)");
                String choice = scanner.nextLine();
                if (!choice.equalsIgnoreCase("arap") && !choice.equalsIgnoreCase("roma")) {
                    throw new IllegalArgumentException("Geçersiz seçim. Lütfen 'arap' veya 'roma' ikisinden birini seçmelisiniz.");
                }

                System.out.println("(Sadece (I ve X arasında) Roma veya Arap (١  - ١٠) rakamları girmelisiniz): ");
                System.out.print("Birinci sayıyı girin: ");
                String num1Str = scanner.nextLine();
                if ((choice.equalsIgnoreCase("arap") && !arabicToRoman.containsKey(num1Str)) ||
                        (choice.equalsIgnoreCase("roma") && !romanToArabic.containsKey(num1Str))) {
                    throw new IllegalArgumentException("Arap Makinesi için Arap Rakam, Roma Makinesi için Roma Rakamları girmelisiniz. Tekrar deneyin.");
                }

                System.out.print("İkinci sayıyı girin: ");
                String num2Str = scanner.nextLine();
                if ((choice.equalsIgnoreCase("arap") && !arabicToRoman.containsKey(num2Str)) ||
                        (choice.equalsIgnoreCase("roma") && !romanToArabic.containsKey(num2Str))) {
                    throw new IllegalArgumentException("Arap Makinesi için Arap Rakam, Roma Makinesi için Roma Rakamları girmelisiniz. Tekrar deneyin.");
                }

                System.out.print("İşlem operatörünü seçin (+, -, *, /): ");
                String operator = scanner.nextLine();

                int num1, num2;
                if (choice.equalsIgnoreCase("arap")) {
                    num1 = arabicToRoman.get(num1Str);
                    num2 = arabicToRoman.get(num2Str);
                } else {
                    num1 = romanToArabic.get(num1Str);
                    num2 = romanToArabic.get(num2Str);
                }

                int result = calculate(num1, num2, operator);
                if (choice.equalsIgnoreCase("roma")) {
                    if (result <= 0) {
                        throw new IllegalArgumentException("Sonuç pozitif Roma rakamı olmalıdır.");
                    }
                    System.out.println(toRoman(result));
                } else {
                    System.out.println(toArabic(result)); // Sonucu Arapça rakamlarla yazdır
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }
        }
    }
    static int calculate(int num1, int num2, String operator) {
        switch (operator) {
            case "+":
                return num1 + num2;
            case "-":
                return num1 - num2;
            case "*":
                return num1 * num2;
            case "/":
                if (num2 == 0) {
                    throw new ArithmeticException("Sıfıra bölme hatası");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Geçersiz operatör sadece ( -, +, /, * ) bir tanesini seçmelisiniz");
        }
    }
    static String toArabic(int num) {
        // Arapça rakamlarını ve karşılık gelen değerlerini azalan sırada tutan bir dizi
        String[] arabicSymbols = {"٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩"};
        int[] values = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        StringBuilder result = new StringBuilder();

        // Sayıyı Arapça rakamlara dönüştürme işlemi
        if (num == 0) {
            return "٠"; // Sıfır özel durum
        }
        if (num < 0) {
            result.append('-');
            num *= -1; // Negatif sayıyı pozitife çevir
        }
        // Sayıyı Arapça rakamlara dönüştürme işlemi
        while (num > 0) {
            int digit = num % 10;
            result.insert(0, arabicSymbols[digit]);
            num /= 10;
        }
        return result.toString();
    }
    static String toRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Sayı aralık dışında (1-3999)");
        }

        StringBuilder result = new StringBuilder();

        // Roma rakamlarını ve karşılık gelen değerlerini azalan sırada tutan bir dizi
        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        // Sayıyı Roma rakamına dönüştürme işlemi
        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                result.append(romanSymbols[i]);
                num -= values[i];
            }
        }
        return result.toString();
    }
}
