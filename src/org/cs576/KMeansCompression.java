package org.cs576;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by gongruya on 3/9/17.
 */
public class KMeansCompression {
    /**
     * Iterations until stop.
     */
    private static final int MAX_ITERATIONS = 80;

    /**
     * Feature vectors.
     */
    private FeatureVector[] features;
    /**
     * Which cluster do the features belong to.
     */
    private int[] category;
    /**
     * Cluster centers.
     */
    private FeatureVector[] centers;

    private int width;
    private int height;
    private int dim;


    public KMeansCompression(final GrayImage image) {
        //Dimension 2
        width = image.size().width;
        height = image.size().height;
        dim = 2;
        int totalFeatures = width / 2 * height;
        features = new FeatureVector[totalFeatures];
        category = new int[totalFeatures];
        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; j += 2) {
                double pixel1 = image.getPixel(i, j);
                double pixel2 = image.getPixel(i, j + 1);
                features[k++] = new FeatureVector(new double[]{pixel1, pixel2});
            }
        }
    }

    public KMeansCompression(final RGBImage image) {
        //Dimension 3
        width = image.size().width;
        height = image.size().height;
        dim = 3;
        int totalFeatures = width * height;
        features = new FeatureVector[totalFeatures];
        category = new int[totalFeatures];
        int k = 0;
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                double r = image.getPixel(0, i, j);
                double g = image.getPixel(1, i, j);
                double b = image.getPixel(2, i, j);
                FeatureVector rgb = new FeatureVector(new double[]{r, g, b});
                features[k++] = rgbToYUV(rgb);
            }
        }
    }

    public void kmeans(final int levels) {
        //Pick k random features as initial center
        Random rand = new Random();
        centers = new FeatureVector[levels];
        centers = Arrays.copyOf(features, levels);
        int iter = MAX_ITERATIONS;
        while (iter-- > 0) {
            //Assign features

            for (int i = 0; i < features.length; ++i) {
                category[i] = nearest(features[i]);
            }
            //Update centers
            int[] count = new int[levels];
            FeatureVector[] newCenters = new FeatureVector[centers.length];

            for (int i = 0; i < centers.length; ++i) {
                //Get a new set of centers
                newCenters[i] = new FeatureVector(dim);
            }

            for (int i = 0; i < features.length; ++i) {
                //Compute average
                newCenters[category[i]] = newCenters[category[i]].plus(features[i]);
                ++count[category[i]];
            }

            for (int i = 0; i < centers.length; ++i) {
                if (count[i] > 0) {
                    centers[i] = newCenters[i].times(1.0 / count[i]);
                }
            }
        }
        System.out.println(Arrays.toString(centers));
    }

    public BufferedImage quantize(final int levels) {
        kmeans(levels);
        if (dim == 2) {
            return generateGrayImage();
        } else {
            return generateRGBImage();
        }
    }

    private int nearest(final FeatureVector feat) {
        int ret = 0;
        double minDist = Double.MAX_VALUE;
        //Pick a center
        for (int i = 0; i < centers.length; ++i) {
            double dist;
            dist = feat.distance(centers[i]);
            if (dist < minDist) {
                minDist = dist;
                ret = i;
            }
        }
        return ret;
    }

    public BufferedImage generateGrayImage() {
        byte[] bytes = new byte[width * height];
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; j += 2) {
                int idx = (i * width + j) >> 1;
                double[] fea = centers[category[idx]].getVal();
                bytes[i * width + j] = (byte) (fea[0] * 255);
                bytes[i * width + j + 1] = (byte) (fea[1] * 255);
            }
        }
        return new GrayImage(new ImageSize(width, height), bytes).toBufferedImage();
    }

    public BufferedImage generateRGBImage() {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                int idx = (i * width + j);
                double[] rgb = yuvToRGB(centers[category[idx]]).getVal();
                int r = (int) (rgb[0] * 255);
                int g = (int) (rgb[1] * 255);
                int b = (int) (rgb[2] * 255);
                int pix = 0xff000000 | ((r & 0xff) << 16) | ((g & 0xff) << 8) | (b & 0xff);
                image.setRGB(j, i, pix);
            }
        }
        return image;
    }

    private FeatureVector rgbToYUV(FeatureVector f) {
        double r = f.getVal()[0];
        double g = f.getVal()[1];
        double b = f.getVal()[2];
        double y = 0.299 * r + 0.587 * g + 0.114 * b;
        double u = -0.147 * r - 0.289 * g + 0.436 * b;
        double v = 0.615 * r - 0.515 * g - 0.100 * b;
        return new FeatureVector(new double[]{y, u, v});
    }
    private FeatureVector yuvToRGB(FeatureVector f) {
        double y = f.getVal()[0];
        double u = f.getVal()[1];
        double v = f.getVal()[2];
        double r = y + 1.140 * v;
        double g = y - 0.395 * u - 0.581 * v;
        double b = y + 2.032 * u;
        return new FeatureVector(new double[]{r, g, b});
    }
}
