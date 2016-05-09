package it.polito.tdp.metrodeparis.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.metrodeparis.model.Connessione;

public class ConnessioneDAO 
{
	List<Connessione> connessioni = new ArrayList<Connessione>();
	public List<Connessione> getConnessioni()
	{
		Connection conn = DBConnect.getConnection();
		String sql = "select id_connessione, id_linea, id_stazP, id_stazA from connessione";
		try 
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			
			while(res.next())
			{
				int id_conn = Integer.parseInt(res.getString("id_connessione"));
				int id_linea = Integer.parseInt(res.getString("id_linea"));
				int id_stazP = Integer.parseInt(res.getString("id_stazP"));
				int id_stazA = Integer.parseInt(res.getString("id_stazA"));
				Connessione c = new Connessione(id_conn, id_linea, id_stazP, id_stazA);
				connessioni.add(c);
			}
			res.close();
			conn.close();
			return connessioni;
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore nella connessione tab connessione");
		}
		
	}
}
