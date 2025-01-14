package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger counter3 = new AtomicInteger(0);
    public static AtomicInteger counter4 = new AtomicInteger(0);
    public static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread t1 = new Thread(() -> {
            for(String text : texts) {
                if(palindromCounter(text) && !identicalLettersCounter(text)){
                    incrementer(text.length());
                }
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for(String text : texts) {
                if(identicalLettersCounter(text)) {
                    incrementer(text.length());
                }
            }
        });
        t2.start();

        Thread t3 = new Thread(() -> {
            for(String text : texts) {
                if(ascendingCounter(text) && !identicalLettersCounter(text)) {
                    incrementer(text.length());
                }
            }
        });
        t3.start();

        t1.join();
        t2.join();
        t3.join();

        System.out.println("3: " + counter3);
        System.out.println("4: " + counter4);
        System.out.println("5: " + counter5);
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean palindromCounter(String text) {
        if (text.equalsIgnoreCase(new StringBuilder(text).reverse().toString())) {
            return true;
        }
        return false;
    }

    public static boolean identicalLettersCounter(String text) {
        boolean isSame = false;
        for (int i = 1; i < text.length(); i++) {
            for (int j = 0; j < text.length()-1; j++) {
                if (text.charAt(i) == text.charAt(j)) {
                    isSame=true;
                }
            }
        }
        return isSame;
    }

    public static boolean ascendingCounter(String text) {
        boolean isAscending = false;
        for (int i = 1; i < text.length(); i++) {
            for (int j = 0; j < text.length()-1; j++) {
                if (text.charAt(i) <= text.charAt(j)) {
                    isAscending=true;
                }
            }
        }
        return isAscending;
    }

    public static void incrementer(int length) {
        if (length == 3) {
            counter3.getAndIncrement();
        }
        if (length == 4) {
            counter4.getAndIncrement();
        }
        if (length == 5) {
            counter5.getAndIncrement();
        }
    }

}