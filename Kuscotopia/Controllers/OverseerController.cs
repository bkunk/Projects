using Kuscotopia.Entities;
using Kuscotopia.Services;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Logging;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;

namespace Kuscotopia.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class OverseerController : Controller
    {
        private readonly QueueService queueService;

        public OverseerController(QueueService queueService)
        {
            this.queueService = queueService;
        }
        
        [HttpPost]
        public async Task<IActionResult> PostAsync([FromBody] WorkCountEntity entity)
        {

            await this.queueService.QueueWorkAsync(entity.WorkCount);
            
           
            return StatusCode((int)HttpStatusCode.Accepted);

        }
    }
}
