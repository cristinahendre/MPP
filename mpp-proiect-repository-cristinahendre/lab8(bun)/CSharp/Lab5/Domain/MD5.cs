using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Numerics;
using System.Security.Cryptography;
using System.Text;
using System.Threading.Tasks;

namespace Cerinta1.Domain
{
    public class MD
    {
       
            public static void main(String[] args)
            {


            }

            public static String Md5(String input)
            {


                byte[] bytes = { 0x35, 0x24, 0x76, 0x12 };
                MD5 md5 = new MD5CryptoServiceProvider();
                byte[] result = md5.ComputeHash(bytes);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < result.Length; i++)
            {
                    sb.Append(result[i].ToString("x2"));
            }
                return sb.ToString();



        }

    }

    }

