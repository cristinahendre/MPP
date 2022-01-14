using System;
using System.Collections.Generic;
using System.Linq;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Threading.Tasks;
using Cerinta1.Domain;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;

namespace ClientN
{
    class Test
    {

		static HttpClient client = new HttpClient();

		public static void Main(string[] args)
		{
			Console.WriteLine("Hello World!");
			RunAsync().Wait();
		}


		static async Task RunAsync()
		{
			client.BaseAddress = new Uri("http://localhost:8080/api/cazuri");
			client.DefaultRequestHeaders.Accept.Clear();
			//client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("text/plain"));
			client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));
			// Get the string
			//String text = await GetTextAsync("http://localhost:8080/chat/greeting");
			//Console.WriteLine("am obtinut {0}", text);
			//Get one user
			int id = 2;
			Console.WriteLine("\nCaut user cu id: {0}", id);
			String result = await GetTextAsync("http://localhost:8080/api/cazuri/" + id);
			Console.WriteLine("\nAm primit {0}", result);

            Console.WriteLine("\nCaut toate cazurile");
			String all = await GetTextAsync("http://localhost:8080/api/cazuri");
			Console.WriteLine("\nAm primit {0}", all);

			CazCaritabil c = new CazCaritabil("Beautiful Madness", 100);
            Console.WriteLine("\nAdaug un nou caz: "+c);
			String added = await PostTextAsync("http://localhost:8080/api/cazuri",c);
			Console.WriteLine("\nAm primit id-ul cazului adaugat: {0}", added);
			int idNou = Int32.Parse(added);

			c.Suma_donata = 188;
			c.Id = idNou;
            Console.WriteLine("\nModific cazul asa: "+c);
			String addr = "http://localhost:8080/api/cazuri" + "/" + c.Id;
			String rez1 = await PutTextAsync(addr, c);
            Console.WriteLine("\nupdate realizat-> all :");
			all = await GetTextAsync("http://localhost:8080/api/cazuri");
			Console.WriteLine("\nAm primit {0}", all);

			Console.WriteLine("\ndeleting "+c.Id);
			
			rez1 = await DeleteTextAsync(addr);
			all = await GetTextAsync("http://localhost:8080/api/cazuri");
			Console.WriteLine("\nAfter delete: {0}", all);



			Console.ReadLine();
		}




		static async Task<String> GetTextAsync(string path)
		{
			String product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
				product = await response.Content.ReadAsStringAsync();
			}
			return product;
		}


		static async Task<String> PostTextAsync(string path, CazCaritabil c)
		{

			JObject min = new JObject(
		
				 new JProperty("nume",c.Nume),
				 new JProperty("suma_donata", c.Suma_donata)		
				 
		 );

			using (HttpClient client = new HttpClient())
			{
				var httpContent = new StringContent(min.ToString(), Encoding.UTF8, "application/json");

				using (HttpResponseMessage response = await client.PostAsync(path, httpContent))
				{
					response.EnsureSuccessStatusCode();
					string responseBody = await response.Content.ReadAsStringAsync();
					return responseBody;
				}
			}
		
		}




		static async Task<String> PutTextAsync(string path, CazCaritabil c)
		{

			JObject min = new JObject(

				 new JProperty("nume", c.Nume),
				 new JProperty("suma_donata", c.Suma_donata)

		 );

			using (HttpClient client = new HttpClient())
			{
				var httpContent = new StringContent(min.ToString(), Encoding.UTF8, "application/json");

				using (HttpResponseMessage response = await client.PutAsync(path, httpContent))
				{
					response.EnsureSuccessStatusCode();
					string responseBody = await response.Content.ReadAsStringAsync();
					return responseBody;
				}
			}

		}



		static async Task<String> DeleteTextAsync(string path)
		{


			using (HttpClient client = new HttpClient())
			{
				//var httpContent = new StringContent(id.ToString(), Encoding.UTF8, "application/json");

				using (HttpResponseMessage response = await client.DeleteAsync(path)) 
				{
					response.EnsureSuccessStatusCode();
					string responseBody = await response.Content.ReadAsStringAsync();
					return responseBody;
				}
			}

		}





		static async Task<CazCaritabil> GetUserAsync(string path)
		{
			CazCaritabil product = null;
			HttpResponseMessage response = await client.GetAsync(path);
			if (response.IsSuccessStatusCode)
			{
               // product = await response.Content.ReadAsAsync<CazCaritabil>();
			}
			return product;
		}

	}
	
}
