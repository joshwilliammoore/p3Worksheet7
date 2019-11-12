/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import ku.piii2019.bl2.MediaItem;

/**
 *
 * @author James
 */
public interface MediaInfoSource {
    void addMediaInfo(MediaItem m) throws Exception;
    
}
