package com.yelstream.topp.standard.terminal;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

public class SimpleTuiExample {
    public static void main(String[] args) throws Exception {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        TextGraphics graphics = screen.newTextGraphics();
        graphics.putString(2, 1, "Hello Terminal Window! 🌟");
        graphics.putString(2, 3, "Press any key to quit...");

        screen.refresh();
        screen.readInput();  // wait for keypress

        screen.stopScreen();
        terminal.close();
    }
}
