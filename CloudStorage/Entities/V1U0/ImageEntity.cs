using CloudStorage.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace CloudStorage.Entities.V1U0
{
    [ApiVersion("1.0")]
    public class ImageEntity
    {

        public string UserName { get; internal set; }

        public string Id { get; internal set; }

        [MinLength(3)]
        public string Name { get; set; }

        public string UploadUrl { get; internal set; }




        public ImageTableEntity ToTableEntity()
        {
            return new ImageTableEntity()
            {
                UserName = this.UserName,
                Name = this.Name
            };
        }

        public static ImageEntity FromTableEntity(ImageTableEntity tableEntity)
        {
            return new ImageEntity()
            {
                UserName = tableEntity.UserName,
                Id = tableEntity.Id,
                Name = tableEntity.Name
            };
        }
    }
}
