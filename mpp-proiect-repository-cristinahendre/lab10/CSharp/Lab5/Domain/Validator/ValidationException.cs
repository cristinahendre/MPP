using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    class ValidationException : Exception
    {
        public ValidationException() { }

        public ValidationException(string message)
        {
            throw new Exception(message);
        }
    }
}
