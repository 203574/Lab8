package it.polito.tdp.metrodeparis.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.metrodeparis.model.Linea;

public class LineaDAO
{
	public List<Linea> getLinee()
	{
		List<Linea> linee = new ArrayList<Linea>();
		Connection conn = DBConnect.getConnection();
		String sql = "select id_linea, nome, velocita, intervallo, colore from linea";
		PreparedStatement ps;
		try 
		{
			ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				int id = Integer.parseInt(res.getString("id_linea"));
				String nome = res.getString("nome");
				double velocita = Double.parseDouble(res.getString("velocita"));
				double intervallo = Double.parseDouble(res.getString("intervallo"));
				String colore = res.getString("colore");
				Linea l = new Linea(id, nome, velocita, intervallo, colore);
				linee.add(l);
			}
			res.close();
			conn.close();
			return linee;
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("errore connessione tabella linea");
		}
		
	}
}
