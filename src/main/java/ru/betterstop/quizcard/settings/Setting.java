package ru.betterstop.quizcard.settings;

public interface Setting {

    int COUNT_RIGHT = 2;

    String FORM_PLAY_NAME = "Карточки с вопросами";
    String FORM_BUILDER_NAME = "Создание карточек с вопросами";
    int FORM_PLAY_HEIGHT = 350;
    int FORM_PLAY_WIDTH = 600;
    int FORM_BUILD_HEIGHT = 500;
    int FORM_BUILD_WIDTH = 470;

    String FONT_NAME = "sanserif";

    String LABEL_QUESTION = "Вопрос:";
    String LABEL_ANSWER = "Ответ:";

    String BUTTON_NEW = "Новая карточка";
    String BUTTON_SAVE = "Сохранить карточку";
    String BUTTON_NEXT = "Следующая карточка";
    String BUTTON_PREV = "Предыдущая карточка";
    String BUTTON_CHECK = "Проверить ответ";
    String BUTTON_SHOW = "Показать ответ";

    String MENU_FILE = "Файл";
    String MENU_LOAD = "Загрузить набор карточек";
    String MENU_SAVE = "Сохранить набор карточек";
    String MENU_CREATE = "Создать новый набор карточек";
    String MENU_CLOSE = "Закрыть";
    String MENU_EXIT = "Выход";

    String CORRECT_ANSWER = "Правильно!";
    String ERROR_ANSWER = "Ошибка!";

    String FILE_FILTER_NAME = "Набор карточек (*.qac)";
    String FILE_FILTER_EXT = "qac";

    String FINISH = "Вы закончили все карточки в наборе!!!";
}
