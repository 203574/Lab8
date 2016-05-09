package it.polito.tdp.metrodeparis.model;

import java.util.*;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.DB.ConnessioneDAO;
import it.polito.tdp.metrodeparis.DB.FermataDAO;
import it.polito.tdp.metrodeparis.DB.LineaDAO;

public class MetroDeParis
{
	SimpleWeightedGraph<Fermata, DefaultWeightedEdge> stazioni = new SimpleWeightedGraph<Fermata, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	List<Fermata> fermate = new ArrayList<Fermata>();
	List<Connessione> connessioni = new ArrayList<Connessione>();
	List<Linea> linee = new ArrayList<Linea>();
	GraphPath< Fermata, DefaultWeightedEdge> path;
	
	public List<Fermata> getFermate()
	{
		FermataDAO fdao = new FermataDAO();
		fermate = fdao.getFermate();
		return fermate;
	}
	
	public void generaGrafo()
	{
		Graphs.addAllVertices(stazioni, fermate);
		ConnessioneDAO cdao = new ConnessioneDAO();
		LineaDAO ldao = new LineaDAO();
		linee = ldao.getLinee();
		connessioni = cdao.getConnessioni();
		
		for (Connessione connessione : connessioni) 
		{
			Fermata f1 = getFermataById(connessione.getIdStazioneP());
			Fermata f2 = getFermataById(connessione.getIdStazioneA());
			Linea l = getLineaById(connessione.getIdLinea());
			if(f1 != null && f2 != null & l != null)
			{
				double distanza = LatLngTool.distance(new LatLng(f1.getCoordX(), f1.getCoordY()), new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
				double tempo= distanza/l.getVelocita();
				Graphs.addEdge(stazioni, f1, f2, tempo);
			}
		}
		
		
	}
	
	public List<Fermata> getCammino(Fermata a, Fermata b)
	{
		DijkstraShortestPath<Fermata, DefaultWeightedEdge> cammino = new DijkstraShortestPath<Fermata, DefaultWeightedEdge>(stazioni, a, b);
		path = cammino.getPath();
		if(path == null)
		{
			return null;
		}
		return Graphs.getPathVertexList(path);
	}
	
	public double getTime()
	{
		return path.getWeight();
	}
	public Fermata getFermataById(int id)
	{
		for (Fermata fermata : fermate) 
		{
			if(fermata.getId() == id)
			{
				return fermata;
			}
		}
		return null;		
	}
	
	public Fermata getFermataByNome(String nome)
	{
		for (Fermata fermata : fermate) 
		{
			if(fermata.getNome().compareTo(nome) == 0) 
			{
				return fermata;
			}
		}
		return null;		
	}

	public Linea getLineaById(int id)
	{
		for (Linea linea : linee) 
		{
			if(linea.getId() == id)
			{
				return linea;
			}
		}
		return null;		
	}
}
