/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.bl2;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author James
 */
@RunWith(Parameterized.class)
public class DuplicateFinderTest {
    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        
        Collection<Object[]> listOfInstances = new ArrayList<>();
        
            
       
        listOfInstances.add(new Object[]{new DuplicateFindFromFilename(), 
                                         new FileStoreOriginalNames(), 
                                         null, 
                                         rightAnswersForFilename});
        
        listOfInstances.add(new Object[]{new DuplicateFindFromMetaData(),
                                         new FileStoreShortNames(),
                                         new MediaInfoSourceFromID3(), 
                                         rightAnswersForID3});
        
        return listOfInstances;
    }
    public DuplicateFinderTest(DuplicateFinder testThisOneNext, 
                               FileStore fs, 
                               MediaInfoSource mis, 
                               List<Boolean> rightAnswers) {
       instance =  testThisOneNext;
       fileStore = fs;
       mediaInfoSource = mis;
       rightAnswersForAreDuplicates = rightAnswers;
    }
    
    DuplicateFinder instance = null;
    FileStore fileStore = null;
    MediaInfoSource mediaInfoSource = null;
    List<Boolean> rightAnswersForAreDuplicates = null;
    static List<Boolean> rightAnswersForFilename = Arrays.asList(true, true, false);
    static List<Boolean> rightAnswersForID3 = Arrays.asList(false, false, true);
    
