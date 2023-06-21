import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String result;
        try {
            result = calc(input);
            System.out.println(result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка: т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
        }
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.split(" ");
        if (parts.length != 3) {
            throw new Exception("Неправильный формат выражения");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        if (!isValidInput(operand1) || !isValidInput(operand2)) {
            throw new IllegalArgumentException("Неправильный формат чисел");
        }

        boolean isRoman = isRoman(operand1) && isRoman(operand2);

        int num1 = isRoman ? romanToArabic(operand1) : Integer.parseInt(operand1);
        int num2 = isRoman ? romanToArabic(operand2) : Integer.parseInt(operand2);

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new IllegalArgumentException("Неподдерживаемая операция");
        }

        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат должен быть положительным римским числом");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    private static boolean isValidInput(String input) {
        return input.matches("^[1-9]|10$|^[IVX]{1,3}$");
    }

    private static boolean isRoman(String input) {
        return input.matches("^[IVX]+$");
    }

    private static int romanToArabic(String input) {
        Map<Character, Integer> romanToArabicMap = new HashMap<>();
        romanToArabicMap.put('I', 1);
        romanToArabicMap.put('V', 5);
        romanToArabicMap.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int value = romanToArabicMap.get(input.charAt(i));
            if (value < prevValue) {
                result -= value;
            } else {
                result += value;
                prevValue = value;
            }
        }

        return result;
    }

    private static String arabicToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("Число должно быть в диапазоне от 1 до 3999");
        }

        StringBuilder roman = new StringBuilder();
        Map<Integer, String> arabicToRomanMap = new HashMap<>();
        arabicToRomanMap.put(1000, "M");
        arabicToRomanMap.put(900, "CM");
        arabicToRomanMap.put(500, "D");
        arabicToRomanMap.put(400, "CD");
        arabicToRomanMap.put(100, "C");
        arabicToRomanMap.put(90, "XC");
        arabicToRomanMap.put(50, "L");
        arabicToRomanMap.put(40, "XL");
        arabicToRomanMap.put(10, "X");
        arabicToRomanMap.put(9, "IX");
        arabicToRomanMap.put(5, "V");
        arabicToRomanMap.put(4, "IV");
        arabicToRomanMap.put(1, "I");

        for (Map.Entry<Integer, String> entry : arabicToRomanMap.entrySet()) {
            int value = entry.getKey();
            String symbol = entry.getValue();

            while (number >= value) {
                roman.append(symbol);
                number -= value;
            }
        }

        return roman.toString();
    }
}
