package edu.utdallas.hpews.solver;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Created by sasha on 4/3/16.
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class DictionaryServiceTest {

    @Before
    public void init() {
        Context context = InstrumentationRegistry.getTargetContext();
        DictionaryService.initialize(context, DictionaryService.SIZE.SMALL);
    }

    @Test
    public void getWords() {
        List<String> results = DictionaryService.getInstance().getWords(5);
        assertTrue(results.size() == 5);
    }

}