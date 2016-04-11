package edu.utdallas.hpews.solver;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.List;

import edu.utdallas.hpews.generator.GeneratorService;
import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Solution;

import static org.junit.Assert.assertTrue;

/**
 * Created by sasha on 4/10/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class SolverServiceTest {

    @Before
    public void init() {
        Context context = InstrumentationRegistry.getTargetContext();
        DictionaryService.initialize(context, DictionaryService.SIZE.SMALL);
    }

    @Test
    public void solvePuzzle() {
        List<String> knownWords = DictionaryService.getInstance().getWords(5);

        Puzzle puzzle = GeneratorService.getInstance().generatePuzzle(knownWords);
        SolverService.getInstance().solvePuzzle(puzzle);
        List<String> foundWords = puzzle.getSolutionWords();

        // this convoluted mess is necessary because the known words might be singular and/or present tense,
        // and thus shorter and contained within the found words, which might be plural and/or past tense and thus longer,
        // or vice versa
        boolean allWordsFound = true;
        for (String knownWord : knownWords) {
            boolean found = false;
            for (int i = 0; i < foundWords.size(); i++) {
                String foundWord = foundWords.get(i);
                if (foundWord.contains(knownWord) || knownWord.contains(foundWord)) {
                    found = true;
                    break;
                }
            }
            if (found != true) {
                allWordsFound = false;
                break;
            }
        }

        assertTrue(allWordsFound);
    }

}