﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain.Validator
{
    public interface IValidator<T>
    {
         void Validate(T entity);
    }
}
