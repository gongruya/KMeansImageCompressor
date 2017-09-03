package org.cs576;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;

/**
 * Created by gongruya on 2/4/17.
 */
public class ImageDisplay extends JFrame {
    private JLabel imageLabel1;
    private JLabel imageLabel2;
    private JLabel descriptionLabel;
    private int width;
    private int height;

    /**
     * Constructor w/ width and height.
     * @param width image width
     * @param height image height
     */
    public ImageDisplay(final int width, final int height) {
        super("ImageDisplay");

        this.width = width;
        this.height = height;

        GridBagLayout gLayout = new GridBagLayout();

        Container contentPane = getContentPane();

        contentPane.setLayout(gLayout);
        descriptionLabel = new JLabel(width + "x" + height);
        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);

        //Placeholder
        imageLabel1 = new JLabel(new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)));
        imageLabel2 = new JLabel(new ImageIcon(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        contentPane.add(descriptionLabel, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 1;
        contentPane.add(imageLabel1, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        contentPane.add(imageLabel2, c);
        pack();
        setVisible(true);
    }

    /**
     * Update image1 in JFrame.
     * @param image BufferedImage
     */
    public void setImage1(final BufferedImage image) {
        imageLabel1.setIcon(new ImageIcon(image));
    }

    /**
     * Update image2 in JFrame.
     * @param image BufferedImage
     */
    public void setImage2(final BufferedImage image) {
        imageLabel2.setIcon(new ImageIcon(image));
    }

    /**
     * Append string to the description label.
     * @param description text string
     */
    public void setDescription(final String description) {
        descriptionLabel.setText(width + "x" + height + ", " + description);
    }

}
