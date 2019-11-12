/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author James
 */
public interface FileService {
    Set<MediaItem>          getAllMediaItems    (String rootFolder);
    Set<MediaItem>          getItemsToRemove    (Set<Set<MediaItem>> duplicates);
    boolean                 removeFiles         (Set<MediaItem> listToRemove);
    Map<Path, MediaItem>    getDestinationPaths (Set<MediaItem> collectionToCopy, 
                                                 Path sourceRoot, 
                                                 Path destRoot);
    boolean                 copyFiles           (Map<Path, MediaItem> filesToCopy);
     
}
