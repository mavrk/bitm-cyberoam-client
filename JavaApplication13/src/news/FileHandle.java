package news;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sanatt Abrol
 */
public class FileHandle {

    public boolean CheckForFileInParentDirectory(String subFolder,String fileName)
    {
        try{
            String directoryPath = getPathToRunnable() + File.separator + subFolder;
            return new File(new File(directoryPath), fileName).exists();
        }
        catch(URISyntaxException e){ return true;}
    }
    
    public boolean CheckForFolderInParentDirectory(String subFolder){
        try{
            String dirName = getPathToRunnable() + File.separator + subFolder; 
            return Files.isDirectory(Paths.get(dirName));
        }
        catch(URISyntaxException ex){return false;}
    
    }
    public String getPathToRunnable() throws URISyntaxException
    {
        return new File(XMLClass.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParentFile().getAbsolutePath();
    }

    public void CreateFileIfNotExists(String subFolder, String fileName) throws URISyntaxException, IOException{
        if(!CheckForFileInParentDirectory(subFolder, fileName))
        {
            String dirName = getPathToRunnable() + File.separator + subFolder; 
            if(!CheckForFolderInParentDirectory(subFolder))
            {
                CreateSubFolder(getPathToRunnable(), subFolder);
            }
            
            File dir = new File (dirName);
            System.out.println(dirName);
            File actualFile = new File (dir, fileName);
            FileWriter fw = new FileWriter(actualFile);
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write("HEllo!! Its me");
                bw.flush();
            }
        }
        
    }
    
    
    public void CreateSubFolder(String rootDirectory, String subFolder)
    {
        new File(rootDirectory + File.separator + subFolder).mkdir();
    }
}
