# NonTouchRecyclerView
RecyclerView for devices using arrow keys or D-Pad to navigate. (Ofcourse it Works with touch, but then the selector won't show) The cell selector is the backbone of this library and can be heavily customized.

### Screenshot (from the example)
![Screenshot](https://raw.githubusercontent.com/samyboy89/NonTouchRecyclerView/master/images/screenshot.png)

## Usage

1) Add the library as a dependency to your build.gradle

    dependencies {
        compile 'info.awesomedevelopment:nontouch-recyclerview:1.0.0-SNAPSHOT@aar'
    }
    
    
2) Add the application namespace to the root element in the XML you are using this view 

    xmlns:app="http://schemas.android.com/apk/res-auto"

3) Instead of RecyclerView use 

    no.development.awesome.library.StrokeRecyclerView

4) Customize the view
### XML
These are all the available stylables from XML:


    <developmen.awesome.library.StrokeRecyclerView
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:nt_selectorPosition="over"
        app:nt_strokePosition="inside"
        app:nt_filled="false"
        app:nt_fillAlpha="0.5"
        app:nt_fillColor="@android:color/darker_gray"
        app:nt_strokeColor="@android:color/white"
        app:nt_strokeWidth="3dp"
        app:nt_cornerRadius="3dp"
        app:nt_marginLeft="0dp
        app:nt_marginTop="0dp"
        app:nt_marginRight="0dp"
        app:nt_marginBottom="0dp"
        app:nt_spacingLeft="0dp"
        app:nt_spacingTop="0dp"
        app:nt_spacingRight="0dp"
        app:nt_spacingBottom="0dp"

### JAVA
You can modify the View programtically in Java as well, like this:

    StrokeRecyclerView recyclerView = (StrokeRecyclerView) findViewById(R.id.stroke_recycler_view);
    recyclerView.setSelectorPosition(StrokeRecyclerView.SelectorPosition.OVER);
    recyclerView.setFillColor(Color.BLUE);
    recyclerView.setFillAlpha(0.3f);
    recyclerView.setStrokeWidth(0f);

#### Extras
There are some extra setters when you do it in runtime. The first one is that you can change the corner radius of the x and y sides of the selector.

    recyclerView.setCornerRadius(float x, float y);
    
You can also set a focus and a selected color. Why you might want to do this is to indicate to the user that a cell is selected, but another View now has focus. Focus = currently focused and selected. Selected = only selected.

    recyclerView.setFocusColor(int color);
    recyclerView.setSelectedColor(int color);
    
5) In RecyclerView.Adapter onBindView method add these lines:

    holder.itemView.setFocusable(true);
    holder.itemView.setOnFocusChangeListener(new View.OnFocusChangeListener() {

        @Override
        public void onFocusChange(final View view, final boolean b) {
            recyclerView.highlightRecyclerView(view, b);
            
            // alt if you want selection to dissapear when recycleview looses focus
            // if (b) mSectionView.highlightRecyclerView(view, true);
            // else mSectionView.clearHighlightedRecyclerView();
        }
    });
    
6) Watch the magic happen!

## Attributes

| attr|type | comment
------------- | -------------|----------
nt_selectorPosition  | enum | Place the selector over or under the cell
nt_strokePosition  | enum | Stroke can be placed inside, center or ourside of the edge of the cell
nt_filled | boolean | Indicate if the selector should have a fill color
nt_fillAlpha | float | Opacity of the fill color
nt_fillColor | color | Fill color
nt_strokeColor | color | Stroke color
nt_strokeWidth | dimen | The strokes width. If set to 0, there will be no stroke
nt_cornerRadius | dimen | Corner radius of the selector. 0 is square.
nt_marginLeft | dimen | Margin left, this pushes the selector inwards on the left edge
nt_marginTop | dimen | Margin top, this pushes the selector inwards on the top edge
nt_marginRight | dimen | Margin right, this pushes the selector inwards on the right edge
nt_marginBottom | dimen | Margin bottom, this pushes the selector inwards on the bottom edge 
nt_spacingLeft | dimen | Spacing left, this pushes the selector outwards on the left edge
nt_spacingTop | dimen | Spacing top, this pushes the selector outwards on the top edge
nt_spacingRight | dimen | Spacing right, this pushes the selector outwards on the right edge
nt_spacingBottom | dimen | Spacing bottom, this pushes the selector outwards on the bottom edge 

## Licence

    Copyright 2015 Sam Mathias Weggersen

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
