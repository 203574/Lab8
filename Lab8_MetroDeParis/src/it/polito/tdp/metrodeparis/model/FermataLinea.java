package it.polito.tdp.metrodeparis.model;

public class FermataLinea extends Fermata implements Comparable<Fermata> 
{
	private int id_linea;
	
	
	public FermataLinea(int id, String nome, double coordX, double coordY) 
	{
		super(id, nome, coordX, coordY);
	}

	public int getId_linea()
	{
		return id_linea;
	}

	public void setId_linea(int id_linea) 
	{
		this.id_linea = id_linea;
	}

	@Override
	public int compareTo(Fermata o)
	{
		// TODO Auto-generated method stub
		return this.getNome().compareTo(o.getNome());
	}

	@Override
	public int hashCode() 
	{
		int result = super.hashCode();
		result = result + id_linea;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		FermataLinea other = (FermataLinea) obj;
		if (id_linea != other.id_linea)
			return false;
		return true;
	}

	
	
}
