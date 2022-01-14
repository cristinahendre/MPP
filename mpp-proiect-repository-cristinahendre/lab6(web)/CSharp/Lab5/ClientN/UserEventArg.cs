using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientN
{
    public enum ChatUserEvent
    {
        Refresh
    };
    public class UserEventArg : EventArgs
    {
        private readonly ChatUserEvent userEvent;
        private readonly Object data;

        public UserEventArg(ChatUserEvent userEvent, object data)
        {
            this.userEvent = userEvent;
            this.data = data;
        }

        public ChatUserEvent UserEventType
        {
            get { return userEvent; }
        }

        public object Data
        {
            get { return data; }
        }
    }
}
