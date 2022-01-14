using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    [Serializable]
    public class Entity<TID> 
    {
        public virtual  TID Id { get; set; }
    }
}
