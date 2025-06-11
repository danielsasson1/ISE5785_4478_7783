package renderer;

import org.junit.jupiter.api.Test;
import primitives.Color;


class ImageWriterTest {
    //Test for doing a simple image
    //The image will have a net of 10x16 pixels
    //The resolution is 500x800 pixels
    @Test
    void testWriteToImage() {
        ImageWriter imageWriter = new ImageWriter(800, 500);
        for (int i = 0; i < 800; i++) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.YELLOW));
            }
        }//a for loop to write the pixels of the background
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(java.awt.Color.RED));
            }
        }//a for loop to write the pixels of the vertical lines
        for (int i = 0; i < 500; i += 50) {
            for (int j = 0; j < 800; j++) {
                imageWriter.writePixel(j, i, new Color(188, 0, 201));
            }
        }//a for loop to write the pixels of the horizontal lines
        imageWriter.writeToImage("TestImage");//Write the image to a file
    }
}