
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import javax.imageio.ImageIO;
import org.apache.commons.io.FileUtils;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author akash
 */
public class BitMapToNumberMap {

    int[] histogram = new int[256];
    double SCALE_FACTOR = 1.0/10;
    double SQUISH_FACTOR = 300.0/550.0;

    private int[][] makeTxtArray(BufferedImage image) throws IOException {
        
        int height = image.getHeight();
        int width = image.getWidth();
        height = (int)(height * SCALE_FACTOR * SQUISH_FACTOR);
        width = (int)(width * SCALE_FACTOR);
       
        Image thumbnail = image.getScaledInstance(width, height, Image.SCALE_REPLICATE);
        image = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        image.getGraphics().drawImage(thumbnail, 0, 0, null);
        int[][] numberArray = new int[height][width];
        System.out.println("image height is " + height + "image width is " + width);
        System.out.println("len is " +height * width);
        for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
            for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
                int color = image.getRGB(yPixel, xPixel);
                int alpha = (color>>24 & 0xff);

                numberArray[xPixel][yPixel] = alpha;
                histogram[alpha]++;
               // System.out.println(String.format("0x%08X", color));

            }
        }

        return numberArray;

    }

    public void printArray() {
        for (int x = 0; x < histogram.length; x++) {
            System.out.println(x + ", " + histogram[x]);
        }

    }

    void generateHTMLfile(String content) throws IOException {
        // Read existing template file
        File htmlTemplateFile = new File("template.html");
        String htmlString = FileUtils.readFileToString(htmlTemplateFile);

        htmlString = htmlString.replace("$content", content);

        // Create a new HTML file
        File newHtmlFile = new File("prime.html");
        FileUtils.writeStringToFile(newHtmlFile, htmlString, "UTF-8");

    }

    private String generateContent(int[][] numberArray, boolean addNewLine) {
        String content = "";
        for (int xPixel = 0; xPixel < numberArray.length; xPixel++) {
            for (int yPixel = 0; yPixel < numberArray[0].length; yPixel++) {
                content = content + convertIntensityToDigit(numberArray[xPixel][yPixel]);
            }
            if (addNewLine) {
                content = content + "\n";
            }
        }
        return content;
    }

    private int convertIntensityToDigit(int in) {

        switch (in) {
            case 255:
                return 1;
            case 254:
                return 7;
            case 253:
                return 2;
            case 228:
                return 3;
            default:
                if (in <= (100 + 2 * (255 - 100) / 4)) {
                    return 8;
                }
                if (in <= (100 + 3 * (255 - 100) / 4)) {
                    return 0;
                }
                if (in <= (100 + 4 * (255 - 100) / 4)) {
                    return 4;
                }
        }
        return 8;

    }

    public void makeOutputFiles(String BMPfileName) throws IOException {

        BufferedImage image = ImageIO.read(new File(BMPfileName));
        File newTxtFile = new File("prime.txt");
        FileUtils.writeStringToFile(newTxtFile, generateContent(makeTxtArray(image), false), "UTF-8");
        generateHTMLfile(generateContent(makeTxtArray(image), true));

    }

}
