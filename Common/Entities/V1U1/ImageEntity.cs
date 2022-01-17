using CloudStorage.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Threading.Tasks;

namespace CloudStorage.Entities.V1U1
{
    [ApiVersion("1.1")]
    public class ImageEntity
    {

        public string UserName { get; internal set; }

        public string Id { get; internal set; }

        [MinLength(3)]
        public string Name { get; set; }

        public string UploadUrl { get; internal set; }

        [Required]
        [MinLength(5)]
        public string Description { get; set; }

        public ImageTableEntity ToTableEntity()
        {
            return new ImageTableEntity()
            {
                UserName = this.UserName,
                Name = this.Name,
                Description = this.Description
            };
        }

        public static ImageEntity FromTableEntity(ImageTableEntity tableEntity)
        {
            return new ImageEntity()
            {
                UserName = tableEntity.UserName,
                Id = tableEntity.Id,
                Name = tableEntity.Name,
                Description = tableEntity.Description
    };
        }
    }
}
