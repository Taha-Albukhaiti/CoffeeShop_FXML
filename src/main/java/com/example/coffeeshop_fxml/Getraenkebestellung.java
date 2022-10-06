package com.example.coffeeshop_fxml;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

/** Model-Klasse fuer Getraenkebestellungen. Fuer die Anzahl(en) und den Gesamtbetrag werden
 * Properties verwendet, um diese an die UI-Elemente binden zu koennen 
 * (siehe GetraenkebestellungController).
 * 
 * @author holger
 * @author Taha
 *
 */
public class Getraenkebestellung {

	//welches Getraenk wie oft bestellt wird, speichert diese Map
	private Map<String, IntegerProperty> getraenkeanzahl = new HashMap<>();
	
	//Gesamtbetrag der Bestellung
	private DoubleProperty gesamtbetrag;

	//die Konfiguration wird benoetigt, um den Gesamtbetrag berechnen zu koennen
	private Konfiguration konf;
	
	
	/**
	 * Initialisiert die Bestellung (alles auf 0 setzen)
	 * @param konf Die Konfiguration mit den aktuellen Getraenkepreisen
	 */
	public Getraenkebestellung(Konfiguration konf){
		
		this.konf = konf;
		
		//Getraenkeanzahl und Gesamtbetrag jeweils mit 0 initialisieren
		getraenkeanzahl.put("Kaffee", new SimpleIntegerProperty(0));
		getraenkeanzahl.put("Cappuccino", new SimpleIntegerProperty(0));
		getraenkeanzahl.put("Espresso", new SimpleIntegerProperty(0));
		gesamtbetrag = new SimpleDoubleProperty(0.0);		
	}

	
	/**
	 * Setzt die Werte (Anzahl, Gesamtbetrag) zurueck, um eine neue Bestellung aufnehmen zu koennen.
	 * Wichtig ist, dass die alten Objekte bestehen bleiben wg. Binding, daher setValue(0).
	 */
	public void reset() {
		getraenkeanzahl.get("Kaffee").setValue(0);
		getraenkeanzahl.get("Cappuccino").setValue(0);
		getraenkeanzahl.get("Espresso").setValue(0);
		gesamtbetrag.setValue(0);		
	}

	
	/**
	 * Aendert die Anzahl des uebergebenen Getraenks um das uebergebene (ggf. negative) Inkrement.
	 * Wird aufgerufen, wenn auf einen der +/--Buttons geklickt wird.
	 * Pro Getraenkeart ist eine Anzahl von 0 bis 10 moeglich.
	 * @param getraenk s.o.
	 * @param inkrement s.o
	 * @return Die Anzahl des Getraenks nach der Aenderung
	 */
	public int anzahlAendern(String getraenk, int inkrement) {
		int anzahlAlt = getraenkeanzahl.get(getraenk).getValue();
		//inkrement kann negativ sein, um Anzahl zu verringern
		int anzahlNeu = anzahlAlt + inkrement;
		if (anzahlNeu >= 0 && anzahlNeu <= 10) {	//Anzahl 0-10
			getraenkeanzahl.get(getraenk).setValue(anzahlNeu);
			gesamtbetragBerechnen();
			return anzahlNeu;
		}
				
		return anzahlAlt;
	}
	

	/**
	 * Berechnet den Gesamtbetrag der Bestellung anhand der Getraenkeanzahl(en) und der
	 * Konfiguration mit den Getraenkepreisen.
	 */
	public void gesamtbetragBerechnen() {
		double betrag = 0.0;
		
		//Map mit Getraenken durchlaufen
        for (Map.Entry<String, IntegerProperty> e : getraenkeanzahl.entrySet()) {
        	//anzahl des Getraenks ermitteln
        	int anzahl = e.getValue().getValue();
        	//aktuellen Preis ermitteln
        	double preis = konf.preisverzeichnis.get(e.getKey());
        	
        	betrag += anzahl * preis;
        }

		betrag = Math.round(betrag * 100.0) / 100.0;
		gesamtbetrag.setValue(betrag);		
	}

	
	public IntegerProperty getAnzahl(String getraenk) {
		return getraenkeanzahl.get(getraenk);
	}

	
	public DoubleProperty getGesamtbetrag() {
		return gesamtbetrag;
	}
	
}
