package edu.utdallas.hpews.solver;

import java.util.List;

/**
 * Created by sasha on 3/26/16.
 */
public class DictionaryService {

    private static DictionaryService ourInstance = new DictionaryService();
    public static DictionaryService getInstance() {
        return ourInstance;
    }



    private DictionaryService() {
    }



    public List<String> getWords(int count) {

        // TODO: implement
        throw new UnsupportedOperationException();

    }
}