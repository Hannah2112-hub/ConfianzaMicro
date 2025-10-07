package com.example.confianzamicro.ui.menu;

public class MenuOption {
    public final String title;
    public final int iconRes;
    public final Class<?> target;

    public MenuOption(String title, int iconRes, Class<?> target) {
        this.title = title;
        this.iconRes = iconRes;
        this.target = target;
    }
}
