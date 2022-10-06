package com.example.coffeeshop_fxml;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller fuer das Formular zum Editieren von Getraenkepreisen.
 * 
 * @author holger
 * @author Taha
 */
public class KonfigurationController {

	// die folgenden Annotationen dienen der Verknuepfung der Attribute mit den
	// Controls in der fxml-Datei
	@FXML
	private TextField textfeldPreisKaffee;
	@FXML
	private TextField textfeldPreisCappuccino;
	@FXML
	private TextField textfeldPreisEspresso;

	private Stage popupStage;
	private Konfiguration konf;
	private boolean bestaetigt = false;

	/**
	 * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem
	 * Laden der fxml-Datei.
	 */
	@FXML
	public void initialize() {
		// nichts zu tun
	}

	/**
	 * Setzt die (secondary) Stage fuer dieses Popup.
	 * 
	 * @param popupStage
	 */
	public void setPopupStage(Stage popupStage) {
		this.popupStage = popupStage;
	}

	/**
	 * Setzt das Konfiguration-Objekt, das in diesem Formular bearbeitet werden
	 * soll.
	 * 
	 * @param konf
	 */
	public void setKonfiguration(Konfiguration konf) {
		this.konf = konf;

		// aktuelle Werte in Formularfelder uebernehmen (mit Dezimalpunkt statt -komma)
		DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance();
		dfs.setDecimalSeparator('.');
		DecimalFormat df = new DecimalFormat("#.##", dfs);
		textfeldPreisKaffee.setText(df.format(konf.preisverzeichnis.get("Kaffee")));
		textfeldPreisCappuccino.setText(df.format(konf.preisverzeichnis.get("Cappuccino")));
		textfeldPreisEspresso.setText(df.format(konf.preisverzeichnis.get("Espresso")));
	}

	/**
	 * Liefert zurueck, ob das Formular mit "Bestaetigen" oder "Abbrechen"
	 * geschlossen wurde.
	 * 
	 * @return true, falls bestaetigt, false sonst.
	 */
	public boolean istBestaetigt() {
		return bestaetigt;
	}

	/**
	 * Handler fuer Clicks auf "Bestaetigen". Die Eingaben werden geprueft und -
	 * falls gueltig - in das Konfiguration-Objekt eingetragen. Anschliessend wird
	 * das Popup geschlossen.
	 */
	@FXML
	private void bestaetigen() {
		if (eingabenGueltig()) {

			konf.preisverzeichnis.replace("Kaffee", Double.parseDouble(textfeldPreisKaffee.getText()));
			konf.preisverzeichnis.replace("Cappuccino", Double.parseDouble(textfeldPreisCappuccino.getText()));
			konf.preisverzeichnis.replace("Espresso", Double.parseDouble(textfeldPreisEspresso.getText()));
			bestaetigt = true;
			popupStage.close(); // Fenster schliessen
		}
	}

	/**
	 * Handler fuer Clicks auf "Abbrechen". Popup wird ohne Datenuebernahme
	 * geschlossen.
	 */
	@FXML
	private void abbrechen() {
		popupStage.close();
	}

	/**
	 * Prueft die Eintraege in den Formularfeldern auf Gueltigkeit.
	 * 
	 * @return true falls gueltig, false sonst.
	 */
	private boolean eingabenGueltig() {

		String errorMessage = "";

		if (textfeldPreisKaffee.getText() == null || textfeldPreisKaffee.getText().length() == 0) {
			errorMessage += "Thema fehlt!\n";
		}
		if (textfeldPreisCappuccino.getText() == null || textfeldPreisCappuccino.getText().length() == 0) {
			errorMessage += "Ort fehlt!\n";
		}

		if (textfeldPreisEspresso.getText() == null || textfeldPreisEspresso.getText().length() == 0) {
			errorMessage += "Nummer fehlt!\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(popupStage);
			alert.setTitle("Ungueltige Eintruege");
			alert.setHeaderText("Bitte korrigieren Sie die ungueltigen Eintraege!");
			alert.setContentText(errorMessage);
			alert.showAndWait();
			return false;
		}

	}

}
