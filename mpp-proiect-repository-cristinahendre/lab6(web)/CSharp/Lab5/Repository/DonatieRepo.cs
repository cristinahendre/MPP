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
    public class DonatieRepo : IDonatieRepo
    {

        ICazRepo cazRepo;
        IDonatorRepo donatorRepo;
        private static readonly ILog log = LogManager.GetLogger("DonatieRepo");

        public DonatieRepo(ICazRepo cazRepo, IDonatorRepo donatorRepo)
        {
            this.cazRepo = cazRepo;
            this.donatorRepo = donatorRepo;
        }

        public Donatie Delete(int id)
        {
            Donatie dn = FindOne(id);
            CazCaritabil caz = dn.Caz;

            caz.Suma_donata = caz.Suma_donata - dn.Suma_donata;
            cazRepo.Update(caz);

            IDbConnection con = DBUtils.getConnection();
            log.InfoFormat("incerc sa sterg donatia {0}", id);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "delete from donatie where id=@id";
                IDbDataParameter paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);
                var dataR = comm.ExecuteNonQuery();
                con.Close();
                if (dataR == 0)
                {
                    log.InfoFormat("Eroare la stergerea donatiei.");
                    throw new RepositoryException("Nu s-a sters cu succes!");
                }
                else
                {
                    log.InfoFormat("Stergerea donatiei a avut loc cu succes.");
                    return null;
                }
            }
        }


        public IEnumerable<Donatie> FindAll()
        {
            IList<Donatie> lista = new List<Donatie>();
            log.InfoFormat("Afisez toate donatiile");
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donatie ";


                    using (var dataR = comm.ExecuteReader())
                    {
                        while (dataR.Read())
                        {
                            int id = dataR.GetInt32(0);
                            int id_caz = dataR.GetInt32(1);
                            int id_donator = dataR.GetInt32(2);
                            int suma = dataR.GetInt32(3);

                            CazCaritabil caz = cazRepo.FindOne(id_caz);
                            Donator donator = donatorRepo.FindOne(id_donator);
                            Donatie donatie = new Donatie(caz, suma, donator);
                            donatie.Id = id;

                            lista.Add(donatie);
                        }
                    }
                }
            }
            catch (Exception )
            {
                // MessageBox.Show(ex.Message);
            }
            con.Close();
            log.InfoFormat("Am parcurs donatiile.");
            return lista;
        }

        public Donatie FindOne(int id)
        {
            log.InfoFormat("Caut donatia cu acest id: {0}", id);
            IDbConnection con = DBUtils.getConnection();
            try
            {

                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select * from donatie where id=@id";
                    IDbDataParameter paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = id;
                    comm.Parameters.Add(paramId);

                    using (var dataR = comm.ExecuteReader())
                    {
                        if (dataR.Read())
                        {
                            
                            int id_caz = dataR.GetInt32(1);
                            int id_donator = dataR.GetInt32(2);
                            int suma = dataR.GetInt32(3);

                            CazCaritabil caz = cazRepo.FindOne(id_caz);
                            Donator donator = donatorRepo.FindOne(id_donator);
                            Donatie donatie = new Donatie(caz, suma, donator);
                            donatie.Id = id;
                            con.Close();
                            return donatie;
                        }
                    }
                }
            }
            catch (Exception )
            {
                //  MessageBox.Show(ex.Message);
            }
            log.InfoFormat("nu am  gasit acea donatie.");
            return null;

        }

        public int nrElem()
        {
            log.InfoFormat("Numar cate donatii sunt in baza de date.");
            IDbConnection con = DBUtils.getConnection();
            try
            {
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "select count(*) from donatie";
                    int rez = int.Parse(comm.ExecuteScalar().ToString());
                    con.Close();
                    log.InfoFormat("Sunt {0} donatii.", rez);
                    return rez;
                }


            }
            catch (Exception )
            {
                //  MessageBox.Show(ex.Message);
            }
            return -1;
        }

        public Donatie Save(Donatie entity)
        {
            log.InfoFormat("Voi salva donatia {0}", entity);
            CazCaritabil caz = entity.Caz;
            caz.Suma_donata = caz.Suma_donata + entity.Suma_donata;
            cazRepo.Update(entity.Caz);
            IDbConnection con = DBUtils.getConnection();


            try
            {
               
                    using (var comm = con.CreateCommand())
                    {
                        comm.CommandText = "insert into donatie(id_caz,id_donator,suma) values (@caz,@don,@s)";



                        IDbDataParameter paramCaz = comm.CreateParameter();
                        paramCaz.ParameterName = "@caz";
                        paramCaz.Value = entity.Caz.Id;
                        comm.Parameters.Add(paramCaz);

                        IDbDataParameter paramDonator = comm.CreateParameter();
                        paramDonator.ParameterName = "@don";
                        paramDonator.Value = entity.Donator.Id;


                        comm.Parameters.Add(paramDonator);

                        IDbDataParameter paramSuma = comm.CreateParameter();
                        paramSuma.ParameterName = "@s";
                        paramSuma.Value = entity.Suma_donata;


                        comm.Parameters.Add(paramSuma);

                        var dataR = comm.ExecuteNonQuery();

                        if (dataR == 0)
                        {
                            log.InfoFormat("Salvarea donatiei a intampinat probleme");
                            throw new RepositoryException("Nu s-a adaugat!");
                        }
                        else
                        {
                            log.InfoFormat("Salvarea donatiei e cu succes.");

                            return entity;
                        }
                    }
                }
            
            catch (Exception )
            {
                //  MessageBox.Show(ex.Message);
            }
            finally
            {
             //  con.Close();
            }
            return null;
        }



        public Donatie Update(Donatie entity)
        {
            Donatie initial = FindOne(entity.Id);

            if (initial.Caz.Equals(entity.Caz))
            {
                //se modifica doar suma donata
                CazCaritabil caz = initial.Caz;
                caz.Suma_donata = caz.Suma_donata - initial.Suma_donata + entity.Suma_donata;

                cazRepo.Update(caz);
            }
            else
            {

                //se modifica acel caz
                CazCaritabil caz = initial.Caz;
                caz.Suma_donata = caz.Suma_donata - initial.Suma_donata;
                cazRepo.Update(caz);

                CazCaritabil nou = entity.Caz;
                nou.Suma_donata = nou.Suma_donata + entity.Suma_donata;
                cazRepo.Update(nou);

            }



            log.InfoFormat("Modific donatia {0}", entity);
            try
            {
                IDbConnection con = DBUtils.getConnection();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "update donatie set id_caz=@caz, id_donator =@don ,suma=@s where id = @id";
                    IDbDataParameter paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = entity.Id;



                    IDbDataParameter paramCaz = comm.CreateParameter();
                    paramCaz.ParameterName = "@caz";
                    paramCaz.Value = entity.Caz.Id;
                    comm.Parameters.Add(paramCaz);

                    IDbDataParameter paramDonator = comm.CreateParameter();
                    paramDonator.ParameterName = "@don";
                    paramDonator.Value = entity.Donator.Id;
                    comm.Parameters.Add(paramDonator);


                    IDbDataParameter paramSuma = comm.CreateParameter();
                    paramSuma.ParameterName = "@s";
                    paramSuma.Value = entity.Suma_donata;


                    comm.Parameters.Add(paramSuma);
                    comm.Parameters.Add(paramId);

                    var dataR = comm.ExecuteNonQuery();
                    con.Close();
                    if (dataR == 0)
                    {
                        log.InfoFormat("Eroare la modificarea donatiei.");
                        throw new RepositoryException("Nu s-a modificat!");
                    }
                    else
                    {
                        log.InfoFormat("Donatie modificata cu succes.");
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
    }
}
