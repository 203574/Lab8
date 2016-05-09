package it.polito.tdp.metrodeparis.model;

public class Connessione 
{
	private int id;
	private int idLinea;
	private int idStazioneP;
	private int idStazioneA;
	
	public Connessione(int id, int idLinea, int idStazioneP, int idStazioneA) 
	{
		this.id = id;
		this.idLinea = idLinea;
		this.idStazioneP = idStazioneP;
		this.idStazioneA = idStazioneA;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Connessione other = (Connessione) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId() 
	{
		return id;
	}

	public int getIdLinea() 
	{
		return idLinea;
	}

	public int getIdStazioneP()
	{
		return idStazioneP;
	}

	public int getIdStazioneA()
	{
		return idStazioneA;
	}
	
	
	
}

