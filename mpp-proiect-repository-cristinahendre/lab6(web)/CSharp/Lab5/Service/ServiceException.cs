using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Service
{
    public class ServiceException : Exception
    {
      
        public ServiceException()
        {
        }

        public ServiceException(String message)
        {
            this.GetBaseException();
        }

    }

}

