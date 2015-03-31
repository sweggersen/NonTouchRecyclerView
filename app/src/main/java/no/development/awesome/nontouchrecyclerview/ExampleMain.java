package no.development.awesome.nontouchrecyclerview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.TextView;

import no.development.awesome.library.StrokeRecyclerView;

public class ExampleMain extends Activity {

    public int mCellWidth;
    public int mCellHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_main);

        final int mumColumns = getResources().getInteger(R.integer.num_columns);
        final int cellMargin = (int) getResources().getDimension(R.dimen.cell_margin);
        final int recyclerViewPadding = (int) getResources().getDimension(R.dimen.recycle_view_padding);

        StrokeRecyclerView recyclerView = (StrokeRecyclerView) findViewById(R.id.stroke_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(cellMargin, cellMargin, cellMargin, cellMargin);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(this, mumColumns, GridLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new Adapter(recyclerView));

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mCellWidth = (size.x - (2*mumColumns*cellMargin) - (2*recyclerViewPadding)) / mumColumns;
        mCellHeight = mCellWidth / 2;

    }

    public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

        private StrokeRecyclerView mRecyclerView;

        public Adapter(StrokeRecyclerView recyclerView) {
            mRecyclerView = recyclerView;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(ViewGroup v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.text);
            }
        }

        @Override
        public Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewGroup v = (ViewGroup) LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            AbsListView.LayoutParams params = new AbsListView.LayoutParams(mCellWidth, mCellHeight);
            holder.itemView.setLayoutParams(params);

            holder.textView.setText(String.valueOf(position));

            holder.itemView.setFocusable(true);
            holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

                @Override
                public void onFocusChange(final View view, final boolean b) {
                    if (b) mRecyclerView.highlightView(view, true);
                    else mRecyclerView.clearHighlightedView();
                }
            });

        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }

}