String sep = File.separator;
 List<MediaItem> item1list = Arrays.asList(new MediaItem()
                                                .setAbsolutePath("c:" + sep + "file.mp3"),
                                              new MediaItem()
                                                .setAbsolutePath("c:" + sep + "file.mp3")
                                                .setTitle("title")
                                                .setArtist("artist"),
                                              new MediaItem()
                                                .setTitle("title")
                                                .setArtist("artist")
                                                .setAlbum("album") 
                                                .setAbsolutePath("c:" + sep + "file.mp3")
                                                        );
    List<MediaItem> item2list = Arrays.asList(new MediaItem()
                                                .setAbsolutePath("c:" + sep + "file.mp3"),
                                              new MediaItem()
                                                .setAbsolutePath("c:" + sep + "sameFilename" + sep +"file.mp3")
                                                .setTitle("title")
                                                .setArtist("artist"),
                                              new MediaItem()
                                                .setTitle("title ")
                                                .setArtist(" artist")
                                                .setAlbum("Album") 
                                                .setAbsolutePath("c:" + sep + "differentFilename.mp3")
                                                        );    

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
//        Workshop6TestHelper.deleteFolderRecursively
//                    (new File(Workshop6TestHelper.TEMP_INPUT_FOLDER_FOR_ORIGINAL_FILENAMES));
        Workshop6TestHelper.
                deleteFolderWithFileVisitor(Workshop6TestHelper.TEST_SCRATCH_FOLDER);
    }

    
     /**
     * Test of getMissingItems method, of class DuplicateFinder.
     */
    @Test
    public void testGetMissingItems() {
        System.out.println("getMissingItems");
        initializeTestFolder();
        Path testRootFolder = Paths.get(Workshop6TestHelper.TEST_SCRATCH_FOLDER).toAbsolutePath();
      
        Set<MediaItem> myCollection = fileStore.getCollectionAItems(testRootFolder.toString(), mediaInfoSource);
        Set<MediaItem> yourCollection = fileStore.getCollectionBItems(testRootFolder.toString(), mediaInfoSource);
        Set<MediaItem> expResult = fileStore.getItemsFromBmissingFromA(testRootFolder.toString(), mediaInfoSource);
        Set<MediaItem> result = instance.getMissingItems(myCollection, yourCollection);
        assertSameMedia(expResult, result);
        
        myCollection = fileStore.getCollectionBItems(testRootFolder.toString(), mediaInfoSource);
        yourCollection = fileStore.getCollectionAItems(testRootFolder.toString(), mediaInfoSource);
        expResult = fileStore.getItemsFromAmissingFromB(testRootFolder.toString(), mediaInfoSource);
        result = instance.getMissingItems(myCollection, yourCollection);
        assertSameMedia(expResult, result);
        
   }

    private void assertSameMedia(Set<MediaItem> expResult, Set<MediaItem> result) {
        for(MediaItem expItem : expResult)
        {
            System.out.println("We should have item " + expItem + "do we?");
            Set<MediaItem> shouldBeOne = instance.getDuplicates(result, expItem);
            if(shouldBeOne.size()!=1)
            {
                System.out.println("eek");
            }
            assertEquals(shouldBeOne.size(), 1);
        }
        for(MediaItem item : result)
        {
            
            System.out.println("We do have item " + item  + "is this ok?");
            Set<MediaItem> shouldBeOne = instance.getDuplicates(expResult, item);
            if(shouldBeOne.size()!=1)
            {
                System.out.println("eek2");
            }
            assertEquals(shouldBeOne.size(), 1);
        }
    }
    
    
    
    
    /**
     * Test of getAllDuplicates method, of class DuplicateFindFromFilename.
     */
    
  //  @Ignore
    @Test
    public void testGetAllDuplicates() {
        System.out.println("getAllDuplicates");
        
        initializeTestFolder();
        
        String rootTestFolder = Paths.get(Workshop6TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> allMediaItems = fileStore.getAllMediaItems(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> expResult = fileStore.getAllDuplicates(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> result = instance.getAllDuplicates(allMediaItems);
        
        System.out.println("the expected result:");
        Workshop6TestHelper.print1(expResult);
        System.out.println("the actual result:");
        Workshop6TestHelper.print1(result);

        assertEquals(expResult, result);
    }
    
 //   @Ignore
    @Test
    public void testGetDuplicates() {
        System.out.println("getDuplicates");
        
        initializeTestFolder();
        
        String rootTestFolder = Paths.get(Workshop6TestHelper.TEST_SCRATCH_FOLDER)
                                     .toAbsolutePath()
                                     .toString();
        
        Set<MediaItem> allMediaItems = fileStore.getAllMediaItems(rootTestFolder, mediaInfoSource);
        Set<Set<MediaItem>> expectedOutputSets = fileStore.getAllDuplicates(rootTestFolder, mediaInfoSource);
        
        for(Set<MediaItem> expectedOutput : expectedOutputSets) {
            MediaItem testInputItem = expectedOutput.iterator().next();
            Set<MediaItem> actualOutput = instance.getDuplicates(allMediaItems, testInputItem);    
            assertEquals(expectedOutput,  actualOutput);
        }
    }
    
  //  @Ignore
    @Test
    public void testAreDuplicates() {
        System.out.println("areDuplicates");
        
        int n = rightAnswersForAreDuplicates.size();
        for(int i = 0;i<n;i++)
        {
            boolean actualReturnValue = instance.areDuplicates(item1list.get(i),
                                                                       item2list.get(i));
            boolean expectedReturnValue = rightAnswersForAreDuplicates.get(i);
            
            String comment = "class is " + instance.getClass() + " and index is " + i;
            assertEquals(comment, expectedReturnValue, actualReturnValue);
        }
    }

    private void initializeTestFolder() {
        
        Path cwdPath = Paths.get("").toAbsolutePath();
        Path testSrcFolder = Paths.get(Workshop6TestHelper.TEST_SRC_FOLDER);
        Path testScratchFolder = 
                Paths.get(Workshop6TestHelper.TEST_SCRATCH_FOLDER);

        File srcFolder = new File(Workshop6TestHelper.TEST_SRC_FOLDER);
        File destFolder = new File(Workshop6TestHelper.TEST_SCRATCH_FOLDER);
        try {
//            Workshop6TestHelper.deleteFolderRecursively(destFolder);    
            Workshop6TestHelper.deleteFolderWithFileVisitor(testScratchFolder.toString());
        } catch (Exception e) {
            // no problem
            e.printStackTrace();
        }
        try {
            Files.createDirectory(testScratchFolder);
//            Workshop6TestHelper.copyFolder(cwdPath, srcFolder, destFolder);
            Path srcPath = Paths.get(cwdPath.toString(), 
                                     Workshop6TestHelper.TEST_SRC_FOLDER,
                                     fileStore.getRootFolder());
            Path dstPath = Paths.get(cwdPath.toString(), 
                                     Workshop6TestHelper.TEST_SCRATCH_FOLDER );
            Path relDstFolder = Paths.get(fileStore.getRootFolder() );
            
            Workshop6TestHelper.copyFolder(srcPath, dstPath, relDstFolder);
        } catch (Exception e) {
            // problem
            e.printStackTrace();
            fail("could not create test folder");
        }
        // remove temporary folder, if it is there
        // copy permanent folder to temp location

    }

}
