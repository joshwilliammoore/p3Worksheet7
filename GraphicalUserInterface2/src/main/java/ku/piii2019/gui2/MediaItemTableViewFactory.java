/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ku.piii2019.gui2;

import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import ku.piii2019.bl2.MediaItem;

/**
 *
 * @author James
 *//*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author James
 */
@SuppressWarnings("restriction")
public class MediaItemTableViewFactory {

    
    static public void processInput(MediaItem editItem, String newValue, String editProperty)
    {
        System.out.println("New value is " + newValue + " for property " + editProperty);
    }
    public static List<MediaItemColumnInfo> makeColumnInfoList() {
        List<MediaItemColumnInfo> myColumnInfoList = new ArrayList<>();
        myColumnInfoList.add(new MediaItemColumnInfo().setHeading("Path")
                                             .setMinWidth(200)
                                             .setProperty("absolutePath")
        );

        myColumnInfoList.add(new MediaItemColumnInfo().setHeading("Track Title")
                                             .setMinWidth(100)
                                             .setProperty("title")
        );

        myColumnInfoList.add(new MediaItemColumnInfo().setHeading("Album")
                                             .setMinWidth(100)
                                             .setProperty("album")
        );

        myColumnInfoList.add(new MediaItemColumnInfo().setHeading("Artist")
                .setMinWidth(100)
                .setProperty("artist")
        );
       
        return myColumnInfoList;
        
    }
//    private String path;
//    private Integer lengthInSeconds;
    
//    private Id3Version id3Version;
    // retrieves from the ID tag:
//    private String title;
//    private String year;
//    private String genre;
    
    
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	static public void  makeTable(TableView<MediaItem>      tableView, 
                                      List<MediaItemColumnInfo> myColumnInfoList )
    {
        

        for(final MediaItemColumnInfo myColumnInfo : myColumnInfoList)
        {
            @SuppressWarnings("rawtypes")
			TableColumn thisColumn = new TableColumn(myColumnInfo.getHeading());
            thisColumn.setMinWidth(myColumnInfo.getMinWidth());
               
            thisColumn.setCellValueFactory(
                new PropertyValueFactory<MediaItem, String>(myColumnInfo.getProperty())
            );
            thisColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            
            thisColumn.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<MediaItem, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<MediaItem, 
                                       String> editEvent) {
                    
                        int editRow = editEvent.getTablePosition().getRow();
                        MediaItem editItem = editEvent.getTableView()
                                                        .getItems()
                                                        .get(editRow);                    
                        processInput(editItem, 
                                     editEvent.getNewValue(), 
                                     myColumnInfo.getProperty());
                    }
                }
            );
            tableView.getColumns().add(thisColumn);
        }
    }
     
}

