package it.polito.tdp.metrodeparis.DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import it.polito.tdp.metrodeparis.model.Fermata;

public class FermataDAO
{
	public List<Fermata> getFermate()
	{
		List<Fermata> fermate = new ArrayList<Fermata>();
		Connection conn = DBConnect.getConnection();
		String sql = "select id_fermata, nome, coordX, coordY from fermata";
		try
		{
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet res = ps.executeQuery();
			while(res.next())
			{
				int id = Integer.parseInt(res.getString("id_fermata"));
				String nome = res.getString("nome");
				double coordX = Double.parseDouble(res.getString("coordX"));
				double coordY = Double.parseDouble(res.getString("coordY"));
				Fermata f = new Fermata(id, nome, coordX, coordY);
				fermate.add(f);
				
			}
			res.close();
			conn.close();
			return fermate;
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Errore connessione");
		}
	}
}
