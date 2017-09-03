package org.cs576;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.util.Arrays;

/**
 * Created by gongruya on 2/4/17.
 */
public class RGBImage {
    private int width;
    private int height;
    private byte[] bytes;   //RGB, row, col


    public RGBImage(final ImageSize imageSize) {
        this.width = imageSize.width;
        this.height = imageSize.height;
        this.bytes = new byte[3 * height * width];
    }

    public RGBImage(final ImageSize imageSize,
                    final byte[] bytes) {
        this.width = imageSize.width;
        this.height = imageSize.height;
        this.bytes = Arrays.copyOf(bytes, bytes.length);

    }

    public RGBImage(final GrayImage r,
                    final GrayImage g,
                    final GrayImage b) {
        this.width = r.size().width;
        this.height = r.size().height;
        this.bytes = new byte[3 * height * width];
        System.arraycopy(r.getBytes(), 0, bytes, 0, height * width);
        System.arraycopy(g.getBytes(), 0, bytes, height * width, height * width);
        System.arraycopy(b.getBytes(), 0, bytes, height * width * 2, height * width);

    }

    public RGBImage(final ImageSize imageSize,
                    final BufferedImage image) {
        this.width = imageSize.width;
        this.height = imageSize.height;
        this.bytes = new byte[3 * height * width];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int rgb = image.getRGB(j, i);
                bytes[getIndex(0, i, j)] = (byte) ((rgb >> 16) & 0xff);
                bytes[getIndex(1, i, j)] = (byte) ((rgb >> 8) & 0x0ff);
                bytes[getIndex(2, i, j)] = (byte) ((rgb) & 0x0ff);
            }
        }
    }

    /**
     * Convert to BufferedImage type.
     * @return a BufferedImage instance
     */
    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                //int index = j * width + i;
                int r = bytes[getIndex(0, i, j)];
                int g = bytes[getIndex(1, i, j)];
                int b = bytes[getIndex(2, i, j)];
                int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                image.setRGB(j, i, pix);
            }
        }
        return image;
    }

    /**
     * Get the size of image.
     * @return an object of width, height
     */
    public ImageSize size() {
        return new ImageSize(width, height);
    }

    /**
     * Get bytes array of the image
     * @return bytes array
     */
    public byte[] getBytes() {
        return bytes;
    }

    /**
     * Get the index of a byte array.
     * @param k color channel
     * @param i vertical coordinate (row)
     * @param j horizontal coordinate (column)
     * @return 1-d index
     */
    private int getIndex(final int k, final int i, final int j) {
        return k * width * height + i * width + j;
    }

    public double getPixel(final int k, final int i, final int j) {
        double t = bytes[getIndex(k, i, j)];
        if (t >= 0) {
            return t / 255.0;
        }
        return (t + 256) / 255.0;
    }

}
