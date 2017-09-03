package org.cs576;

/**
 * Created by gongruya on 2/4/17.
 */
public class ImageSize {
    public static final ImageSize STANDARD = new ImageSize(352, 288);
    public int width;
    public int height;
    public ImageSize(final int width,
                     final int height) {
        this.width = width;
        this.height = height;
    }
    public ImageSize() {
        this(0, 0);
    }
    public double aspectRatio() {
        return (double) width / height;
    }
}
