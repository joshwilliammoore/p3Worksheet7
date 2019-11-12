/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import static org.junit.Assert.fail;
import static org.junit.Assert.fail;

/**
 *
 * @author James
 */
public class Workshop6TestHelper {

    public static final String TEST_SRC_FOLDER=  ".." + File.separator + 
                                                 "test_folders";
    public static final String TEST_SCRATCH_FOLDER = ".." + File.separator + 
                                                 "tmp_folders";

    
    
    public static void copyFolder(Path absSrcRoot, 
                                  Path absDestRoot,
                                  Path relativeDestFolder)  {
 //       try {
 //           Files.createDirectory(Paths.get(destFolder.toString()));
 //       }
 //       catch(IOException e){
 //           e.printStackTrace();
 //       }
       final String c = absSrcRoot.toString();
       final String d = absDestRoot.toString();
       final String e = relativeDestFolder.toString();
       
        try (Stream<Path> stream = Files.walk(absSrcRoot)) {
            stream.forEach(absSrcPath -> {

                try {
                    
//                    System.out.println("absSrcPath is " + absSrcPath);   
                    Path relSrcPath = Paths.get(c).relativize(absSrcPath);
//                    System.out.println("relSrcPath is " + relSrcPath);
                    Path absDestPath = Paths.get(d, e, relSrcPath.toString());
//                    System.out.println("absDestPath is " + absDestPath);
                    
//                    Path absSourcePath = Paths.get(commonFolder.toString(), 
//                                                   sourcePath.toString());
//                    
//                    Path relSrcPath = absSourcePath.relativize(Paths.get(absSrcFolder.toString()));
//                    Path absDestPath = Paths.get(commonFolder.toString(),
//                                                 destFolder.toString(), sourcePath.toString());
                    Files.copy(absSrcPath,absDestPath);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
        catch(IOException ex){
            ex.printStackTrace();
            fail();
        }

    }
    
//    public static void copyFolder(Path commonFolder, 
//                                  File absSrcFolder, 
//                                  File destFolder)  {
//        try {
//            Files.createDirectory(Paths.get(destFolder.toString()));
//        }
//        catch(IOException e){
//            e.printStackTrace();
//        }
//       
//        try (Stream<Path> stream = Files.walk(absSrcFolder.toPath())) {
//            stream.forEach(sourcePath -> {
//
//                try {
//                    
//                                        
//                    Path absSourcePath = Paths.get(commonFolder.toString(), 
//                                                   sourcePath.toString());
//                    
//                    Path relSrcPath = absSourcePath.relativize(Paths.get(absSrcFolder.toString()));
//                    Path absDestPath = Paths.get(commonFolder.toString(),
//                                                 destFolder.toString(), sourcePath.toString());
//                    Files.copy(absSourcePath,absDestPath);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//        }
//        catch(IOException e){
//            e.printStackTrace();
//            fail();
//        }
//
//    }
    public static boolean deleteFolderWithFileVisitor(String rootFolder) {
        Path p = Paths.get(rootFolder);
        if (!p.isAbsolute()) {
            Path currentWorkingFolder = Paths.get("").toAbsolutePath();
            rootFolder = Paths.get(currentWorkingFolder.toString(), rootFolder).toString();
        }

        SimpleFileVisitor myDeleteVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if(Files.isRegularFile(file)){
                    try {
                        Files.delete(file);
                        
                    } catch (Exception e) {}
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    try {
                        Files.delete(dir);
                    }
                    catch (IOException e) {
                        //e.printStackTrace();                        
                    }
                return FileVisitResult.CONTINUE;               
            }
        };
        try {
            Files.walkFileTree(Paths.get(rootFolder), myDeleteVisitor);
        } catch (IOException ex) {
//            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public static boolean deleteFolderRecursively(File dir) {

        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFolderRecursively(children[i]);
                if (!success) {
                    return false;
                }
                try {
                    Files.delete(Paths.get(children[i].toString()));
                }
                catch (IOException e){
                    
                }
            }
        }
        try {
            Files.delete(Paths.get(dir.toString()));
        }
        catch (IOException e) {
            
        }
        return true;
    }

    static void print1(Set<Set<MediaItem>> result) {
        for(Set<MediaItem> s : result) {
            for (MediaItem m : s) {
                System.out.println(m.getAbsolutePath());
            }
        }
    }
    static void print2(Set<MediaItem> result) {
        for(MediaItem s : result) {
                System.out.println(s.getAbsolutePath());
            
        }
    }

    static int getNumLikeThis(MediaItem thisItem, Set<MediaItem> otherItems) {
        Path p = Paths.get(thisItem.getAbsolutePath());
        String filenameToSearchFor = p.getFileName().toString();

        int num=0;
        for(MediaItem m : otherItems) {
            Path otherP = Paths.get(m.getAbsolutePath());
            String otherFilename = otherP.getFileName().toString();
            if(filenameToSearchFor.equals(otherFilename)) {
//                System.out.println("same: " + filenameToSearchFor + " is the same as " + otherFilename);
                num++;
            }
            else {
//                System.out.println("different: " + filenameToSearchFor + 
//                        " is NOT the same as " + otherFilename);                
            }
        }
        return num;    
    }

    static boolean filesExist(Set<MediaItem> allMediaItems) {
        for(MediaItem m : allMediaItems) {
            if(Files.exists(Paths.get(m.getAbsolutePath()))==false)
                return false;
        }
        return true;
    }

    static boolean filesDontExist(Set<MediaItem> allDuplicates) {
        for(MediaItem m : allDuplicates){
            if(Files.exists(Paths.get(m.getAbsolutePath())))
                return false;
        }
        return true;    
    }

   

}
