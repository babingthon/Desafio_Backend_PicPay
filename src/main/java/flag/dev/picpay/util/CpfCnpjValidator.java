package flag.dev.picpay.util;

public class CpfCnpjValidator {

    public static boolean isValidCpfCnpj(String cpfCnpj) {
        if (cpfCnpj == null) {
            return false;
        }
        String digits = cpfCnpj.replaceAll("\\D", "");

        if (digits.length() == 11) {
            return isValidCpf(digits);
        } else if (digits.length() == 14) {
            return isValidCnpj(digits);
        } else {
            return false;
        }
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf.matches("(\\d)\\1{10}")) return false; // all digits equal

        int sum = 0;
        for (int i = 0; i < 9; i++)
            sum += (cpf.charAt(i) - '0') * (10 - i);

        int check1 = 11 - (sum % 11);
        if (check1 >= 10) check1 = 0;
        if (check1 != (cpf.charAt(9) - '0')) return false;

        sum = 0;
        for (int i = 0; i < 10; i++)
            sum += (cpf.charAt(i) - '0') * (11 - i);

        int check2 = 11 - (sum % 11);
        if (check2 >= 10) check2 = 0;
        return check2 == (cpf.charAt(10) - '0');
    }

    private static boolean isValidCnpj(String cnpj) {
        if (cnpj.matches("(\\d)\\1{13}")) return false; // all digits equal

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < 12; i++)
            sum += (cnpj.charAt(i) - '0') * weights1[i];

        int check1 = sum % 11;
        check1 = check1 < 2 ? 0 : 11 - check1;
        if (check1 != (cnpj.charAt(12) - '0')) return false;

        sum = 0;
        for (int i = 0; i < 13; i++)
            sum += (cnpj.charAt(i) - '0') * weights2[i];

        int check2 = sum % 11;
        check2 = check2 < 2 ? 0 : 11 - check2;
        return check2 == (cnpj.charAt(13) - '0');
    }
}
