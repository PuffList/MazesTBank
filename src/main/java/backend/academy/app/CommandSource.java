package backend.academy.app;

/**
 * Интерфейс для взаимодействия с пользователем.
 * Определяет методы для получения ввода и вывода сообщений или данных.
 */
public interface CommandSource {

    int getIntInput(String prompt);

    void showMessageOrMaze(String messageOrMaze);
}
