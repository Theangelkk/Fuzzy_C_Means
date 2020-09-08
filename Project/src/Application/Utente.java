package Application;

public class Utente {

	private String Nome;
	private String Cognome;
	private String Sesso;
	private String DataNascita;
	
	public Utente()
	{
		Nome = "";
		Cognome = "";
		Sesso = "M";
		DataNascita = "";
	}
	
	public Utente( String N, String C, String S, String DN )
	{
		setNome(N);
		setCognome(C);
		setSesso(S);
		setDataNascita(DN);
	}
	
	public void setNome( String N )
	{
		if( N != "" )
			Nome = N;
	}
	
	public void setCognome( String C ) 
	{
		if( C != "" )
			Cognome = C;
	}
	
	public void setSesso( String S )
	{
		if( S == "M" || Sesso == "F" )
			Sesso = S;
	}
	
	public void setDataNascita( String DN )
	{
		DataNascita = DN;
	}
	
	public String getNome()
	{
		return Nome;
	}
	
	public String getCognome() 
	{
		return Cognome;
	}
	
	public String getSesso()
	{
		return Sesso;
	}
	
	public String getDataNascita()
	{
		return DataNascita;
	}
}
