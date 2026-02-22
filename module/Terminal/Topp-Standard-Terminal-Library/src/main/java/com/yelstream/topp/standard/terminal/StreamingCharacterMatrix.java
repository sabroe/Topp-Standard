package com.yelstream.topp.standard.terminal;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Random;

public class StreamingCharacterMatrix {

    private static final int COLS = 64;
    private static final int ROWS = 64;

    public static void main(String[] args) throws IOException, InterruptedException {
        Terminal terminal = new DefaultTerminalFactory()
                .setPreferTerminalEmulator(false).setForceTextTerminal(true)
                .setInitialTerminalSize(new TerminalSize(COLS, ROWS + 3))
                .createTerminal();

        Screen screen = new TerminalScreen(terminal);
        screen.startScreen();

        TextGraphics graphics = screen.newTextGraphics();

        Random rnd = new Random();
        char[] chars = "01#·*·$@░▒".toCharArray();

        graphics.setForegroundColor(TextColor.ANSI.GREEN_BRIGHT);
        graphics.putString(2, 0, " Streaming random character matrix  –  press any key to quit ");

        TextGraphics bg = graphics.setBackgroundColor(TextColor.ANSI.BLACK);
        bg.fill(' ');   // or use drawRectangle if you prefer bounds

        screen.refresh();

        boolean running = true;
        while (running) {
            int updates = 8 + rnd.nextInt(33);
            for (int i = 0; i < updates; i++) {
                int x = rnd.nextInt(COLS);
                int y = rnd.nextInt(ROWS) + 2;  // skip header rows

                char c = chars[rnd.nextInt(chars.length)];
                TextColor fg = TextColor.ANSI.values()[2 + rnd.nextInt(6)]; // RED to CYAN-ish

                graphics.setCharacter(
                        x,
                        y,
                        TextCharacter.fromCharacter(c,fg,TextColor.ANSI.BLACK_BRIGHT)[0]
                );
            }

            screen.refresh();
            Thread.sleep(10);

            if (screen.pollInput() != null) {
                running = false;
            }
        }

        screen.stopScreen();
        terminal.close();
    }
}
