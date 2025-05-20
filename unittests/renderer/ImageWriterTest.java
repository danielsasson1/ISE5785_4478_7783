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
                imageWriter.writePixel(i, j, new Color(0, 153, 148));
            }
        }
        for (int i = 0; i < 800; i += 50) {
            for (int j = 0; j < 500; j++) {
                imageWriter.writePixel(i, j, new Color(188, 0, 201));
            }
        }
        for (int i = 0; i < 500; i += 50) {
            for (int j = 0; j < 800; j++) {
                imageWriter.writePixel(j, i, new Color(188, 0, 201));
            }
        }
        imageWriter.writeToImage("TestImage");
    }



}