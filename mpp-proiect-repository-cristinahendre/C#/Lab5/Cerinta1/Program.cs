using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Threading.Tasks;
using System.Windows.Forms;
using Cerinta1.Controller;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using Cerinta1.Repository;
using Cerinta1.Service;
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

        private static readonly ILog log = LogManager.GetLogger(nameof(Program));

        [STAThread]
        static void Main(string[] args)
        {

			XmlConfigurator.Configure(new System.IO.FileInfo("App.config"));
            var logRepo = LogManager.GetRepository(Assembly.GetEntryAssembly());
            XmlConfigurator.Configure(logRepo, new FileInfo("log4net.config"));
            log.Info("configurare");
            
           



			//TeledonConnectionString
			Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            ICazRepo cazRepo = new CazRepo();
            IDonatorRepo donatorRepo = new DonatorRepo();
            IVoluntarRepo voluntarRepo = new VoluntarRepo();
            IDonatieRepo donatieRepo = new DonatieRepo(cazRepo, donatorRepo);

            IValidator<Donator> donatorV = new DonatorValidator();
            IValidator<Donatie> donatieV = new DonatieValidator();
            SuperService service = new SuperService(cazRepo, donatieRepo, donatorRepo, voluntarRepo,
                donatorV, donatieV);
            // LoginController lCtr = new LoginController(service);
            //LoginForm loginF = new LoginForm();
            //  loginF.setService(service);

            LoginController ctr = new LoginController(service);
            LoginForm view = new LoginForm();
            view.setController(ctr);
            ctr.setLogin(view);
            log.Info("In main:");
           
           
            view.ShowDialog();
            service.closeCon();
            


            //Application.Run(loginF);

            
          


            



        }

    }
}

