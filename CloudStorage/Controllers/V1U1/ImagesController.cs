using System.Collections.Generic;
using Microsoft.AspNetCore.Mvc;
using System.Threading.Tasks;
using System.Linq;
using System.Net;
using CloudStorage.Services;
using CloudStorage.Entities.V1U1;
using System.Net.Http.Headers;
using System;
using CloudStorage.Models;

namespace CloudStorage.Controllers.V1U1
{
    [ApiController]
    [Route("api/[controller]")]
    [ApiVersion("1.1")]
    public class ImagesController : Controller
    {
        private IImageTableStorage imageTableStorage;

        public ImagesController(IImageTableStorage imageTableStorage)
        {
            this.imageTableStorage = imageTableStorage;
        }

        [HttpGet]
        public IAsyncEnumerable<ImageEntity> GetAsync()
        {
            return imageTableStorage.GetAllImagesAsync().Select(image => ImageEntity.FromTableEntity(image));
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetAsync(string id)
        {
            var imageTableEntity = await this.imageTableStorage.GetAsync(id);
            var imageTableEntityDownloadURL = imageTableStorage.GetDownloadUrl(imageTableEntity);
            if (imageTableEntity == null)
            {
                return StatusCode((int)HttpStatusCode.NotFound);
            }
                Response.Headers["Cache-Control"] = "public, max-age=25200"; 
                Response.Headers["Location"] = imageTableEntityDownloadURL;

                return StatusCode((int)HttpStatusCode.Redirect);
        }

        [HttpPost] 
        public async Task<IActionResult> PostAsync([FromBody] ImageEntity imageEntity)
        {
            var imageTableEntity = await this.imageTableStorage.AddOrUpdateAsync(imageEntity.ToTableEntity());

            imageEntity.Id = imageTableEntity.Id;
            imageEntity.UserName = imageTableEntity.UserName; 
            imageEntity.UploadUrl = imageTableStorage.GetUploadUrl(imageTableEntity);
            imageEntity.Description = imageTableEntity.Description; 

            return Json(imageEntity); 
        }

        [HttpPost("{id}/uploadComplete")]
        public async Task<IActionResult> UploadCompleteAsync(string id)
        {
            var imageTableEntity = await this.imageTableStorage.GetAsync(id);
            if(imageTableEntity == null)
            {
                return StatusCode((int)HttpStatusCode.NotFound);
            }
            imageTableEntity.UploadComplete = true;

            await this.imageTableStorage.AddOrUpdateAsync(imageTableEntity); 
                                                                             
            return Json(ImageEntity.FromTableEntity(imageTableEntity)); 
        }

        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteAsync(string id)
        {
            if (await this.imageTableStorage.DeleteAsync(id))
            {
                return StatusCode((int) HttpStatusCode.NoContent);
            }
            return StatusCode((int) HttpStatusCode.NotFound);
        }

        [HttpDelete]
        public async Task<IActionResult> PurgeAsync()
        {
            await this.imageTableStorage.PurgeAsync();
            return StatusCode((int)HttpStatusCode.NoContent);
        }
    }
}
