package backend.academy.app;

import java.io.PrintStream;
import java.util.Scanner;

/**
 * Реализация интерфейса {@link CommandSource} для взаимодействия с пользователем через консоль.
 */
public class ConsoleCommandSource implements CommandSource {

    private static final PrintStream OUT = System.out;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Считывает ввод пользователя в виде целого числа.
     *
     * @param prompt сообщение с инструкцией для ввода
     * @return введённое пользователем число
     */
    @Override
    public int getIntInput(String prompt) {
        OUT.println(prompt);
        while (!scanner.hasNextInt()) {
            OUT.println("Ошибка ввода, попробуйте снова.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Отображает сообщение или результат в консоли.
     *
     * @param messageOrMaze сообщение или строковое представление лабиринта
     */
    @Override
    public void showMessageOrMaze(String messageOrMaze) {
        OUT.println(messageOrMaze);
    }
}
