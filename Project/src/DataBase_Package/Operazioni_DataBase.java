package DataBase_Package;

import java.sql.ResultSet;

public interface Operazioni_DataBase 
{
	public void InserimentoRecord( String Sql );
	public ResultSet Query( String Sql );
	public void UpdateRecord( String Sql );
}
