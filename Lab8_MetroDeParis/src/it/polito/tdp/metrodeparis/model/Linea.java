package it.polito.tdp.metrodeparis.model;

public class Linea 
{
	private int id;
	private String nome;
	private double velocita;
	private double intervallo;
	private String colore;
	
	public Linea(int id, String nome, double velocita, double intervallo, String colore) 
	{
		this.id = id;
		this.nome = nome;
		this.velocita = velocita;
		this.intervallo = intervallo;
		this.colore = colore;
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
		Linea other = (Linea) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public int getId()
	{
		return id;
	}

	public String getNome() 
	{
		return nome;
	}

	public double getVelocita()
	{
		return velocita;
	}

	public double getIntervallo() 
	{
		return intervallo;
	}

	public String getColore()
	{
		return colore;
	}

	
	
	
}

