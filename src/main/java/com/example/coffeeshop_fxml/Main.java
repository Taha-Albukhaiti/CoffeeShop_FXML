package com.example.coffeeshop_fxml;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


/** Main-Klasse zum Starten der Anwendung und zum Oeffnen der Views
 *
 * @author Taha
 *
 */
public class Main extends Application {

    private Stage primaryStage;
    private AnchorPane rootLayout;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Getraenkebestellung");

        //Haupt-View aus fxml laden
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("GetraenkebestellungView.fxml"));
            rootLayout = (AnchorPane) loader.load();

            //Controller (steht in fxml) holen und mit der MainApp (this) verknuepfen
            GetraenkebestellungController controller = loader.getController();

            //Dem Controller die Primary Stage und die MainApp bekannt machen
            controller.setPrimaryStage(primaryStage);
            controller.setMainApp(this);

            //Szene mit Root Layout erzeugen, auf die Stage setzen und anzeigen
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Laedt das Formular zum Aendern der Konfiguration, wird vom GetraenkebestellungController
     * aufgerufen, wenn auf den Button "Admin: Konfigurieren..." geklickt wird.
     * @param konf Die aktuelle Konfiguration.
     * @return true, falls Aenderung bestaetigt, false sonst
     */
    public boolean konfigurationViewZeigen(Konfiguration konf) {
        //View laden
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("KonfigurationView.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();

            //Controller (steht in fxml) holen und Konfiguration-Objekt uebergeben
            KonfigurationController controller = loader.getController();

            //(Secondary) Stage fuer das Popup-Formular erzeugen und mit der Primary Stage verknuepfen
            Stage popupStage = new Stage();
            popupStage.setTitle("Bearbeite Konfiguration");
            popupStage.initModality(Modality.WINDOW_MODAL);	//Popup muss geschlossen werden, bevor es im Hauptfenster weitergehen kann
            popupStage.initOwner(primaryStage);
            popupStage.setResizable(false);					//fixe Fenstergroesse
            Scene scene = new Scene(pane);
            popupStage.setScene(scene);

            //Konfiguration an Controller uebergeben
            controller.setKonfiguration(konf);
            //ausserdem Popup Stage uebergeben, damit Controller diese z. B. schliessen oder weitere Fenster oeffnen kann
            controller.setPopupStage(popupStage);

            //Popup anzeigen und warten, bis der User es schliesst
            popupStage.showAndWait();

            //zurueckgeben, ob bestaetigt oder abgebrochen wurde
            return controller.istBestaetigt();
        } catch (IOException e) {

            return false;
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
