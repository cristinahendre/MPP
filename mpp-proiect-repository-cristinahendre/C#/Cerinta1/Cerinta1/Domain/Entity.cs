using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    class Entity<TID> 
    {
        public TID Id { get; set; }
    }
}
