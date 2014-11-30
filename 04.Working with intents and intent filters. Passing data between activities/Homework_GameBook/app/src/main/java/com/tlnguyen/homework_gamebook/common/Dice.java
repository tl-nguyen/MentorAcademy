package com.tlnguyen.homework_gamebook.common;

import java.util.Random;

/**
 * Created by TL on 11/30/2014.
 */
public class Dice {
    public static final int MAX_DICE_SIZE = 6;

    public static int roll() {
        Random r = new Random();
        return r.nextInt(MAX_DICE_SIZE) + 1;
    }
}
