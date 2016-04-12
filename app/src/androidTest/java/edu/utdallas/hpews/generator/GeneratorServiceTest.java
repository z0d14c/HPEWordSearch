package edu.utdallas.hpews.generator;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.utdallas.hpews.generator.GeneratorService;
import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.solver.DictionaryService;

import static org.junit.Assert.assertTrue;

/**
 * Created by sasha on 4/9/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class GeneratorServiceTest {

    @Before
    public void init() {
        Context context = InstrumentationRegistry.getTargetContext();
        DictionaryService.initialize(context, DictionaryService.SIZE.SMALL);
    }

    @Test
    public void puzzleShouldBeCompletelyFilledOut() {

        Puzzle puzzle = GeneratorService.getInstance().generatePuzzle();

        Boolean completelyFilledOut = true;
        rowloop:
        for (int x = 0; x < puzzle.getDimension(); x++) {
            for (int y = 0; y < puzzle.getDimension(); y++) {
                if (puzzle.getCharacterAt(x, y) == null) {
                    completelyFilledOut = false;
                    break rowloop;
                }
            }
        }

        assertTrue(completelyFilledOut);
    }

}