
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author akash
 */
public class ReadNumbers {
    
    private int makeTxtArray(BufferedImage image) throws IOException {
        
        int[][] numberArray = new int[image.getHeight()][image.getWidth()];
        int numberOfNonWhitePixels = 0;
        for (int xPixel = 0; xPixel < image.getHeight(); xPixel++) {
            for (int yPixel = 0; yPixel < image.getWidth(); yPixel++) {
                int color = image.getRGB(yPixel, xPixel);
                int red = (color & 0xff);

                numberArray[xPixel][yPixel] = red;
                if (red != 255){
                    numberOfNonWhitePixels++;
                }
                

//                if (red > 230) {
//                    numberArray[xPixel][yPixel] = 1;
//                } else {
//                    numberArray[xPixel][yPixel] = 0;
//                }
                
            }
        }
        return numberOfNonWhitePixels;

    }

    public void listFilesAndFolders(String directoryName) throws IOException {
        File directory = new File(directoryName);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        FileAndPixelCount[] fListithCount;
            for (File file : fList) {
               BufferedImage image = ImageIO.read(file);
               int blackPixelCount = makeTxtArray(image);
               System.out.println(file.getName() + " " + blackPixelCount);
               new FileAndPixelCount(file.getName() , blackPixelCount);
            }
        }
    
    
    
    public class FileAndPixelCount {
        int numGreyPixels;
        String fileName;
        public FileAndPixelCount(String ifileName, int inumGreyPixels) {
            numGreyPixels = inumGreyPixels;
            fileName = ifileName;
        }
    }
}

