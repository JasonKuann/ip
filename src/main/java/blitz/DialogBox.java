import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class DialogBox extends HBox {

    private Label text;
    private ImageView displayPicture;

    public DialogBox(String s, Image i) {
        text = new Label(s);
        displayPicture = new ImageView(i);
        text.setWrapText(true);
        displayPicture.setFitWidth(100.0);
        displayPicture.setFitHeight(100.0);
        this.setAlignment(Pos.TOP_RIGHT);

        this.getChildren().addAll(text, displayPicture);
    }

    private void flip() {
        this.setAlignment(Pos.TOP_LEFT);
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        FXCollections.reverse(tmp);
        this.getChildren().setAll(tmp);
    }

    public static DialogBox getUserDialog(String s, Image i) {
        return new DialogBox(s, i);
    }

    public static DialogBox getBlitzDialog(String text, Image img, String commandType) {
     var db = new DialogBox(text, img);
     db.flip();
     db.changeDialogStyle(commandType);
     return db;
 }

     private void changeDialogStyle(String commandType) {
     switch(commandType) {
     case "AddCommand":
         this.getStyleClass().add("add-label");
         break;
     case "ChangeMarkCommand":
         this.getStyleClass().add("marked-label");
         break;
     case "DeleteCommand":
         this.getStyleClass().add("delete-label");
         break;
     default:
         // Do nothing
     }
 }
}
