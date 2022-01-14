using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SQLite;
using System.Configuration;
using log4net.Config;

namespace Cerinta1.Utils
{
	public class LiteConnectionFactory : ConnectionFactory
	{
		public override IDbConnection createConnection()
		{
			
			string connectionString=ConfigurationManager.ConnectionStrings["teledonDB"].ConnectionString;
			
			//Mono Sqlite Connection
			///String connectionString = "URI=file:C:\\Users\\crist\\sqllite\\databases\\teledon.db";
			//string connectionString = ConfigurationManager.ConnectionStrings["ConnString"].ConnectionString;
			return new SQLiteConnection(connectionString);

			// Windows Sqlite Connection, fisierul .db ar trebuie sa fie in directorul debug/bin
			//String connectionString = "Data Source=tasks.db;Version=3";
			//return new SqliteConnection(connectionString);
		}
	}
}
