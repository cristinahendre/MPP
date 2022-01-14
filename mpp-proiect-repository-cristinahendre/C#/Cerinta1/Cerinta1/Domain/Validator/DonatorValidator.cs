using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    class DonatorValidator : IValidator<Donator>
    {

     /*
     * Valideaza un donator
     * @param entity ->donatorul
     * @throws ValidationException
     */

        public void Validate(Donator entity)
        {
            if (entity.Nume.Equals(" ") || entity.Nume.Equals(""))
                throw new ValidationException("Numele este vid");
            if (entity.Adresa.Equals(" ") || entity.Adresa.Equals(""))
                throw new ValidationException("Adresa este vida");
            if (entity.Prenume.Equals(" ") || entity.Prenume.Equals(""))
                throw new ValidationException("Prenumele este vid");
            if (entity.NrTelefon < 0)
                throw new ValidationException("Numarul de telefon  e negativ.");
        }
    }
}
