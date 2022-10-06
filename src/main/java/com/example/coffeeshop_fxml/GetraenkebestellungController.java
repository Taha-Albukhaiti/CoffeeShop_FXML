package com.example.coffeeshop_fxml;

import java.util.Locale;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller fuer das Formular (Hauptfenster) zum Aufgeben einer
 * Getraenkebestellung.
 * 
 * @author holger
 * @author Taha
 *
 */
public class GetraenkebestellungController {

	// Model
	private Konfiguration konf;
	private Getraenkebestellung gb;

	// die folgenden Annotationen dienen der Verknuepfung der Attribute mit den
	// Controls in der fxml-Datei
	@FXML
	private Label preisKaffee;
	@FXML
	private Label preisCappuccino;
	@FXML
	private Label preisEspresso;
	@FXML
	private Label anzahlKaffee;
	@FXML
	private Label anzahlCappuccino;
	@FXML
	private Label anzahlEspresso;
	@FXML
	private Label gesamtbetrag;

	// Referenz zur Applikation
	private Main mainApp;
	private Stage primaryStage;

	/**
	 * Initialisiert das Controller-Objekt. Wird automatisch aufgerufen nach dem
	 * Laden der fxml-Datei.
	 */
	@FXML
	public void initialize() {
		konf = new Konfiguration();
		// Default-Preise festlegen
		konf.preisverzeichnis.put("Kaffee", 1.5);
		konf.preisverzeichnis.put("Cappuccino", 2.5);
		konf.preisverzeichnis.put("Espresso", 2.0);
		// Preise in UI uebernehmen
		setPreise();

		gb = new Getraenkebestellung(konf);

	}

	/**
	 * Event-Handler fuer Klick auf "Konfigurieren..."
	 * 
	 */
	@FXML
	private void konfigurieren() {
		boolean bestaetigt = mainApp.konfigurationViewZeigen(konf);
		if (bestaetigt) {
			setPreise(); // Preise in View aktualisieren
			gb.gesamtbetragBerechnen(); // Gesamtbetrag aktualisieren
		}
	}

	@FXML
	private void anzahl(ActionEvent actionEvent) {
		int anzahl;
		try {
			if (!(actionEvent.getSource() instanceof Button)) {
				return;
			}
			Button button = (Button) actionEvent.getSource();
			String id = button.getId();
			if (id.contains("deKaffee")) {
				String anzahlKaffee = this.anzahlKaffee.getText();
				anzahl = Integer.parseInt(anzahlKaffee) + 1;
				this.anzahlKaffee.setText("" + anzahl);
			} else if (id.contains("inKaffee")) {
				String anzahlKaffee = this.anzahlKaffee.getText();
				anzahl = Integer.parseInt(anzahlKaffee);
				if (anzahl > 0) {
					anzahl -= 1;
					this.anzahlKaffee.setText("" + anzahl);
				}
			} else if (id.contains("deCappuccino")) {
				String anzahlCappuccino = this.anzahlCappuccino.getText();
				anzahl = Integer.parseInt(anzahlCappuccino) + 1;
				this.anzahlCappuccino.setText("" + anzahl);

			} else if (id.contains("inCappuccino")) {
				String anzahlCappuccino = this.anzahlCappuccino.getText();
				anzahl = Integer.parseInt(anzahlCappuccino);
				if (anzahl > 0) {
					anzahl -= 1;
					this.anzahlCappuccino.setText("" + anzahl);
				}
			} else if (id.contains("deEspresso")) {
				String anzahlEspresso = this.anzahlEspresso.getText();
				anzahl = Integer.parseInt(anzahlEspresso) + 1;
				this.anzahlEspresso.setText("" + anzahl);
			} else if (id.contains("inEspresso")) {
				String anzahlEspresso = this.anzahlEspresso.getText();
				anzahl = Integer.parseInt(anzahlEspresso);
				if (anzahl > 0) {
					anzahl -= 1;
					this.anzahlEspresso.setText("" + anzahl);
				}
			}
			updateSum();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Aktualisiert die gesamte Summe aller ausgewaehlten Getraenke.
	 */
	@FXML
	public void updateSum() {
		int anzahlCappuccino, anzahlKaffee, anzahlEspresso;
		anzahlCappuccino = Integer.parseInt(this.anzahlCappuccino.getText());
		anzahlKaffee = Integer.parseInt(this.anzahlKaffee.getText());
		anzahlEspresso = Integer.parseInt(this.anzahlEspresso.getText());
		double sum = anzahlCappuccino * konf.preisverzeichnis.get("Cappuccino")
				+ anzahlKaffee * konf.preisverzeichnis.get("Kaffee")
				+ anzahlEspresso * konf.preisverzeichnis.get("Espresso");
		this.gesamtbetrag.setText(String.format(Locale.GERMAN, "%,.2f", sum));
	}

	/**
	 * Gibt die BestellInfos zurück
	 */
	public void bestellInfos() {
		String message = "";
		message += this.gesamtbetrag.getText();
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle("Bestellung");
		alert.setHeaderText("Infos");
		alert.setContentText("Ihre Bestellung kostet : " + message);
		alert.showAndWait();

	}

	/**
	 * Preise setzen und anzeigen
	 * 
	 */
	private void setPreise() {
		preisKaffee.setText(String.format("%,.2f", konf.preisverzeichnis.get("Kaffee")) + " €");
		preisCappuccino.setText(String.format("%,.2f", konf.preisverzeichnis.get("Cappuccino")) + " €");
		preisEspresso.setText(String.format("%,.2f", konf.preisverzeichnis.get("Espresso")) + " €");
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	/**
	 * Handler fuer Clicks auf "Abbrechen". Popup wird ohne Datenuebernahme
	 * geschlossen.
	 */
	@FXML
	private void abbrechen() {
		primaryStage.close();
	}
}
