package no.development.awesome.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/*
    Copyright [2015] [Sam Mathias Weggersen]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

public class StrokeRecyclerView extends RecyclerView {

    private Drawable mStrokeCell;
    private Rect mStrokeCellOriginalBounds;
    private Rect mStrokeCellCurrentBounds;

    private SelectorPosition mSelectorPosition;
    private StrokePosition mStrokePosition;
    private boolean mIsFilled;
    private float mFillAlpha;
    private int mFillColor;
    private float mCornerRadiusX;
    private float mCornerRadiusY;
    private float mStrokeThickness;
    private int mStrokeColor;
    private int mStrokeColorFocused;
    private int mStrokeColorSelected;
    private float mStrokeMarginLeft;
    private float mStrokeMarginTop;
    private float mStrokeMarginRight;
    private float mStrokeMarginBottom;
    private float mStrokeSpacingLeft;
    private float mStrokeSpacingTop;
    private float mStrokeSpacingRight;
    private float mStrokeSpacingBottom;

    public StrokeRecyclerView(Context context) {
        super(context);
        init(null);
    }

    public StrokeRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StrokeRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        TypedValue typedValue = new TypedValue();
        getResources().getValue(R.dimen.defFillAlpha, typedValue, true);

        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.StrokeRecyclerView,
                    0,
                    0);

            try {
                mSelectorPosition = SelectorPosition.getSelectorPosition(a.getInteger(R.styleable.StrokeRecyclerView_selectorPosition, getResources().getInteger(R.integer.defSelectorPosition)));
                mStrokePosition = StrokePosition.getStrokePosition(a.getInteger(R.styleable.StrokeRecyclerView_strokePosition, getResources().getInteger(R.integer.defStrokePosition)));
                mIsFilled = a.getBoolean(R.styleable.StrokeRecyclerView_filled, getResources().getBoolean(R.bool.defIsFilled));
                mFillAlpha = a.getFloat(R.styleable.StrokeRecyclerView_fillAlpha, typedValue.getFloat());
                mFillColor = a.getColor(R.styleable.StrokeRecyclerView_fillColor, getResources().getColor(R.color.defFillColor));
                mCornerRadiusX = a.getDimension(R.styleable.StrokeRecyclerView_cornerRadius, getResources().getDimension(R.dimen.defCornerRadius));
                mCornerRadiusY = a.getDimension(R.styleable.StrokeRecyclerView_cornerRadius, getResources().getDimension(R.dimen.defCornerRadius));
                mStrokeThickness = a.getDimension(R.styleable.StrokeRecyclerView_strokeWidth, getResources().getDimension(R.dimen.defStrokeWidth));
                mStrokeColorFocused = a.getColor(R.styleable.StrokeRecyclerView_strokeColor, getResources().getColor(R.color.defStrokeColor));
                mStrokeColorSelected = a.getColor(R.styleable.StrokeRecyclerView_strokeColor, getResources().getColor(R.color.defStrokeColor));
                mStrokeMarginLeft = a.getDimension(R.styleable.StrokeRecyclerView_marginLeft, getResources().getDimension(R.dimen.defStrokeMarginLeft));
                mStrokeMarginTop = a.getDimension(R.styleable.StrokeRecyclerView_marginTop, getResources().getDimension(R.dimen.defStrokeMarginTop));
                mStrokeMarginRight = a.getDimension(R.styleable.StrokeRecyclerView_marginRight, getResources().getDimension(R.dimen.defStrokeMarginRight));
                mStrokeMarginBottom = a.getDimension(R.styleable.StrokeRecyclerView_marginBottom, getResources().getDimension(R.dimen.defStrokeMarginBottom));
                mStrokeSpacingLeft = a.getDimension(R.styleable.StrokeRecyclerView_spacingLeft, getResources().getDimension(R.dimen.defStrokeSpacingLeft));
                mStrokeSpacingTop = a.getDimension(R.styleable.StrokeRecyclerView_spacingTop, getResources().getDimension(R.dimen.defStrokeSpacingTop));
                mStrokeSpacingRight = a.getDimension(R.styleable.StrokeRecyclerView_spacingRight, getResources().getDimension(R.dimen.defStrokeSpacingRight));
                mStrokeSpacingBottom = a.getDimension(R.styleable.StrokeRecyclerView_spacingBottom, getResources().getDimension(R.dimen.defStrokeSpacingBottom));
            } finally {
                a.recycle();
            }
        } else {
            mSelectorPosition = SelectorPosition.getSelectorPosition(getResources().getInteger(R.integer.defSelectorPosition));
            mStrokePosition = StrokePosition.getStrokePosition(getResources().getInteger(R.integer.defStrokePosition));
            mIsFilled = getResources().getBoolean(R.bool.defIsFilled);
            mFillAlpha = typedValue.getFloat();
            mFillColor = getResources().getColor(R.color.defFillColor);
            mCornerRadiusX = getResources().getDimension(R.dimen.defCornerRadius);
            mCornerRadiusY = getResources().getDimension(R.dimen.defCornerRadius);
            mStrokeThickness = getResources().getDimension(R.dimen.defStrokeWidth);
            mStrokeColorFocused = getResources().getColor(R.color.defStrokeColor);
            mStrokeColorSelected = getResources().getColor(R.color.defStrokeColor);
            mStrokeMarginLeft = getResources().getDimension(R.dimen.defStrokeMarginLeft);
            mStrokeMarginTop = getResources().getDimension(R.dimen.defStrokeMarginTop);
            mStrokeMarginRight = getResources().getDimension(R.dimen.defStrokeMarginRight);
            mStrokeMarginBottom = getResources().getDimension(R.dimen.defStrokeMarginBottom);
            mStrokeSpacingLeft = getResources().getDimension(R.dimen.defStrokeSpacingLeft);
            mStrokeSpacingTop = getResources().getDimension(R.dimen.defStrokeSpacingTop);
            mStrokeSpacingRight = getResources().getDimension(R.dimen.defStrokeSpacingRight);
            mStrokeSpacingBottom = getResources().getDimension(R.dimen.defStrokeSpacingBottom);
        }

        setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (!b) {
                    clearHighlightedView();
                }
            }
        });
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                clearHighlightedView();
            }
        });
    }

    public void setStrokePosition(StrokePosition strokePosition) {
        mStrokePosition = strokePosition;
    }

    public void setStrokeWidth(float width) {
        mStrokeThickness = width;
    }

    public void setRadius(float radius) {
        mCornerRadiusX = radius;
        mCornerRadiusY = radius;
    }

    public void setCornerRadius(float x, float y) {
        mCornerRadiusX = x;
        mCornerRadiusY = y;
    }

    public void setStrokeColor(int color) {
        mStrokeColorSelected = color;
        mStrokeColorFocused = color;
    }

    public void setFocusColor(int color) {
        mStrokeColorFocused = color;
    }

    public void setSelectedColor(int color) {
        mStrokeColorSelected = color;
    }

    /**
     * Set margins.
     */
    public void setStrokeMargin(float left, float top, float right, float bottom) {
        mStrokeMarginLeft = left;
        mStrokeMarginTop = top;
        mStrokeMarginRight = right;
        mStrokeMarginBottom = bottom;
    }

    /**
     * Set spacing.
     */
    public void setStrokeSpacing(float left, float top, float right, float bottom) {
        mStrokeSpacingLeft = left;
        mStrokeSpacingTop = top;
        mStrokeSpacingRight = right;
        mStrokeSpacingBottom = bottom;
    }

    public void setFilled(boolean filled) {
        mIsFilled = filled;
    }

    public void setFillColor(int color) {
        mFillColor = color;
    }

    public void setFillAlpha(float alpha) {
        mFillAlpha = alpha;
    }

    public void setSelectorPosition(SelectorPosition position) {
        mSelectorPosition = position;
    }

    /**
     * Sets a stroke to the given view
     */
    public void highlightView(View view, boolean focused) {
        if (view == null) return;
        mStrokeColor = focused ? mStrokeColorFocused : mStrokeColorSelected;
        mStrokeCell = getAndAddStrokedView(view);
        mStrokeCellCurrentBounds.offsetTo(mStrokeCellOriginalBounds.left,
                mStrokeCellOriginalBounds.top);
        mStrokeCell.setBounds(mStrokeCellCurrentBounds);
        invalidate();
    }

    /**
     * Clears the stroke
     */
    public void clearHighlightedView() {
        mStrokeCell = null;
        invalidate();
        requestLayout();
    }

    /**
     * Creates the stroke cell with the appropriate bitmap and of appropriate
     * size. The stroke cell's BitmapDrawable is drawn on top or under of the bitmap every
     * single time an invalidate call is made.
     */
    private Drawable getAndAddStrokedView(View v) {
        int spacing = 0;
        switch (mStrokePosition) {
            case INSIDE:
                spacing = 0;
                break;
            case CENTER:
                spacing = (int) mStrokeThickness;
                break;
            case OUTSIDE:
                spacing = (int) mStrokeThickness * 2;
                break;
        }

        int w = v.getWidth();
        int h = v.getHeight();
        int w_scaled = v.getWidth() + spacing;
        int h_scaled = v.getHeight() + spacing;
        int top = v.getTop() - ((h_scaled - h) / 2);
        int left = v.getLeft() - ((w_scaled - w) / 2);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), getBitmap(w, h));

        mStrokeCellOriginalBounds = new Rect(
                (int) (left - mStrokeSpacingLeft),
                (int) (top - mStrokeSpacingTop),
                (int) (left + w_scaled + mStrokeSpacingRight),
                (int) (top + h_scaled + mStrokeSpacingBottom));
        mStrokeCellCurrentBounds = new Rect(mStrokeCellOriginalBounds);

        drawable.setBounds(mStrokeCellCurrentBounds);

        return drawable;
    }

    /**
     * Returns a stroked bitmap.
     */
    private Bitmap getBitmap(int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        switch (mStrokePosition) {
            case INSIDE:
            case CENTER:
            case OUTSIDE:
                RectF fillRect = new RectF(mStrokeMarginLeft, mStrokeMarginTop, w-mStrokeMarginRight, h-mStrokeMarginBottom);
                RectF cutoutRect = new RectF(mStrokeThickness+mStrokeMarginLeft, mStrokeThickness+mStrokeMarginTop, w-mStrokeThickness-mStrokeMarginRight, h-mStrokeThickness-mStrokeMarginBottom);

                Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
                stroke.setStyle(Paint.Style.FILL);
                stroke.setColor(mStrokeColor);
                canvas.drawRoundRect(fillRect, mCornerRadiusX, mCornerRadiusY, stroke);

                Paint cutout = new Paint(Paint.ANTI_ALIAS_FLAG);
                cutout.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
                canvas.drawRoundRect(cutoutRect, mCornerRadiusX, mCornerRadiusY, cutout);

                if (mIsFilled) {
                    Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
                    fill.setStyle(Paint.Style.FILL);
                    fill.setColor(mFillColor);
                    int alpha = (int) Math.ceil(mFillAlpha * 255);
                    fill.setAlpha(alpha);
                    canvas.drawRoundRect(cutoutRect, mCornerRadiusX, mCornerRadiusY, fill);
                }
                break;
        }

        return bitmap;
    }

    /**
     * onDraw gets invoked before all the child views are about to be drawn.
     * By overriding this method, the stroke cell (BitmapDrawable) can be drawn
     * under the recyclerviews's items whenever the recyclerviews is redrawn.
     */
    @Override
    public void onDraw(@NonNull final Canvas c) {
        if (mSelectorPosition == SelectorPosition.UNDER) {
            if (mStrokeCell != null) {
                mStrokeCell.draw(c);
            }
        }
        super.onDraw(c);
    }

    /**
     * dispatchDraw gets invoked when all the child views are about to be drawn.
     * By overriding this method, the stroke cell (BitmapDrawable) can be drawn
     * over the recyclerviews's items whenever the recyclerviews is redrawn.
     */
    @Override
    protected void dispatchDraw(@NonNull final Canvas c) {
        super.dispatchDraw(c);
        if (mSelectorPosition == SelectorPosition.OVER) {
            if (mStrokeCell != null) {
                mStrokeCell.draw(c);
            }
        }
    }

    public static enum StrokePosition {
        INSIDE(0), CENTER(1), OUTSIDE(2);

        private int value;

        StrokePosition(int value) {
            this.value = value;
        }

        public static StrokePosition getStrokePosition(int value) {
            switch (value) {
                case 1:
                    return CENTER;
                case 2:
                    return OUTSIDE;
                case 0:
                default:
                    return INSIDE;
            }
        }
    }

    public static enum SelectorPosition {
        OVER(0), UNDER(1);

        private int value;

        SelectorPosition(int value) {
            this.value = value;
        }

        public static SelectorPosition getSelectorPosition(int value) {
            switch (value) {
                case 1:
                    return UNDER;
                case 0:
                default:
                    return OVER;
            }
        }
    }
}
