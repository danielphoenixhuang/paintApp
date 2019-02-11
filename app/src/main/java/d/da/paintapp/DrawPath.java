package d.da.paintapp;

import android.graphics.Path;

public class DrawPath {

    public int color;
    public int lineWidth;
    public Path path;

    public DrawPath(int color, int lineWidth, Path path)
    {
        this.color = color;
        this.lineWidth = lineWidth;
        this.path = path;
    }

}
