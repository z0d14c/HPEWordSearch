package edu.utdallas.hpews.solver;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.utdallas.hpews.R;

/**
 * Created by sasha on 3/26/16.
 */
public class DictionaryService {

    public static enum SIZE {
        SMALL(10000),
        MEDIUM(20000),
        LARGE(50000);

        int size;

        SIZE(int size) {
            this.size = size;
        }

        public int getSize() {
            return this.size;
        }

        public int getResource() {
            int resource = 0;
            switch (this) {
                case SMALL:
                    return R.raw.small;
                case MEDIUM:
                    return R.raw.medium;
                case LARGE:
                    return R.raw.large;
            }
            return resource;
        }
    }

    private static DictionaryService ourInstance;
    public static DictionaryService getInstance() {
        if (ourInstance == null) {
            throw new IllegalStateException();
        }
        return ourInstance;
    }



    private ArrayList<String> dictionary;



    private DictionaryService(Context context, SIZE size) {
        this.dictionary = new ArrayList<>(size.getSize());
        InputStream inputStream = context.getResources().openRawResource(size.getResource());
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                this.dictionary.add(line);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }



    public List<String> getWords(int count) {

        List<String> results = new ArrayList<>(count);

        Random random = new Random();

        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(this.dictionary.size());
            String randomWord = this.dictionary.get(randomIndex);
            results.add(randomWord);
        }

        return results;
    }



    public static void initialize(Context applicationContext, SIZE size) {
        ourInstance = new DictionaryService(applicationContext, size);
    }
}