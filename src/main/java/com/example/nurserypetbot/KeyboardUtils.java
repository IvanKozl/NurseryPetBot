package com.example.nurserypetbot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;
@Component
public class KeyboardUtils {

    public ReplyKeyboardMarkup getKeyboard() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        // размер клавы
        keyboardMarkup.setResizeKeyboard(true);

        // Установите, чтобы клавиатура исчезала после нажатия кнопки (true/false)
        keyboardMarkup.setOneTimeKeyboard(true);

        // Устанавливаем строки клавиатуры
        keyboardMarkup.setKeyboard(getKeyboardRows());
        return keyboardMarkup;
    }

    private static List<KeyboardRow> getKeyboardRows() {
        List<KeyboardRow> keyboard = new ArrayList<>();

        // создали клаву
        KeyboardRow row1 = new KeyboardRow();
        row1.add("/start");
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Button 2");
        keyboard.add(row2);

        return keyboard;
    }
    }

