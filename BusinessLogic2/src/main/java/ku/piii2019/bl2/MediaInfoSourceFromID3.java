/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import com.mpatric.mp3agic.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author James
 */
public class MediaInfoSourceFromID3 implements MediaInfoSource {

    public void addMediaInfo(MediaItem m) throws Exception {
        Mp3File mp3 = new Mp3File(m.getAbsolutePath());
        ID3v1 tag = mp3.getId3v1Tag();
        if(tag==null){
            tag = mp3.getId3v2Tag();           
        }
        else{
            System.out.println("got v1 tag");
        }
            
        if(tag==null) {
            throw new Exception();
        }
        try {
            m.setTitle(tag.getTitle());
            m.setAlbum(tag.getAlbum());
            m.setArtist(tag.getArtist());

        } catch (Exception ex) {
            Logger.getLogger(MediaInfoSourceFromID3.class.getName()).log(Level.SEVERE, null, ex);
            throw ex;
        }

    }

}
