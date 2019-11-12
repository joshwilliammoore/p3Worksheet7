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
public class DuplicateFindFromMetaData implements DuplicateFinder{


    @Override
    public boolean areDuplicates(MediaItem m1, MediaItem m2) {
        if(m1==m2)
            return true;
        
        if((m1.getAlbum()==null) || 
           !m1.getAlbum().equalsIgnoreCase(m2.getAlbum().trim())) {
            return false;
        }
        if((m1.getArtist()==null) || 
           !m1.getArtist().equalsIgnoreCase(m2.getArtist().trim())) {
            return false;          
        }
        if((m1.getTitle()==null) || 
           !m1.getTitle().equalsIgnoreCase(m2.getTitle().trim())) {
            return false;            
        }
        return true;
    }
  
   /* 
     @Override
    public boolean areDuplicates(MediaItem m1, MediaItem m2) {
       // throw new UnsupportedOperationException("Not written yet."); //To change body of generated methods, choose Tools | Templates.
    return !m1.getAbsolutePath().equals(m2.getAbsolutePath()) 
                && m1.getArtist().trim().toLowerCase().equals(m2.getArtist().trim().toLowerCase())
                && m1.getAlbum().trim().toLowerCase().equals(m2.getAlbum().trim().toLowerCase())
                && m1.getTitle().trim().toLowerCase().equals(m2.getTitle().trim().toLowerCase());
    }
*/

}
