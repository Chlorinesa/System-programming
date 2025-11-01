import java.util.Scanner;
import java.util.regex.Pattern;

class MyException extends RuntimeException {
    public MyException(String message){
        super(message);
    }
}

class UserDataValidator {
    private static final int minPasswordLength = 6;
    private static final int minUsernameLength = 3;
    private static final String emailRegex = "^[\\w.%+-]+@[\\w.-]+\\.[a-z]{2,}$";

    public static void checkUsernameAndPasswordLength(String username, String password) throws MyException {
        if(username.trim().length() < minUsernameLength || password.trim().length() < minPasswordLength) {
            throw new MyException("Недостаточная длина логина или пароля");
        }
    }

    public static void checkEmail(String email) throws MyException {
        if (!Pattern.matches(emailRegex, email)) {
            throw new MyException("Введен некорректный email");
        }
    }
}

public class SP_lr7_OwnException {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите логин: ");
        String username = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        System.out.print("Введите email: ");
        String email = scanner.nextLine();
        boolean isValid = true;
        try {
            UserDataValidator.checkUsernameAndPasswordLength(username, password);
        } catch (MyException e) {
            System.err.println("Ошибка: " + e.getMessage());
            isValid = false;
        }
        try {
            UserDataValidator.checkEmail(email);
        } catch (MyException e) {
            System.err.println("Ошибка: " + e.getMessage());
            isValid = false;
        }
        if (isValid) {
            System.out.println("✅ Все данные корректны! Регистрация успешна.");
        } else {
            System.out.println("❌ Регистрация не завершена");
        }

    }
}