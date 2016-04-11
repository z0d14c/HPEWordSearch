package edu.utdallas.hpews;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import edu.utdallas.hpews.R;
import edu.utdallas.hpews.generator.GeneratorService;
import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Solution;
import edu.utdallas.hpews.solver.SolverTask;
import edu.utdallas.hpews.solver.SolverTaskFinishedCallback;
import edu.utdallas.hpews.solver.SolverTaskProgressUpdateCallback;

public class PuzzleActivity extends AppCompatActivity {

    public static final String PUZZLE_PARAMETER_KEY = "puzzle";

    String LogTag = "PuzzleActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        final Context context = this;

        Puzzle puzzle = (Puzzle)getIntent().getSerializableExtra(PUZZLE_PARAMETER_KEY);
        if (puzzle == null) {
            throw new IllegalStateException("puzzle missing from Intent extras");
        }

        final int dimensions = puzzle.getDimension();
        final LinearLayout WordPuzzleLayout = (LinearLayout) findViewById(R.id.WordPuzzleLayout);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Integer width = size.x;
        Integer height = size.y;
        GridView gridview = (GridView) findViewById(R.id.WordPuzzle);
        if (gridview != null) {
            Log.v(LogTag, "activating grid adapter");
            String[] letters = new String[81];
            for(int i = 0; i<81; i++) {
                letters[i] = "A";
            }
            gridview.setNumColumns(dimensions);
            gridview.setAdapter(new PuzzleAdapter(context, puzzle));
        }
        Log.v(LogTag, width.toString());
        Log.v(LogTag, height.toString());
        // need Global layout listener to determine WordPuzzleLayout when it renders
//        ViewTreeObserver vto = WordPuzzleLayout.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int width = WordPuzzleLayout.getMeasuredWidth();
//                int height = WordPuzzleLayout.getMeasuredHeight();
//                GridView gridview = (GridView) findViewById(R.id.WordPuzzle);
//                if (gridview != null) {
//                    Log.v(LogTag, "activating grid adapter");
//                    gridview.setNumColumns(dimensions);
//                    gridview.setAdapter(new PuzzleAdapter(context, dimensions, width, height));
//                }
//            }
//        });
//        gridview.setOnItemClickListener(new OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(HelloGridView.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });



        // when you're done setting up the initial UI state (including solution progress control)
        new SolverTask(
                new SolverTaskProgressUpdateCallback() {
                    @Override
                    public void progressUpdate(int progressPercent) {
                        // Thomas/Izu:  fill in code to react to progress update parameter periodically
                    }
                },
                new SolverTaskFinishedCallback() {
                    @Override
                    public void finished(List<Solution> solutions) {
                        // Thomas/Izu:  fill in code to react to solution process completing
                    }
                }
        ).execute(puzzle);
    }

    private class PuzzleAdapter extends BaseAdapter {
        private Context context;
        private String[] PuzzleLetters;
        private int dimensions;
        private int width;
        private int height;
        public PuzzleAdapter(Context context, Puzzle puzzle) {
            this.context = context;

            this.dimensions = puzzle.getDimension();
            this.PuzzleLetters = new String[this.dimensions * this.dimensions];
            int i = 0;
            for (int x = 0; x < this.dimensions; x++) {
                for (int y = 0; y < this.dimensions; y++) {
                    this.PuzzleLetters[i++] = puzzle.getCharacterAt(x, y).toString();
                }
            }
        }

        // return number of items (characters) in puzzle
        public int getCount() {
            return this.PuzzleLetters.length;
        }
        //
        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        //return dimensions
        //TODO: make return puzzle.getDimension()
        public int getDimension() {
            return this.dimensions;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            TextView tv;
//            if (convertView == null) {
            tv = new TextView(context);
//            tv.setLayoutParams(new GridView.LayoutParams(width/dimensions, height/dimensions));
            tv.setLayoutParams(new GridView.LayoutParams(85, 85));
//            }
//            else {
//                tv = (TextView) convertView;
//            }

            tv.setText(this.PuzzleLetters[position]);
            return tv;
        }
    }

}


