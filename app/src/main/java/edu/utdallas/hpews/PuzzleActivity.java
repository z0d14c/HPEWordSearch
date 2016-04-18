package edu.utdallas.hpews;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.utdallas.hpews.model.Coordinate;
import edu.utdallas.hpews.model.Puzzle;
import edu.utdallas.hpews.model.Solution;
import edu.utdallas.hpews.solver.SolverTask;
import edu.utdallas.hpews.solver.SolverTaskFinishedCallback;
import edu.utdallas.hpews.solver.SolverTaskProgressUpdateCallback;

public class PuzzleActivity extends AppCompatActivity {

    public static final String PUZZLE_PARAMETER_KEY = "puzzle";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);
        final Point screenSize = new Point();
        getWindowManager().getDefaultDisplay().getSize(screenSize);
        final Context context = this;



        // extract Puzzle from Intent
        final Puzzle puzzle = (Puzzle)getIntent().getSerializableExtra(PUZZLE_PARAMETER_KEY);
        if (puzzle == null) {
            throw new IllegalStateException("puzzle missing from Intent extras");
        }



        // initialize puzzle display adapter
        int cellDimension = screenSize.x / puzzle.getDimension();
        GridView gridview = (GridView) findViewById(R.id.WordPuzzle);
        gridview.setNumColumns(puzzle.getDimension());
        gridview.setColumnWidth(cellDimension);
        final PuzzleAdapter puzzleAdapter = new PuzzleAdapter(puzzle, cellDimension);
        gridview.setAdapter(puzzleAdapter);



        // initialize Solver async task
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setTitle("Solving Puzzle");
        progress.show();
        new SolverTask(
                new SolverTaskProgressUpdateCallback() {
                    @Override
                    public void progressUpdate(int progressPercent) {
                        progress.setProgress(progressPercent*10);
                    }
                },
                new SolverTaskFinishedCallback() {
                    @Override
                    public void finished(List<Solution> solutions) {
                        progress.dismiss();
                        GridView gridview = (GridView) findViewById(R.id.SolutionBank);
                        gridview.setAdapter(new SolutionAdapter(puzzle, puzzleAdapter));
                    }
                }
        ).execute(puzzle);
    }



    private class PuzzleAdapter extends BaseAdapter {

        private Puzzle puzzle;
        private int cellDimension;
        private Map<Coordinate, TextView> viewMap = new HashMap<>();

        public PuzzleAdapter(Puzzle puzzle, int cellDimension) {
            this.puzzle = puzzle;
            this.cellDimension = cellDimension;
        }

        @Override
        public int getCount() {
            return this.puzzle.getDimension() * this.puzzle.getDimension();
        }

        @Override
        public Object getItem(int position) {
            Coordinate coordinate = this.getCoordinate(position);
            return this.puzzle.getCharacterAt(coordinate.getX(), coordinate.getY());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Coordinate coordinate = this.getCoordinate(position);

            TextView tv = convertView != null ? (TextView)convertView : new TextView(parent.getContext());

            tv.setText(this.puzzle.getCharacterAt(coordinate.getX(), coordinate.getY()).toString());
            tv.setLayoutParams(new GridView.LayoutParams(this.cellDimension, this.cellDimension));
            tv.setGravity(Gravity.CENTER);

            if (this.viewMap.containsKey(coordinate) != true) {
                this.viewMap.put(coordinate, tv);
            }

            return tv;
        }

        private Coordinate getCoordinate(int position) {
            int y = position / this.puzzle.getDimension();
            int x = position % this.puzzle.getDimension();

            return new Coordinate(x, y);
        }

        public void highlight(Coordinate coordinate) {
            TextView tv = this.viewMap.get(coordinate);
            tv.setBackgroundColor(getResources().getColor(R.color.utilityColorPrimary));
            tv.setTextColor(getResources().getColor(R.color.white));
        }

        public void emphasis(Coordinate coordinate) {
            TextView tv = this.viewMap.get(coordinate);
            tv.setPaintFlags(tv.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
            tv.setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }



    private class SolutionAdapter extends BaseAdapter {

        private Puzzle puzzle;
        private PuzzleAdapter puzzleAdapter;

        public SolutionAdapter(Puzzle puzzle, PuzzleAdapter puzzleAdapter) {
            this.puzzle = puzzle;
            this.puzzleAdapter = puzzleAdapter;
        }

        @Override
        public int getCount() {
            return this.puzzle.getSolutions().size();
        }

        @Override
        public Object getItem(int position) {
            return this.puzzle.getSolutions().get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final TextView tv = convertView != null ? (TextView)convertView : new TextView(parent.getContext());

            tv.setText(this.getItem(position).toString());
            tv.setTextColor(getResources().getColor(R.color.white));
            tv.setGravity(Gravity.CENTER_VERTICAL);

            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tv.setPaintFlags(tv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    Solution solution = puzzle.getWordSolution(tv.getText().toString());
                    boolean firstLetter = true;
                    for (Coordinate coordinate : solution.getCoordinates()) {
                        puzzleAdapter.highlight(coordinate);
                        if (firstLetter) {
                            puzzleAdapter.emphasis(coordinate);
                            firstLetter = false;
                        }
                    }
                }
            });

            return tv;
        }
    }

}