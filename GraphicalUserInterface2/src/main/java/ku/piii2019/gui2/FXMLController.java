package ku.piii2019.gui2;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.DirectoryChooser;
import ku.piii2019.bl2.*;

public class FXMLController implements Initializable {
    
    /*public FXMLController(){
        super();
        selectMupltiple();
    }*/

    @FXML
    private Label label;
    @FXML
    private TableView<MediaItem> tableView1;
    @FXML
    private TableView<MediaItem> tableView2;
    @FXML
    private TableView<MediaItem> tableView3;
    
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent content = new ClipboardContent();
    private ObservableList<MediaItem> copied = FXCollections.observableArrayList();

    String collectionRootAB = "test_folders" + File.separator
            + "original_filenames";
    String collectionRootA = collectionRootAB + File.separator
            + "collection-A";
    String collectionRootB = collectionRootAB + File.separator
            + "collection-B";

    @FXML
    private void copy(ActionEvent event){
        ObservableList<MediaItem> selected = tableView1.getSelectionModel().getSelectedItems();
        ObservableList<MediaItem> selected2 = tableView2.getSelectionModel().getSelectedItems();
        pasteSelection(selected,selected2);
        /*String copyThese = selected.toString();
        
        content.putFiles(selected);
        System.out.println(content);
        clipboard.setContent(content);*/
        
        
    }
    
    private void pasteSelection(List<MediaItem> selected, List<MediaItem> selected2){
        try{
            ObservableList<MediaItem> result = FXCollections.observableArrayList(selected);
        result.addAll(selected2);
        System.out.println(result);
        copied.addAll(result);
        }catch(NullPointerException e){
            
        }
    }
    
    @FXML
    private void paste(ActionEvent event){
        System.out.println(copied);
        tableView3.setItems(copied);
        /*if(!clipboard.hasContent(DataFormat.PLAIN_TEXT)){
            ObservableValue<MediaItem> something = FXCollections.ObservableValue(content);
            tableView3.setItems(something);
        }*/
    }
    
    @FXML
    private void handleButton1Action(ActionEvent event) {
        System.out.println("You clicked me!");
        //  label.setText("Hello World!");
    }

