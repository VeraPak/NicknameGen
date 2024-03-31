package org.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static LongAdder stat3 = new LongAdder();

    public static LongAdder stat4 = new LongAdder();

    public static LongAdder stat5 = new LongAdder();
    public static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        for(String text : texts) {
            if(palindromCounter(text) && !identicalLettersCounter(text)){
                incrementer(text.length());
            }

            if(identicalLettersCounter(text)) {
                incrementer(text.length());
            }

            if(ascendingCounter(text) && !identicalLettersCounter(text)) {
                incrementer(text.length());
            }
        }

        System.out.println("3: " + stat3.sum());
        System.out.println("4: " + stat4.sum());
        System.out.println("5: " + stat5.sum());

        executorService.shutdown();
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
            executorService.submit(()->stat3.add(1));
        }
        if (length == 4) {
            executorService.submit(()->stat4.add(1));
        }
        if (length == 5) {
            executorService.submit(()->stat5.add(1));
        }
    }

}