using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    interface IValidator<T>
    {
         void Validate(T entity);
    }
}
