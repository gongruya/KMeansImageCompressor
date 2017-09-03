package org.cs576;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class ImageCompression {

    public static void main(String[] args) {
        String fileName = args[0];
        int levels = Integer.parseInt(args[1]);
        String format = fileName.substring(Math.max(0, fileName.length() - 4));

        ImageSize size = ImageSize.STANDARD;
        ImageDisplay imageDisplay = new ImageDisplay(size.width, size.height);

        try {
            byte[] bytes = new byte[size.height * size.width];
            File file = new File(fileName);
            InputStream is = new FileInputStream(file);
            if (format.equals(".raw")) {
                is.read(bytes);
                GrayImage image = new GrayImage(size, bytes);
                imageDisplay.setImage1(image.toBufferedImage());
                KMeansCompression kmeans = new KMeansCompression(image);
                imageDisplay.setImage2(kmeans.quantize(levels));
            } else if (format.equals(".rgb")) {
                bytes = new byte[size.height * size.width * 3];
                is.read(bytes);
                RGBImage image = new RGBImage(size, bytes);
                imageDisplay.setImage1(image.toBufferedImage());
                KMeansCompression kmeans = new KMeansCompression(image);
                BufferedImage result = kmeans.quantize(levels);
                imageDisplay.setImage2(result);
            } else {
                throw new IllegalArgumentException("Illegal image type");
            }

            is.close();
        } catch (FileNotFoundException e) {
            System.err.println("The input image not found.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