    @FXML
    private void handleButton2Action(ActionEvent event) {
        System.out.println("You clicked me!");
        // label.setText("Hello World!");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<MediaItemColumnInfo> columns = MediaItemTableViewFactory.makeColumnInfoList();
        MediaItemTableViewFactory.makeTable(tableView1, columns);
        MediaItemTableViewFactory.makeTable(tableView2, columns);
        MediaItemTableViewFactory.makeTable(tableView3, columns);
        
        tableView1.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView2.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableView3.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    private void openABIn2(ActionEvent event) {
        open(2, collectionRootAB);
    }

    @FXML
    private void insert1In2(ActionEvent event) {
        insert(tableView1, tableView2);
    }
    
    @FXML
    private void insert1In3(ActionEvent event) {
        insert(tableView3, tableView1);
    }

    @FXML
    private void insert2In1(ActionEvent event) {
        insert(tableView1, tableView2);
    }
    
    @FXML
    private void insert2In3(ActionEvent event) {
        insert(tableView3, tableView2);
    }

    @FXML
    private void insert3In1(ActionEvent event) {
        insert(tableView1, tableView3);
    }
    
    @FXML
    private void insert3In2(ActionEvent event) {
        insert(tableView3, tableView2);
    }
    
    @FXML
    private void show2MissingIn1(ActionEvent event) {
//        showMissing(tableView2, tableView1);

        TableViewSelectionModel table1SelectionModel = tableView1.getSelectionModel();
        TableViewSelectionModel table2SelectionModel = tableView1.getSelectionModel();
        
        List<MediaItem> table1SelectedItems = table1SelectionModel.getSelectedItems();
        List<MediaItem> table2SelectedItems = table2SelectionModel.getSelectedItems();
        
        // what next?...
        
        List<MediaItem> currentItems = tableView1.getItems();

        List<MediaItem> tmpCopy = currentItems;

        tmpCopy.addAll(new ArrayList<>(currentItems));
        
    }

    @FXML
    private void show1MissingIn2(ActionEvent event) {
        showMissing(tableView1, tableView2);
    }
    
    @FXML
    private void show1MissingIn3(ActionEvent event) {
        showMissing(tableView1, tableView3);
    }
    
    @FXML
    private void show2MissingIn3(ActionEvent event) {
        showMissing(tableView2, tableView3);
    }
    
    @FXML
    private void show3MissingIn2(ActionEvent event) {
        showMissing(tableView3, tableView2);
    }
    
    @FXML
    private void show3MissingIn1(ActionEvent event) {
        showMissing(tableView3, tableView1);
    }

    private void insert(TableView src, TableView dst) {
        List<MediaItem> itemsToComeFirst = src.getItems();
        List<MediaItem> itemsToComeLast = dst.getItems();

        ObservableList<MediaItem> newList
                = FXCollections.observableArrayList(itemsToComeFirst);
        newList.addAll(itemsToComeLast);
        dst.setItems(newList);
    }

    private void showMissing(TableView missingFromHere, TableView showHere) {
        List<MediaItem> itemsToLookIn = showHere.getItems();
        List<MediaItem> itemsToLookFor = missingFromHere.getItems();

        DuplicateFinder f = new DuplicateFindFromMetaData();

        Set<MediaItem> missingItems = f.getMissingItems(new HashSet<MediaItem>(itemsToLookFor),
                new HashSet<MediaItem>(itemsToLookIn));

        ObservableList<MediaItem> newList
                = FXCollections.observableArrayList(missingItems);

        showHere.setItems(newList);
    }

    @FXML
    private void swap(ActionEvent event) {

        ObservableList<MediaItem> table1Data
                = tableView1.getItems();
        ObservableList<MediaItem> table2Data
                = tableView2.getItems();
        tableView1.setItems(table2Data);
        tableView2.setItems(table1Data);

    }
    
    @FXML
    private void openIn3(ActionEvent event) {
        open(3, null);
    }

    @FXML
    private void openAIn3(ActionEvent event) {
        open(3, collectionRootA);
    }

    @FXML
    private void openBIn3(ActionEvent event) {
        open(3, collectionRootB);
    }
    
    @FXML
    private void openABIn3(ActionEvent event) {
        open(3, collectionRootAB);
    }
    
    @FXML
    private void openIn2(ActionEvent event) {
        open(2, null);
    }

    @FXML
    private void openAIn2(ActionEvent event) {
        open(2, collectionRootA);
    }

    @FXML
    private void openBIn2(ActionEvent event) {
        open(2, collectionRootB);
    }

    @FXML
    private void openABIn1(ActionEvent event) {
        open(1, collectionRootAB);
    }

    @FXML
    private void openIn1(ActionEvent event) {
        open(1, null);
    }

    @FXML
    private void openAIn1(ActionEvent event) {
        open(1, collectionRootA);
    }

    @FXML
    private void openBIn1(ActionEvent event) {
        open(1, collectionRootB);
    }

    private void open(int tableNumber, String collectionRoot) {
        if (collectionRoot == null) {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Open Media Folder for Table " + tableNumber);
            File path = dirChooser.showDialog(null).getAbsoluteFile();
            collectionRoot = path.toString();
        } else {
            String cwd = System.getProperty("user.dir");
            System.out.println(cwd);
            collectionRoot = Paths.get(cwd,
                    "..",
                    collectionRoot).toString();
        }
        TableView<MediaItem> referenceToEitherTable = null;
        if (tableNumber == 1) {
            referenceToEitherTable = tableView1;
        } else if (tableNumber == 2) {
            referenceToEitherTable = tableView2;
        } else if (tableNumber == 3) {
            referenceToEitherTable = tableView3;
        }
        addContents(referenceToEitherTable, collectionRoot);
    }

    private void addContents(TableView<MediaItem> referenceToEitherTable, String collectionRoot) {
        FileService fileService = new FileServiceImpl();
        Set<MediaItem> collectionA = fileService.getAllMediaItems(collectionRoot.toString());

        MediaInfoSource myInfoSource = new MediaInfoSourceFromID3();
        for (MediaItem item : collectionA) {
            try {
                myInfoSource.addMediaInfo(item);
            } catch (Exception e) {

            }
        }
        List<MediaItem> currentItems = referenceToEitherTable.getItems();
        collectionA.addAll(currentItems);
        ObservableList<MediaItem> dataForTableViewAndModel
                = FXCollections.observableArrayList(collectionA);
        referenceToEitherTable.setItems(dataForTableViewAndModel);
    }
}
