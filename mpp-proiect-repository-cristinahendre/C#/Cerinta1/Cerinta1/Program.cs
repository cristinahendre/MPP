using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Domain;
using Cerinta1.Repository;
using log4net;
using log4net.Config;

namespace Cerinta1
{

    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        /// 

        private static readonly ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);


        [STAThread]
        static void Main(string[] args)
        {

			XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));
            var logRepo = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepo, new FileInfo("log4net.config"));



            log.Info("daa");
            CazRepo  repo = new CazRepo();
            DonatorRepo dRepo = new DonatorRepo();
            DonatieRepo donRepo = new DonatieRepo(repo, dRepo);
            VoluntarRepo volRepo = new VoluntarRepo();

            Voluntar vol = new Voluntar("FPPP", "oooa", "a", "a");
            CazCaritabil caz = new CazCaritabil("Tes8t", 1000);
            CazCaritabil cazC = repo.FindOne(1);
            Console.WriteLine(cazC);
          //  repo.Save(caz);
            //volRepo.Save(vol);
            

         
           



			//TeledonConnectionString
			Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());

            

        }

    }
}

