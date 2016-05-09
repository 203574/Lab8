package it.polito.tdp.metrodeparis.model;

public class Fermata 
{
	private int id;
	private String nome;
	private double coordX;
	private double coordY;
	
	public Fermata(int id, String nome, double coordX, double coordY)
	{
		this.id = id;
		this.nome = nome;
		this.coordX = coordX;
		this.coordY = coordY;
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
		Fermata other = (Fermata) obj;
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

	public double getCoordX() 
	{
		return coordX;
	}

	public double getCoordY() 
	{
		return coordY;
	}
	
	
	
	
	
}
