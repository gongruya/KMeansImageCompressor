package org.cs576;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.util.Arrays;

/**
 * Created by gongruya on 2/4/17.
 */
public class GrayImage {
    private int width;
    private int height;
    private byte[] bytes;   //row, col

    public GrayImage(final ImageSize imageSize) {
        this.width = imageSize.width;
        this.height = imageSize.height;
        this.bytes = new byte[height * width];
    }

    public GrayImage(final ImageSize imageSize,
                     final byte[] bytes) {
        this.width = imageSize.width;
        this.height = imageSize.height;
        this.bytes = Arrays.copyOf(bytes, bytes.length);
    }

    public double getPixel(final int i,
                           final int j) {

        double t = bytes[i * width + j];
        if (t >= 0) {
            return t / 255.0;
        }
        return (t + 256) / 255.0;
    }

    /**
     * Convert to BufferedImage type.
     * @return a BufferedImage instance
     */
    public BufferedImage toBufferedImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        byte[] buffered = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        for (int i = 0; i < bytes.length; ++i) {
            buffered[i] = bytes[i];
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

}
