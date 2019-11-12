/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import ku.piii2019.bl2.FileService;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author James
 */
public class FileServiceImpl implements FileService {

    @Override
    public Set<MediaItem> getAllMediaItems(String rootFolder) {
        Path p = Paths.get(rootFolder);
        if (!p.isAbsolute()) {
            Path currentWorkingFolder = Paths.get("").toAbsolutePath();
            rootFolder = Paths.get(currentWorkingFolder.toString(), rootFolder).toString();
        }
        Set<MediaItem> items = new HashSet<>();
        SimpleFileVisitor myVisitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith("mp3")) {
                    MediaItem m = new MediaItem();
                    m.setAbsolutePath(file.toString());
                    items.add(m);
                }
                return FileVisitResult.CONTINUE;
            }
        };
        try {
            Files.walkFileTree(Paths.get(rootFolder), myVisitor);
        } catch (IOException ex) {}
        return items;
    }



    @Override
    public Set<MediaItem> getItemsToRemove(Set<Set<MediaItem>> duplicates) {
        Set<MediaItem> outputSet = new HashSet<>();
        for(Set<MediaItem> s : duplicates)
        {
            MediaItem firstItem = s.iterator().next();
            outputSet.addAll(s);
            outputSet.remove(firstItem);
        }
        return outputSet;
    }

    @Override
    public boolean removeFiles(Set<MediaItem> listToRemove) {
        boolean retVal = true;
        for(MediaItem m : listToRemove)
        {
            try
            {
                Files.delete(Paths.get(m.getAbsolutePath()));
            }
            catch(Exception e){
                e.printStackTrace();
                retVal = false;
            }
        }
        return retVal;
    }

    @Override
    public Map<Path, MediaItem> getDestinationPaths(Set<MediaItem> collectionToCopy, 
                                                    Path sourceRoot, Path destRoot) {
    
        Map<Path, MediaItem> output = new HashMap<>();
        
        Path sourceDir = sourceRoot.getFileName();
        Path absDestFolder = Paths.get(destRoot.toString(), sourceDir.toString());
        if(Files.exists(absDestFolder))
        {
            absDestFolder = Paths.get(destRoot.toString(), sourceDir.toString()+ "2");
        }
        for(MediaItem item : collectionToCopy)
        {
            Path originalFilename = Paths.get(item.getAbsolutePath());
            Path relativePath = sourceRoot.relativize(originalFilename);
            Path destPath = Paths.get(absDestFolder.toString(), relativePath.toString());
            output.put(destPath, item);
        }
        return output;
    }

    @Override
    public boolean copyFiles(Map<Path, MediaItem> filesToCopy) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
