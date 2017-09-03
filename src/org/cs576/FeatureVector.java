package org.cs576;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by gongruya on 3/9/17.
 */
public class FeatureVector {
    private double[] val;

    public static FeatureVector random(final int dim) {
        double[] val = new double[dim];
        Random rand = new Random();
        for (int i = 0; i < dim; ++i) {
            val[i] = rand.nextDouble();
        }
        return new FeatureVector(val);
    }

    public FeatureVector(final int dim) {
        val = new double[dim];
        Arrays.fill(val, 0);
    }
    public FeatureVector(final double[] val) {
        this.val = val;
    }

    public int getDim() {
        return val.length;
    }

    public double[] getVal() {
        return val;
    }

    public double distance(final FeatureVector other) {
        double ret = 0;
        for (int i = 0; i < val.length; ++i) {
            ret += (val[i] - other.val[i]) * (val[i] - other.val[i]);
        }
        return (ret);
    }

    public FeatureVector plus(final FeatureVector other) {
        FeatureVector ret = new FeatureVector(getDim());
        for (int i = 0; i < val.length; ++i) {
            ret.val[i] = val[i] + other.val[i];
        }
        return ret;
    }

    public FeatureVector minus(final FeatureVector other) {
        FeatureVector ret = new FeatureVector(getDim());
        for (int i = 0; i < val.length; ++i) {
            ret.val[i] = val[i] - other.val[i];
        }
        return ret;
    }

    public double dot(final FeatureVector other) {
        double prod = 0;
        for (int i = 0; i < getDim(); ++i) {
            prod += val[i] * other.val[i];
        }
        return prod;
    }

    public FeatureVector times(final double scale) {
        FeatureVector ret = new FeatureVector(getDim());
        for (int i = 0; i < val.length; ++i) {
            ret.val[i] = val[i] * scale;
        }
        return ret;
    }

    public String toString() {
        return Arrays.toString(val);
    }

}
