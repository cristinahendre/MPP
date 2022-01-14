using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Utils.Observers
{
    interface IObserver<E> where E: Event
    {
        void Update(E e);
    }
}
