using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    class DonatieValidator : IValidator<Donatie>
    {
        /*
          * Valideaza o donatie
         * @param entity -> donatia
         * @throws ValidationException
         */
        public void Validate(Donatie entity)
        {
            if (entity.Suma_donata < 0)
                throw new ValidationException("Ati donat o suma negativa.");
        }
    }
}
