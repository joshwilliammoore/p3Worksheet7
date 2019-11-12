/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.junit.Assert.fail;

/**
 *
 * @author James
 */
public interface FileStore {

    public List<String> getCollectionBFilenames();
    public List<String> getCollectionAFilenames();
    public Set<Set<MediaItem>> getAllDuplicates(String rootTestFolder,
            MediaInfoSource myMediaInfoSource);

    public String getRootFolder();


    default List<String> relativeToAbsolute(List<String> relativeFilenames,
                                            String absoluteTestFolder) {
        List<String> absoluteFilenames = new ArrayList<>();
        for (String filename : relativeFilenames) {
            String abs = Paths.get(absoluteTestFolder,
                                   getRootFolder(),
                                   filename).toString();
            absoluteFilenames.add(abs);
        }
        return absoluteFilenames;
    

    }

    default Set<MediaItem> getCollectionAItems(String absoluteTestFolder,
            MediaInfoSource myMediaInfoSource) {
        List<String> relativeFilenames = getCollectionAFilenames();
        List<String> absoluteFilenames = relativeToAbsolute(relativeFilenames, absoluteTestFolder);
        return filenamesToMediaItems(absoluteFilenames, myMediaInfoSource);
    }
    default  Set<MediaItem> getCollectionBItems(String absoluteTestFolder,
            MediaInfoSource myMediaInfoSource) {
        List<String> relativeFilenames = getCollectionBFilenames();
        List<String> absoluteFilenames = relativeToAbsolute(relativeFilenames, absoluteTestFolder);
        return filenamesToMediaItems(absoluteFilenames, myMediaInfoSource);
    }

    default  Set<MediaItem> getAllMediaItems(String absoluteTestFolder,
            MediaInfoSource myMediaInfoSource) {
        List<String> relativeFilenames = new ArrayList<String>();
        relativeFilenames.addAll(getCollectionAFilenames());
        relativeFilenames.addAll(getCollectionBFilenames());
        List<String> absoluteFilenames = relativeToAbsolute(relativeFilenames, absoluteTestFolder);
        return filenamesToMediaItems(absoluteFilenames, myMediaInfoSource);
    }


    default Set<MediaItem> filenamesToMediaItems(List<String> absoluteFilenames,
            MediaInfoSource myMediaInfoSource) {
        Set<MediaItem> output = new HashSet<MediaItem>();

        for (String filename : absoluteFilenames) {
            MediaItem m = new MediaItem().setAbsolutePath(filename);
            if (myMediaInfoSource != null) {
                try {
                    myMediaInfoSource.addMediaInfo(m);
                } catch (Exception e) {
                    fail(e.toString());
                }
            }
            output.add(m);
        }
        return output;
    }

    public Set<MediaItem> getItemsFromBmissingFromA(String toString, MediaInfoSource mediaInfoSource);
    public Set<MediaItem> getItemsFromAmissingFromB(String toString, MediaInfoSource mediaInfoSource);

    default Map<Path, MediaItem> getDestinationPathsAtoB(String parentOfTestFolder, String nameOfDestFolder)
    {
       Map<Path, MediaItem> output = new HashMap<>();
       List<String> relativeFilenames = getCollectionAFilenames();
       for(String filename : relativeFilenames)
       {
           Path absFolder = Paths.get(parentOfTestFolder, this.getRootFolder(), filename);
           MediaItem m = new MediaItem().setAbsolutePath(absFolder.toString());
           Path p = Paths.get(filename);
           int n = p.getNameCount();
           try
           {
           Path subPath = null;
           if(n>2) {
               subPath = p.subpath(1, n-1);
           } else
           {
               subPath = p.getFileName();
           }
           Path destFilename = Paths.get(parentOfTestFolder, this.getRootFolder(), nameOfDestFolder, subPath.toString());
          output.put(destFilename, m);
           }
           catch (Exception e){
               String fgfg = "eek - " + e.toString();
               System.out.println(fgfg); 
               String fgf = e.toString();
           }
       }
        return output;
        
        
    }
    default Map<Path, MediaItem> getDestinationPaths(List<String> relativeFilenames, 
                                            Path absSrcFolder, Path absDestFolder)
    {
       Map<Path, MediaItem> output = new HashMap<>();
       for(String filename : relativeFilenames)
       {
           Path absFolder = Paths.get(absSrcFolder.toString(), filename);
           MediaItem m = new MediaItem().setAbsolutePath(absFolder.toString());
           Path p = Paths.get(filename);
           int n = p.getNameCount();
           try
           {
           Path subPath = null;
           if(n>2) {
               subPath = Paths.get(p.subpath(1, n-1).toString(), p.getFileName().toString());
           } else
           {
               subPath = p.getFileName();
           }
           Path destFilename = Paths.get(absDestFolder.toString(), subPath.toString());
           
           output.put(destFilename, m);
           }
           catch (Exception e)
           {
               System.out.println("eek");
           }
       }
        return output;
                      
    }
    
    
    
    

}
