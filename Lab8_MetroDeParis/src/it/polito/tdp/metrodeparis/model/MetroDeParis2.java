package it.polito.tdp.metrodeparis.model;

import java.util.*;

import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.WeightedGraph;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedWeightedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.metrodeparis.DB.ConnessioneDAO;
import it.polito.tdp.metrodeparis.DB.FermataDAO;
import it.polito.tdp.metrodeparis.DB.LineaDAO;

public class MetroDeParis2 
{
	WeightedGraph<FermataLinea, DefaultWeightedEdge> stazioni = new DefaultDirectedWeightedGraph<FermataLinea, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	List<Fermata> fermate = new ArrayList<Fermata>();
	List<FermataLinea> tutteStazioni = new ArrayList<FermataLinea>();
	List<Connessione> connessioni = new ArrayList<Connessione>();
	List<Linea> linee = new ArrayList<Linea>();
	GraphPath<FermataLinea, DefaultWeightedEdge> path;

	public List<Fermata> getFermate() 
	{
		FermataDAO fdao = new FermataDAO();
		fermate = fdao.getFermate();
		return fermate;
	}

	public void generaGrafo() 
	{
		ConnessioneDAO cdao = new ConnessioneDAO();
		LineaDAO ldao = new LineaDAO();
		linee = ldao.getLinee();
		connessioni = cdao.getConnessioni();

		for (Connessione connessione : connessioni)
		{
			Fermata f = getFermataById(connessione.getIdStazioneP());
			FermataLinea ff = new FermataLinea(f.getId(), f.getNome(), f.getCoordX(), f.getCoordY());
			ff.setId_linea(connessione.getIdLinea());
			tutteStazioni.add(ff);
			stazioni.addVertex(ff);

			
		}
		
		for (Connessione connessione : connessioni) 
		{
			FermataLinea f1 = getFermataById(connessione.getIdStazioneP(), connessione.getIdLinea());
			FermataLinea f2 = getFermataById(connessione.getIdStazioneA(), connessione.getIdLinea());
			Linea l = getLineaById(connessione.getIdLinea());
			if (f1 != null && f2 != null && l != null)
			{
				double distanza = LatLngTool.distance(new LatLng(f1.getCoordX(), f1.getCoordY()),new LatLng(f2.getCoordX(), f2.getCoordY()), LengthUnit.KILOMETER);
				double tempo = distanza / l.getVelocita();
				Graphs.addEdge(stazioni, f1, f2, tempo);
				
			}
		}
		
		for (FermataLinea fermataLinea : tutteStazioni)
		{
			for (FermataLinea fermataLinea2 : tutteStazioni)
			{
				if(fermataLinea.getId() == fermataLinea2.getId() && fermataLinea.getId_linea() != fermataLinea2.getId_linea())
				{
					Linea l = getLineaById(fermataLinea2.getId_linea());
					Graphs.addEdge(stazioni, fermataLinea, fermataLinea2, (l.getIntervallo()/60));
				}
			}
		}
	}

	

	public List<FermataLinea> getCammino(Fermata a, Fermata b) 
	{
		List<FermataLinea> fPartenza = getFermateLineaByFermata(a);
		List<FermataLinea> fArrivo = getFermateLineaByFermata(b);
		List<FermataLinea> percorso = new ArrayList<FermataLinea>();
		List<FermataLinea> temp = new ArrayList<FermataLinea>();
		double peso = 10000000.0;
		
		for (FermataLinea fermataLineap : fPartenza)
		{
			for (FermataLinea fermataLineaa : fArrivo) 
			{
				DijkstraShortestPath<FermataLinea, DefaultWeightedEdge> cammino = new DijkstraShortestPath<FermataLinea, DefaultWeightedEdge>(stazioni, fermataLineap, fermataLineaa);
				path = cammino.getPath();
				temp = Graphs.getPathVertexList(path);
				if(path != null && peso > (cammino.getPathLength()+(30*((temp.size()-2)/3600))))
				{
					peso = cammino.getPathLength()+(30*((temp.size()-2)/3600));
					percorso = Graphs.getPathVertexList(path);
				}
			}
		}

		if (path == null)
		{
			return null;
		}
		return percorso;
	}

	private List<FermataLinea> getFermateLineaByFermata(Fermata a) 
	{
		List<FermataLinea> f = new ArrayList<FermataLinea>();
		for (FermataLinea fermataLinea : tutteStazioni) 
		{
			if(fermataLinea.getId() == a.getId())
			{
				f.add(fermataLinea);
			}
		}
		return f;
	}

	public double getTime()
	{
		return path.getWeight();
	}

	public Fermata getFermataById(int id)
	{
		for (Fermata fermata : fermate)
		{
			if (fermata.getId() == id)
			{
				return fermata;
			}
		}
		return null;
	}
	
	public FermataLinea getFermataById(int id, int id_linea) 
	{
		for (FermataLinea fermata : tutteStazioni)
		{
			if (fermata.getId() == id && id_linea == fermata.getId_linea())
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
			if (fermata.getNome().compareTo(nome) == 0)
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
			if (linea.getId() == id) 
			{
				return linea;
			}
		}
		return null;
	}
}
