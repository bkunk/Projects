using Newtonsoft.Json;
using System.ComponentModel.DataAnnotations;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Kuscotopia.Entities
{
    public class WorkCountEntity
    {

        [Range(1,10)]
        public int WorkCount { get; set; }

    }
}
