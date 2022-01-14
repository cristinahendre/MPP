using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using log4net;

namespace Cerinta1.Repository
{
    class DonatorRepo : IDonatorRepo
    {

        private static readonly ILog log = LogManager.GetLogger("DonatorRepo");



        public Donator Delete(int id)
        {
            IDbConnection con = DBUtils.getConnection();
            log.InfoFormat("incerc sa sterg donatorul {0}", id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from donator where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                con.Close();
                if (dataR == 0)
                {
                    log.InfoFormat("Eroare la stergerea donatorului.");
                    throw new RepositoryException("Nu s-a sters cu succes!");
                }
                else
                {
                    log.InfoFormat("Stergerea donatorului a avut loc cu succes.");
                    return null;
                }
            }
        }

        public IEnumerable<Donator> FindAll()
        {
            IList<Donator> lista = new List<Donator>();
            log.InfoFormat("Afisez toti donatorii");
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donator";


                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int idV = dataR.GetInt32(0);
                            string nume = dataR.GetString(1);
                            string prenume = dataR.GetString(2);
                            string adresa = dataR.GetString(3);
                            long nr = dataR.GetInt64(4);

                            Donator don = new Donator(nume, prenume, adresa, nr);
                            don.Id = idV;

                            lista.Add(don);
                           
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



        public Donator FindOne(int id)
        {
            log.InfoFormat("Caut donatorul cu acest id: {0}", id);
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donator where id=@id";
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
                            string adresa = dataR.GetString(3);
                            long nr = dataR.GetInt64(4);

                            Donator don = new Donator(nume, prenume, adresa, nr);
                            don.Id = idV;

                            log.InfoFormat("am gasit donatorul {0}", don);
                            con.Close();
                            return don;
                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            log.InfoFormat("nu am  gasit acel donator.");
            return null;
        }



        public IEnumerable<Donator> getDonatorDupaNume(string nume)
        {
            IList<Donator> lista = new List<Donator>();
            log.InfoFormat("Afisez toti donatorii ce au numele/prenumele incepand cu {0}", nume);
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donator where nume like @nume or prenume like @pre";
                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = nume+"%";
                    comm.Parameters.Add(paramNume);


                    IDbDataParameter paramPrenume = comm.CreateParameter();
                    paramPrenume.ParameterName = "@pre";
                    paramPrenume.Value = nume + "%";
                    comm.Parameters.Add(paramPrenume);



                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int idV = dataR.GetInt32(0);
                            string numeG = dataR.GetString(1);
                            string prenume = dataR.GetString(2);
                            string adresa = dataR.GetString(3);
                            long nr = dataR.GetInt64(4);

                            Donator don = new Donator(numeG, prenume, adresa, nr);
                            don.Id = idV;

                            lista.Add(don);

                        }
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            con.Close();
            log.InfoFormat("Am terminat.");
            return lista;
        }



        public int nrElem()
        {
            log.InfoFormat("Numar cati donatori sunt in baza de date.");
            IDbConnection con = DBUtils.getConnection();
            try
            {
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select count(*) from donator";
                    int rez = int.Parse(comm.ExecuteScalar().ToString());
                    con.Close();
                    log.InfoFormat("Sunt {0} donatori.", rez);
                    return rez;
                }


            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }
            return -1;
        }



        public Donator Save(Donator entity)
        {
            log.InfoFormat("Voi salva donatorul {0}", entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "insert into donator(nume,prenume,adresa,nr_telefon)" +
                        " values (@nume,@pre,@addr,@tel)";

                   


                    IDbDataParameter paramNume = comm.CreateParameter();
                    paramNume.ParameterName = "@nume";
                    paramNume.Value = entity.Nume;
                    comm.Parameters.Add(paramNume);

                    IDbDataParameter paramPrenume = comm.CreateParameter();
                    paramPrenume.ParameterName = "@pre";
                    paramPrenume.Value = entity.Prenume;
                    comm.Parameters.Add(paramPrenume);

                    IDbDataParameter paramAdresa = comm.CreateParameter();
                    paramAdresa.ParameterName = "@addr";
                    paramAdresa.Value = entity.Adresa;
                    comm.Parameters.Add(paramAdresa);


                    IDbDataParameter paramTel = comm.CreateParameter();
                    paramTel.ParameterName = "@tel";
                    paramTel.Value = entity.NrTelefon;
                    comm.Parameters.Add(paramTel);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Salvarea donatorului a intampinat probleme");
                        throw new RepositoryException("Nu s-a adaugat!");
                    }
                    else
                    {
                        log.InfoFormat("Salvarea donatorului e cu succes.");
                        return entity;
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show(ex.Message);
            }

            return null;
        }


        public Donator Update(Donator entity)
        {
            log.InfoFormat("Modific donatorul {0}", entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "update donator set nume=@nume, prenume=@pre, adresa=@addr," +
                        " nr_telefon =@tel where id = @id";
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

                    IDbDataParameter paramAdresa = comm.CreateParameter();
                    paramAdresa.ParameterName = "@addr";
                    paramAdresa.Value = entity.Adresa;
                    comm.Parameters.Add(paramAdresa);


                    IDbDataParameter paramTel = comm.CreateParameter();
                    paramTel.ParameterName = "@tel";
                    paramTel.Value = entity.NrTelefon;
                    comm.Parameters.Add(paramTel);


                    comm.Parameters.Add(paramId);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Eroare la modificarea donatorului.");
                        throw new RepositoryException("Nu s-a modificat!");
                    }
                    else
                    {
                        log.InfoFormat("Donator modificat cu succes.");
                        return entity;
                    }
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
