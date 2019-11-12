/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;


import ku.piii2019.bl2.MediaInfoSourceFromID3;
import ku.piii2019.bl2.MediaItem;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author James
 */
public class MediaInfoSourceFromID3Test {
    
    public MediaInfoSourceFromID3Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addMediaInfo method, of class MediaInfoSourceFromID3.
     */
    @Test
    public void testAddMediaInfoID3v1() throws Exception {
        System.out.println("addMediaInfo");
        FileStore fileStore = new FileStoreShortNames();
        Path testPath = Paths.get(Workshop6TestHelper.TEST_SRC_FOLDER,
                                  fileStore.getRootFolder(), 
                                  "collection-A" + File.separator + "file2.mp3")
                              .toAbsolutePath();
                
        MediaItem itemToTest = new MediaItem().setAbsolutePath(testPath.toString());        
        MediaInfoSourceFromID3 instance = new MediaInfoSourceFromID3();
        instance.addMediaInfo(itemToTest);
        
        MediaItem expectedItem = new MediaItem().setAbsolutePath(testPath.toString());
        expectedItem.setTitle("PERFECT WORLD (AMBIENT)");
        expectedItem.setAlbum("PERFECT WORLD");
        expectedItem.setArtist("DARKPOP BAND ANGELIQUE");
        
        assertEquals("checking album:", expectedItem.getAlbum(), itemToTest.getAlbum());
        assertEquals("checking title:", expectedItem.getTitle(), itemToTest.getTitle());
        assertEquals("checking artist:", expectedItem.getArtist(), itemToTest.getArtist());
        
        
    }
    
}
