using Amazon;
using Amazon.Runtime;
using Amazon.SQS;
using Amazon.SQS.Model;
using Common;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Common.Entities;
using Kuscotopia.Entities;

namespace Kuscotopia.Services
{
    public class QueueService
    {

        private static BasicAWSCredentials credentials;
        private static AmazonSQSClient amazonSQSClient;

        public QueueService()
        {
            credentials = new BasicAWSCredentials(Constants.QueueKeyId, Constants.QueueKey);
            amazonSQSClient = new AmazonSQSClient(credentials, RegionEndpoint.USEast2);
        }

        public async Task QueueWorkAsync(int WorkCount)
        {
            WorkEntity Work = new WorkEntity();
            for (int i = 0; i < WorkCount; i++)
            {

                Random rand = new Random();
                int num = rand.Next(1, 4); 
                
                if (num == 1) //carry
                {

                    Work.Type = "Carry";
                    Work.Message = "Bricks";
                    Work.Data = null;

                }
                else if (num == 2) //build
                {

                    Work.Type = "Build";
                    Work.Message = "Church";
                    Work.Data = BuildData1();

                }
                else if (num == 3) //survey
                {

                    Work.Type = "Survey";
                    Work.Message = "Perfect!";
                    Work.Data = SurveyData1();

                }


                var WorkSerialized = JsonConvert.SerializeObject(Work);

                var sendMessageRequest = new SendMessageRequest()
                {

                    QueueUrl = Constants.QueueUrl,
                    MessageBody = WorkSerialized


                };

                await amazonSQSClient.SendMessageAsync(sendMessageRequest);

            }
        }
            

        public String BuildData1()
        {
            Random rand = new Random();
            int num = rand.Next(1,6);
            return num.ToString(); 
        }

        public String SurveyData1()
        {
            Random rand = new Random();
            int num = rand.Next(500,1001);
            return num.ToString();
        }


    }
}
