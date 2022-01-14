using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    class VoluntarValidator : IValidator<Voluntar>
    {
     /*
     * Valideaza un voluntar
     * @param entity ->voluntarul
     * @throws ValidationException
     */
        public void Validate(Voluntar entity)
        {
            if (entity.Email.Equals("") || entity.Email.Equals(" "))
                throw new ValidationException("Email vid.");
            if (entity.Parola.Equals("") || entity.Parola.Equals(" "))
                throw new ValidationException("Parola e vida.");
        }
    }
}
