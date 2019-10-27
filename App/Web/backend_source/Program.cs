using System;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Text.Json;
namespace CParse
{
    class Program
    {

        public struct larr
        {
            public double lat;
            public double lng;
            public double radius;
            public int time;
            public larr(string[] s)
            {
                lat = Convert.ToDouble(s[1].Replace('.', ','));
                lng = Convert.ToDouble(s[2].Replace('.', ','));
                radius= Convert.ToDouble(s[3].Replace('.', ','));
                DateTime FT = DateTime.Parse(s[6]);
                DateTime ST = DateTime.Parse(s[7]);
                time = Math.Abs( ST.Second - FT.Second);
            }
        }
        public struct LineS
        {
            public string id;
            public double C0;
            public double C1;
            public int radius;
            public int Pos_M;
            public int M;
            public DateTime FT;
            public DateTime ST;
            public LineS(string[] s)
            {
                id = s[0];
                C0 = Convert.ToDouble(s[1].Replace('.', ','));
                C1 = Convert.ToDouble(s[2].Replace('.', ','));
                radius = Convert.ToInt32(s[3]);
                Pos_M = Convert.ToInt32(s[4]);
                M = Convert.ToInt32(s[5]);
                FT = DateTime.Parse(s[6]);
                ST = DateTime.Parse(s[7]);
            }
        }
        static public double dist(double x, double y, double x0, double y0)
        {
            double k0 = System.Math.Abs(x - x0);
            double k1 = System.Math.Abs(y - y0);
            return Math.Sqrt((k0 * k0) + (k1 * k1));

        }
        static void Main(string[] args)
        {

            bool b = true;
            IPEndPoint ipEndPoint = new IPEndPoint(IPAddress.Any, 11000);
            Socket sListener = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            sListener.Bind(ipEndPoint);
            sListener.Listen(10);
            while (true)
            {
                Socket handler = sListener.Accept();
                try
                {
                    Console.WriteLine("Коннект");
                     byte[] bytes = new byte[2048];
                        int bytesRec = handler.Receive(bytes);
                        string words = Encoding.Default.GetString(bytes, 0, bytesRec);
                        if (words == "") break;

                        string IDDQD = "1568168";
                        List<LineS> lst = new List<LineS>();
                    Console.WriteLine("EF000EF");
                    


                        List<LineS> l = new List<LineS>();
                        List<larr> f = new List<larr>();
                        string[] line = words.Split('\n');
                        
                        Console.WriteLine("EF111EF   "+ line);
                        for (var i = 0; i< line.Length; i++)
                        
                        {
                            string[] ds = line[i].Split(',');
                            LineS sL = new LineS(ds);
                            larr stL = new larr(ds);
                            if (ds[0] == IDDQD)
                            {

                                if (l.Count > 0)
                                {
                                    if (l[l.Count - 1].radius < dist(l[l.Count - 1].C0, l[l.Count - 1].C1, sL.C0, sL.C1))
                                    {
                                    

                                        l.Add(sL);
                                        f.Add(stL);
                                    Console.WriteLine("EF212EF");
                                        if (l.Count > 2)
                                        {
                                            if (dist(l[l.Count - 3].C0, l[l.Count - 3].C1, l[l.Count - 1].C0, l[l.Count - 1].C1) < dist(l[l.Count - 3].C0, l[l.Count - 3].C1, l[l.Count - 2].C0, l[l.Count - 2].C1))
                                            {
                                                l.RemoveAt(l.Count - 1);
                                                f.RemoveAt(f.Count - 1);
                                            }
                                        }
                                        Console.WriteLine(l.Count);
                                    Console.WriteLine("EF454EF");
                                }
                                    else
                                    {
                                        l[l.Count - 1].FT.AddSeconds(sL.FT.Second);
                                        l[l.Count - 1].ST.AddSeconds(sL.ST.Second);
                                    Console.WriteLine("EF885EF");

                                }
                                }else
                            {

                                l.Add(sL);
                                f.Add(stL);
                                Console.WriteLine("EF212EF");
                            }
                            }


                        Console.WriteLine("EF919EF", f.Count);

                        string reply = "[";
                            foreach(larr hb in f)
                        {
                            string[] st = hb.lat.ToString().Split(',');
                            string[] sg = hb.lng.ToString().Split(',');
                            reply += '{';
                            reply += "\"lat\": "+st[0]+'.'+ st[1] + ",";
                            reply += "\"lng\": " + sg[0] + '.' + sg[1] + ",";
                            reply += "\"radius\": "+hb.radius+",";
                            reply += "\"time\": "+hb.time;
                            reply += "}" + '\n';

                        }

                        reply += ']';


                        Console.WriteLine("Отправленно", reply + "\n");
                        byte[] msg = Encoding.Default.GetBytes(reply + "\n");
                        int fr = handler.Send(msg);

                        Console.WriteLine("EF212EF");
                        sListener.Shutdown(SocketShutdown.Both);
                    }
                    Console.WriteLine("EF272EF");
                    sListener.Close();
                }
                catch (Exception)
                {
                    if (b)
                        Console.WriteLine("\nДисконнект ");
                    b = false;
                }
            }

            Console.ReadKey();
        }
    }
}
