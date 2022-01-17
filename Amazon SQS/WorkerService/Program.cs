using Amazon;
using Amazon.Runtime;
using Amazon.SQS;
using Amazon.SQS.Model;
using Common;
using Common.Entities;
using Newtonsoft.Json;
using System;
using System.Threading.Tasks;

namespace WorkerService
{
    class Program
    {
        private static BasicAWSCredentials credentials;
        private static AmazonSQSClient amazonSQSClient;

        static async Task Main(string[] args)
        {
            Console.WriteLine("Constructing Kuscotopia...");

            credentials = new BasicAWSCredentials(Constants.QueueKeyId, Constants.QueueKey);
            amazonSQSClient = new AmazonSQSClient(credentials, RegionEndpoint.USEast2);

            await ReadMessagesAsync();
        }

        public static async Task ReadMessagesAsync()
        {
            var request = new ReceiveMessageRequest();

            request.QueueUrl = Constants.QueueUrl;
            request.MaxNumberOfMessages = 10;
            request.WaitTimeSeconds = 10;
            

            while (true)
            {
                var messages = await amazonSQSClient.ReceiveMessageAsync(request);
               
                foreach (var message in messages.Messages)
                { 
                    var FromQueue = JsonConvert.DeserializeObject<WorkEntity>(message.Body);

                    switch (FromQueue.Type)
                    {
                        case "Carry":

                            Console.WriteLine("Carry: " + FromQueue.Message);
                            break;

                        case "Build":

                            WorkEntity Work = new WorkEntity();
                            int num = Convert.ToInt32(FromQueue.Data);

                            if (num > 0)
                            {
                                Console.WriteLine("Build: There are " + num + " steps left.");

                                Work.Type = FromQueue.Type;
                                int convertNum = Convert.ToInt32(FromQueue.Data) - 1;
                                Work.Data = convertNum.ToString();


                                var WorkSerialized = JsonConvert.SerializeObject(Work);

                                var sendMessageRequest = new SendMessageRequest()
                                {

                                    QueueUrl = Constants.QueueUrl,
                                    MessageBody = WorkSerialized

                                };

                                await amazonSQSClient.SendMessageAsync(sendMessageRequest);

                            }

                            if (Convert.ToInt32(FromQueue.Data) == 0)
                            {
                                Console.WriteLine("Build: The building is complete.");
                            }
                            break;

                        case "Survey":

                            Console.Write("Survey: Start Delay . . .");
                            await Task.Delay(Convert.ToInt32(FromQueue.Data));
                            Console.WriteLine(" Finish Delay . . .");
                            break; 

                    }


                    var deleteTask = amazonSQSClient.DeleteMessageAsync(new DeleteMessageRequest()
                    {
                        QueueUrl = Constants.QueueUrl,
                        ReceiptHandle = message.ReceiptHandle
                    });
                }
            }
        }
    }
}
