import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        String expression = scanner.nextLine();
        try {
            String result = calculate(expression);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    public static String calculate(String expression) {
        String[] parts = expression.split("\\s+");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Некорректное выражение");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        if (!(isArabicNumber(operand1) && isArabicNumber(operand2)) && !(isRomanNumber(operand1) && isRomanNumber(operand2))) {
            throw new IllegalArgumentException("Нужны либо арабские, либо римские цифры одновременно");
        }

        int num1, num2;
        boolean isRoman = false;

        if (isArabicNumber(operand1) && isArabicNumber(operand2)) {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
            if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
                throw new IllegalArgumentException("Некорректные числа. Допустимы значения от 1 до 10");
            }
        } else {
            num1 = romanToArabic(operand1);
            num2 = romanToArabic(operand2);
            isRoman = true;
        }

        int result;
        if (operator.equals("+")) {
            result = num1 + num2;
        } else if (operator.equals("-")) {
            result = num1 - num2;
        } else if (operator.equals("*")) {
            result = num1 * num2;
        } else if (operator.equals("/")) {
            if (num2 == 0) {
                throw new IllegalArgumentException("На 0 делить нельзя");
            }
            result = num1 / num2;
        } else {
            throw new IllegalArgumentException("Операция не поддерживается. Поддерживаются только +, -, * и /");
        }

        if (isRoman) {
            if (result <= 0) {
                throw new IllegalArgumentException("Результат не может быть отрицательным");
            }
            return arabicToRoman(result);
        } else {
            return String.valueOf(result);
        }
    }

    public static boolean isArabicNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isRomanNumber(String input) {
        return romanToArabic.containsKey(input);
    }

    public static int romanToArabic(String input) {
        int result = 0;
        int prevValue = 0;

        for (int i = input.length() - 1; i >= 0; i--) {
            int value = romanSymbolToValue(input.charAt(i));
            if (value >= prevValue) {
                result += value;
            } else {
                result -= value;
            }
            prevValue = value;
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if (number < 1 || number > 100) {
            throw new IllegalArgumentException("Некорректное арабское число. Допустимы значения от 1 до 100");
        }

        String romanNumber = "";

        for (int i = 0; i < arabicValues.length; i++) {
            while (number >= arabicValues[i]) {
                romanNumber += romanSymbols[i];
                number -= arabicValues[i];
            }
        }

        return romanNumber;
    }

    public static final Map<String, Integer> romanToArabic = new HashMap<>();

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

    public static  int[] arabicValues = { 100, 90, 50, 40, 10, 9, 5, 4, 1 };
    public static  String[] romanSymbols = { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };

    public static int romanSymbolToValue(char symbol) {
        if (symbol == 'I') {
            return 1;
        } else if (symbol == 'V') {
            return 5;
        } else if (symbol == 'X') {
            return 10;
        } else {
            throw new IllegalArgumentException("Некорректный символ римской цифры: " + symbol);
        }
    }
}
