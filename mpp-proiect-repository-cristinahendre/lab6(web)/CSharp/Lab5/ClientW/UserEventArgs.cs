using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace ClientW
{
    public enum ChatUserEvent
    {
        Refresh
    };
    public class UserEventArgs : EventArgs
    {
        private readonly ChatUserEvent userEvent;
        private readonly Object data;

        public UserEventArgs(ChatUserEvent userEvent, object data)
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
