using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using log4net;
using log4net.Config;

namespace Cerinta1.Repository
{
  
    class CazRepo : ICazRepo

    {
     
        private static readonly ILog log = LogManager.GetLogger(nameof(CazRepo));


        public CazCaritabil Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            log.InfoFormat("incerc sa sterg cazul caritabil {0}", id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from caz where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                con.Close();
                if (dataR == 0)
                {
                    log.InfoFormat("Eroare la stergerea cazului.");
                    throw new RepositoryException("Nu s-a sters cu succes!");
                }
                else
                {
                    log.InfoFormat("Stergerea cazului a avut loc cu succes.");
                    return null;
                }
            }
        }


        public IEnumerable<CazCaritabil> FindAll()
        {
            IList<CazCaritabil> lista = new List<CazCaritabil>();
            log.InfoFormat("Afisez toate cazurile caritabile");
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from caz ";
                   

                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int idV = dataR.GetInt32(0);
                            string nume = dataR.GetString(1);
                            int suma_donata = dataR.GetInt32(2);
                            CazCaritabil caz = new CazCaritabil(nume, suma_donata);
                            caz.Id = idV;
                           
                            lista.Add(caz);
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            con.Close();
            log.InfoFormat("Am parcurs.");
            return lista;
        }




        public CazCaritabil FindOne(int id)
        {
            log.Info("Caut cazul cu acest ");
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from caz where id=@id";
                    IDbDataParameter paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = id;
                    comm.Parameters.Add(paramId);

                    using (var dataR = comm.ExecuteReader())
                    {
                        if (dataR.Read())
                        {
                            int idV = dataR.GetInt32(0);
                            string nume = dataR.GetString(1);
                            int suma_donata = dataR.GetInt32(2);
                            CazCaritabil caz = new CazCaritabil(nume, suma_donata);
                            caz.Id = idV;
                            log.InfoFormat("am gasit cazul {0}", caz);
                            con.Close();
                            return caz;
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            log.InfoFormat("nu am  gasit acel caz.");
            return null;
        }



        public int nrElem()
        {
            log.InfoFormat("Numar cate cazuri sunt in baza de date.");
            IDbConnection con = DBUtils.getConnection();
            try
            {
                using(var comm = con.CreateCommand())
                {
                    comm.CommandText = "select count(*) from caz";
                    int rez = int.Parse(comm.ExecuteScalar().ToString());
                    con.Close();
                    log.InfoFormat("Sunt {0} cazuri.", rez);
                    return rez;
                }


            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return -1;
        }



        public CazCaritabil Save(CazCaritabil entity)
        {
            log.InfoFormat("Voi salva cazul caritabil {0}",entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "insert into caz(nume,suma_donata) values (@nume,@s)";
                    


                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = entity.Nume;
                    comm.Parameters.Add(paramNume);

                    IDbDataParameter paramSuma = comm.CreateParameter();
                    paramSuma.ParameterName = "@s";
                    paramSuma.Value = entity.Suma_donata;


                    comm.Parameters.Add(paramSuma);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Salvarea cazului a intampinat probleme");
                        throw new RepositoryException("Nu s-a adaugat!");
                    }
                    else
                    {
                        log.InfoFormat("Salvarea cazului e cu succes.");
                        return entity;
                    }
                }
            }
            catch(Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return null;
        }



        public CazCaritabil Update(CazCaritabil entity)
        {
            log.InfoFormat("Modific cazul {0}",entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "update caz set nume=@nume, suma_donata=@s where id = @id";
                    IDbDataParameter paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = entity.Id;
                   


                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = entity.Nume;
                    comm.Parameters.Add(paramNume);

                    IDbDataParameter paramSuma = comm.CreateParameter();
                    paramSuma.ParameterName = "@s";
                    paramSuma.Value = entity.Suma_donata;


                    comm.Parameters.Add(paramSuma);
                    comm.Parameters.Add(paramId);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Eroare la modificarea cazului.");
                        throw new RepositoryException("Nu s-a modificat!");
                    }
                    else {
                        log.InfoFormat("Caz modificat cu succes.");
                        return entity; }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return null;
        }
    }
}
