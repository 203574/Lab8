package it.polito.tdp.metrodeparis;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.*;
import it.polito.tdp.metrodeparis.model.Fermata;
import it.polito.tdp.metrodeparis.model.FermataLinea;
import it.polito.tdp.metrodeparis.model.MetroDeParis;
import it.polito.tdp.metrodeparis.model.MetroDeParis2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class MetroDeParisController
{
	MetroDeParis mdp = new MetroDeParis();
	MetroDeParis2 mm = new MetroDeParis2();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxPartenza;

    @FXML
    private ComboBox<String> boxArrivo;

    @FXML
    private Button buttonDistanza;

    @FXML
    private TextArea txtResult;

    @FXML
    void doCalcola(ActionEvent event) 
    {
    	txtResult.clear();
    	Fermata f1 = mm.getFermataByNome(boxPartenza.getValue());
    	Fermata f2 = mm.getFermataByNome(boxArrivo.getValue());
    	List<FermataLinea> fermateCammino = new ArrayList<FermataLinea>();
    	double tempo=0.0;
    	fermateCammino = mm.getCammino(f1, f2);
    	if(fermateCammino == null)
    	{
    		txtResult.appendText("Queste due fermate non sono collegate, ci dispiace");
    	}
    	else
    	{
    		txtResult.appendText(String.format("Percorso minimo da %s a %s:\n", f1.getNome(), f2.getNome()));
    		for (FermataLinea fermata : fermateCammino)
        	{
    			txtResult.appendText(fermata.getNome()+ " "+ fermata.getId_linea()+"\n");
    		}
    	}
    	tempo = (((fermateCammino.size()-2)*30)/60) + (mm.getTime()*60);
    	txtResult.appendText("\nTempo di percorrenza: "+ (float)(Math.ceil(tempo*Math.pow(10, 2))/Math.pow(10, 2)));
    }

    @FXML
    void initialize()
    {
        assert boxPartenza != null : "fx:id=\"boxPartenza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert boxArrivo != null : "fx:id=\"boxArrivo\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert buttonDistanza != null : "fx:id=\"buttonDistanza\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'MetroDeParis.fxml'.";
        
        for (Fermata f : mm.getFermate())
        {
        	boxPartenza.getItems().add(f.getNome());
		}
        
        for (Fermata f : mm.getFermate())
        {
        	boxArrivo.getItems().add(f.getNome());
		}
        
        mm.generaGrafo();
    }
}

