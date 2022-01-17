using CloudStorage.Entities;
using Microsoft.Azure.Cosmos.Table;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace CloudStorage.Models
{
    /// <summary>
    /// This class is persisted to a database. It's a "model."
    /// </summary>
    public class ImageTableEntity : TableEntity
    {
        public ImageTableEntity(string userName, string name)
        {
            this.UserName = userName;
            this.Name = name;
        }

        public ImageTableEntity()
        {

        }

        public string UserName
        {
            get { return this.PartitionKey; }
            set { this.PartitionKey = value; }
        }

        public string Id {
            get { return this.RowKey; }
            set { this.RowKey = value; }
        }

        public string Name { get; set; }

        public bool UploadComplete { get; set; }

        public string Description { get; set; }

    }
}
