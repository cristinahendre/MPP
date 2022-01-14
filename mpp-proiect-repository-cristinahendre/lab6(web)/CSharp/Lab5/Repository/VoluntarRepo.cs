using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using log4net;

namespace Cerinta1.Repository
{
    public class VoluntarRepo : IVoluntarRepo
    {

        private static readonly ILog log = LogManager.GetLogger(nameof(VoluntarRepo));



        public Voluntar Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            log.InfoFormat("incerc sa sterg voluntarul {0}", id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from voluntar where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                con.Close();
                if (dataR == 0)
                {
                    log.InfoFormat("Eroare la stergerea voluntarului.");
                    throw new RepositoryException("Nu s-a sters cu succes!");
                }
                else
                {
                    log.InfoFormat("Stergerea voluntarului a avut loc cu succes.");
                    return null;
                }
            }
        }

        public IEnumerable<Voluntar> FindAll()
        {
            IList<Voluntar> lista = new List<Voluntar>();
            log.InfoFormat("Afisez toti voluntarii");
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from voluntar";


                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int idV = dataR.GetInt32(0);
                            string nume = dataR.GetString(1);
                            string prenume = dataR.GetString(2);
                            string email = dataR.GetString(3);
                            string parola = dataR.GetString(4);

                            Voluntar vol = new Voluntar(nume, prenume, email, parola);
                            vol.Id = idV;

                            lista.Add(vol);

                        }
                    }
                }
            }
            catch (Exception )
            {
            }
            con.Close();
            log.InfoFormat("Am parcurs.");
            return lista;
        }


        public Voluntar FindOne(int id)
        {
            log.InfoFormat("Caut voluntarul cu acest id: {0}", id);
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from voluntar where id=@id";
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
                            string prenume = dataR.GetString(2);
                            string email = dataR.GetString(3);
                            string parola = dataR.GetString(4);

                            Voluntar vol = new Voluntar(nume, prenume, email, parola);
                            vol.Id = idV;

                            log.InfoFormat("am gasit voluntarul {0}", vol);
                            con.Close();
                            return vol;
                        }
                    }
                }
            }
            catch (Exception )
            {
             //   MessageBox.Show(ex.Message);
            }
            log.InfoFormat("nu am  gasit acel voluntar.");
            return null;
        }

        public Voluntar getVoluntarDupaEmail(string email, string parola)
        {
            Console.WriteLine("am intrat in vol repo");
            log.InfoFormat("Caut voluntarul cu acest email si parola: {0}, {1}", email,parola);
            IDbConnection con = DBUtils.getConnection();
            Console.WriteLine("conexiune cu succes in voluntar repo");
            try
            {
                using (var comm = con.CreateCommand())
                    {
                        
                        comm.CommandText = "select * from voluntar where email=@em and parola=@par";
                        IDbDataParameter paramEmail = comm.CreateParameter();
                        paramEmail.ParameterName = "@em";
                        paramEmail.Value = email;
                        comm.Parameters.Add(paramEmail);


                        IDbDataParameter paramParola = comm.CreateParameter();
                        paramParola.ParameterName = "@par";
                        paramParola.Value = parola;
                        comm.Parameters.Add(paramParola);


                        using (var dataR = comm.ExecuteReader())
                        {
                            if (dataR.Read())
                            {
                                int idV = dataR.GetInt32(0);
                                string nume = dataR.GetString(1);
                                string prenume = dataR.GetString(2);


                                Voluntar vol = new Voluntar(nume, prenume, email, parola);
                                vol.Id = idV;

                                log.InfoFormat("am gasit voluntarul {0}", vol);
                                Console.WriteLine("vol repo: return" +vol);
                                return vol;

                            }

                        }
                    }
                }
            
            catch (Exception e)
            {
                Console.WriteLine(e.Message);

            }
            finally
            {
                
            }
            log.InfoFormat("nu am  gasit acel voluntar.");
            return null;
        }


        public int nrElem()
        {
            log.InfoFormat("Numar cati voluntari sunt in baza de date.");
            IDbConnection con = DBUtils.getConnection();
            try
            {
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select count(*) from voluntar";
                    int rez = int.Parse(comm.ExecuteScalar().ToString());
                    con.Close();
                    log.InfoFormat("Sunt {0} voluntari.", rez);
                    return rez;
                }


            }
            catch (Exception )
            {
                // MessageBox.Show(ex.Message);
            }
            return -1;
        }


        public Voluntar Save(Voluntar entity)
        {

            log.InfoFormat("Voi salva voluntarul {0}", entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "insert into voluntar(nume,prenume,email,parola)" +
                        " values (@nume,@pre,@em,@par)";

                    


                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = entity.Nume;
                    comm.Parameters.Add(paramNume);

                    IDbDataParameter paramPrenume = comm.CreateParameter();
                    paramPrenume.ParameterName = "@pre";
                    paramPrenume.Value = entity.Prenume;
                    comm.Parameters.Add(paramPrenume);

                    IDbDataParameter paramEmail = comm.CreateParameter();
                    paramEmail.ParameterName = "@em";
                    paramEmail.Value = entity.Email;
                    comm.Parameters.Add(paramEmail);


                    IDbDataParameter paramParola = comm.CreateParameter();
                    paramParola.ParameterName = "@par";
                    paramParola.Value = entity.Parola;
                    comm.Parameters.Add(paramParola);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Salvarea voluntarului a intampinat probleme");
                        throw new RepositoryException("Nu s-a adaugat!");
                    }
                    else
                    {
                        log.InfoFormat("Salvarea voluntarului e cu succes.");
                        return entity;
                    }
                }
            }
            catch (Exception)
            {
                //  MessageBox.Show(ex.Message);
            }

            return null;
        }

        public Voluntar Update(Voluntar entity)
        {
            log.InfoFormat("Modific voluntarul {0}", entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "update voluntar set nume=@nume, prenume=@pre, email=@em," +
                        " parola =@par where id = @id";
                    IDbDataParameter paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = entity.Id;



                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = entity.Nume;
                    comm.Parameters.Add(paramNume);

                    IDbDataParameter paramPrenume = comm.CreateParameter();
                    paramPrenume.ParameterName = "@pre";
                    paramPrenume.Value = entity.Prenume;
                    comm.Parameters.Add(paramPrenume);

                    IDbDataParameter paramEmail = comm.CreateParameter();
                    paramEmail.ParameterName = "@em";
                    paramEmail.Value = entity.Email;
                    comm.Parameters.Add(paramEmail);


                    IDbDataParameter paramParola = comm.CreateParameter();
                    paramParola.ParameterName = "@par";
                    paramParola.Value = entity.Parola;
                    comm.Parameters.Add(paramParola);


                    comm.Parameters.Add(paramId);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Eroare la modificarea voluntarului.");
                        throw new RepositoryException("Nu s-a modificat!");
                    }
                    else
                    {
                        log.InfoFormat("Voluntar modificat cu succes.");
                        return entity;
                    }
                }
            }
            catch (Exception )
            {
                //  MessageBox.Show(ex.Message);
            }
            return null;
        }

        public void closeConnection()
        {
            IDbConnection con = DBUtils.getConnection();
            con.Close();
        }
    }
}
