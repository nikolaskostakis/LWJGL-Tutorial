/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikolas Kostakis
 */
public class FileUtils {

    /**
     * Opens the file in the given path and returns its content as a string
     *
     * @param path the path of the file
     * @return the content of the file as a string
     */
    public static String loadAsString(String path) {
        String shaderCode = "";
        try {

            File shaderFile = new File(path);
            Scanner shaderReader = new Scanner(shaderFile);
            
            //
            while (shaderReader.hasNextLine()) {
                shaderCode += shaderReader.nextLine() + '\n';
            }
            shaderReader.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shaderCode;
    }
}
