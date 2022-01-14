using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Sockets;
using System.Text;
using System.Threading;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Cerinta1.Domain.Validator;
using Cerinta1.networking;
using Cerinta1.Repository;
using Cerinta1.Service;
using Network;
using protobuf;

namespace Server
{
    
    class StartServer
    {
        public static void Main(string[] args)
        {

            // IUserRepository userRepo = new UserRepositoryMock();
            //  IUserRepository userRepo=new UserRepositoryDb();
            // IMessageRepository messageRepository=new MessageRepositoryDb();
            ICazRepo cazRepo = new CazRepo();
            IDonatorRepo donatorRepo = new DonatorRepo();
            IDonatieRepo donatieRepo = new DonatieRepo(cazRepo, donatorRepo);
            IVoluntarRepo voluntarRepo = new VoluntarRepo();
            IValidator<Donatie> doValidator = new DonatieValidator();
            IValidator<Donator> donatorValidator = new DonatorValidator();
           
            IService serviceImpl = new SuperService(cazRepo,donatieRepo,donatorRepo,
                voluntarRepo,donatorValidator,doValidator);

            // IChatServer serviceImpl = new ChatServerImpl();
            SerialChatServer server = new SerialChatServer("127.0.0.1", 55556, serviceImpl);
            server.Start();
            Console.WriteLine("Server started ...");
            //Console.WriteLine("Press <enter> to exit...");
            Console.ReadLine();

        }
    }

    public class SerialChatServer : ConcurrentServer
    {
        private IService server;
        private ProtoChatWorker worker;
        public SerialChatServer(string host, int port, IService server) : base(host, port)
        {
            this.server = server;
            Console.WriteLine("SerialChatServer...");
        }
        protected override Thread createWorker(TcpClient client)
        {
            worker = new ProtoChatWorker(server, client);
            return new Thread(new ThreadStart(worker.run));
        }
    }
}
