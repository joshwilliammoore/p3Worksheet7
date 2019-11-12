/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author James
 */

public interface DuplicateFinder {

    // classes that implement DuplicateFinder must include a method for this:
    boolean areDuplicates  (MediaItem m1, MediaItem m2); 
    
    default public Set<Set<MediaItem>>  getAllDuplicates (Set<MediaItem> allMediaItems){
       Set<Set<MediaItem>> allDuplicates = new HashSet<>();        
        Set<MediaItem> possibleDuplicates = new HashSet<>();
        possibleDuplicates.addAll(allMediaItems);

        while(possibleDuplicates.iterator().hasNext())
        {
            MediaItem m = possibleDuplicates.iterator().next();
            Set<MediaItem> duplicates = getDuplicates(possibleDuplicates, m);
            if(duplicates.size()>1) {
                allDuplicates.add(duplicates);
            }
            possibleDuplicates.removeAll(duplicates);
        }
        return allDuplicates;
    }
   
    default public Set<MediaItem> getDuplicates  (Set<MediaItem> inThisList, 
                                                  MediaItem toThis) {
        Set<MediaItem> duplicates = new HashSet<>();        
        for(MediaItem m : inThisList) {
            if(areDuplicates(m,toThis)){
                duplicates.add(m);
            }
        }
        return duplicates;      
    }
          
    default  Set<MediaItem> getMissingItems        (Set<MediaItem> myCollection, 
                                                    Set<MediaItem> yourCollection)
    {
        Set<MediaItem> missingItems = new HashSet<>();
        for(MediaItem item : yourCollection) {
            Set<MediaItem> duplicates1 = getDuplicates(myCollection, item);
            if (duplicates1.isEmpty()) {
               Set<MediaItem> duplicates2 = getDuplicates(missingItems, item);
                if(duplicates2.isEmpty()) {
                    missingItems.add(item);
                }
            }
        }
        return missingItems;
    }
            
}
