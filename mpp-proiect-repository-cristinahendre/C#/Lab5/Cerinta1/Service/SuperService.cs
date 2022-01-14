using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using Cerinta1.Repository;

namespace Cerinta1.Service
{
    public class SuperService
    {
        private ICazRepo cazRepo;
        private IDonatieRepo donatieRepo;
        private IDonatorRepo donatorRepo;
        private IVoluntarRepo voluntarRepo;

        private IValidator<Donator> donatorV;
        private IValidator<Donatie> donatieV;

        public SuperService(ICazRepo cazRepo, IDonatieRepo donatieRepo, IDonatorRepo donatorRepo,
            IVoluntarRepo voluntarRepo, IValidator<Donator> donatorV, 
            IValidator<Donatie> donatieV)
        {
            this.cazRepo = cazRepo;
            this.donatieRepo = donatieRepo;
            this.donatorRepo = donatorRepo;
            this.voluntarRepo = voluntarRepo;
            this.donatorV = donatorV;
            this.donatieV = donatieV;
        }


        //cauta cazul cu acest id
        public CazCaritabil findCaz(int id)
        {
            return cazRepo.FindOne(id);
        }


        //returneaza toate cazurile caritabile
        public IEnumerable<CazCaritabil> getCazuri()
        {
            return cazRepo.FindAll();
        }


        //returneaza toti donatorii
        public IEnumerable<Donator> getDonatori()
        {
            return donatorRepo.FindAll();
        }


        //salveaza o donatie noua
        public Donatie saveDonatie(CazCaritabil caz, Donator don ,int suma)
        {
            Donatie donatie = new Donatie(caz, suma, don);
            donatieV.Validate(donatie);
            return donatieRepo.Save(donatie);
            
        }


        //salveaza un nou donator
        public Donator saveDonator(string nume, string prenume, string addr, long nr)
        {
            Donator don = new Donator(nume, prenume, addr, nr);
            donatorV.Validate(don);
            return donatorRepo.Save(don);
        }

        //se cauta donatorul al carui nume ori prenume incepe cu stringul dat
        public IEnumerable<Donator> getDonatordupaNume(string nume)
        {
            return donatorRepo.getDonatorDupaNume(nume);
        }



        //se cauta voluntarul ce are acest email si aceasta parola
        public Voluntar getVoluntarDupaDate(string email, string parola)
        {
            return voluntarRepo.getVoluntarDupaEmail(email, parola);
        }


        //se dau aceste date despre donator si se cauta id-ul sau
        public Donator getDonatorDupaDate(string nume, string prenume, string addr, long nr)
        {
            return donatorRepo.getDonatorDupaDate(nume, prenume, addr, nr);
        }

        public void closeCon()
        {
            voluntarRepo.closeConnection();
        }
    }
}
