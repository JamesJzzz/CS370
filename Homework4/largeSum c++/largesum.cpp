#include <fstream>
#include <iostream>
#include <string>
#include <ctime>
#include <cmath>
#include <cstddef>

using namespace std;

int main()
{
	clock_t time = clock();
	unsigned long long carry = 0;
	string fullSum, a;

	ifstream infile("thefile.txt");
	ofstream outfile("file1.txt");
	while (infile >> a)
	{
	 	outfile << a.append("\r\n");
	}
	infile.close();
	outfile.close();

	for (int i = 0; i < 4; i++)
	{
		unsigned long long temp16Digits = 0, result = 0;
		string b, temp;
		int lock = 0;
		if (carry != 0)
		{
			result += carry;
		}

		if (i == 0 || i == 2)
		{
			ifstream in("file1.txt");
			ofstream out("file2.txt");
			while (in >> a)
			{
				lock = 1;
				if (a.size() < 16)
				{
					if (a.size() == 1)
					{
						b = a;
					}
					else{
						b = a.substr(0,a.size());
					}
					a.clear();
					out << a.append("\r\n");
				}
				else{
					b = a.substr(a.size()-16, a.size());
					a.resize(a.size() - 16);
					out << a.append("\r\n");
				}
				temp16Digits = stoull(b, NULL, 10);
				result += temp16Digits;
			}
			in.close();
			out.close();
		}
		else{
			ifstream in("file2.txt");
			ofstream out("file1.txt");
			while (in >> a)
			{
				lock = 1;
				if (a.size() < 16)
				{
					if (a.size() == 1)
					{
						b = a;
					}
					else{
						b = a.substr(0,a.size());
					};
					a.clear();
					out << a.append("\r\n");
				}
				else{
					b = a.substr(a.size()-16, a.size());
					a.resize(a.size() - 16);
					out << a.append("\r\n");
				}
				temp16Digits = stoull(b, NULL, 10);
				result += temp16Digits;
			}
			in.close();
			out.close();
		}
		if (lock != 0)
		{
			carry = floor(result / 10000000000000000);
			if (carry > 0)
			{
				result = result - carry * 10000000000000000;
			}
			temp = to_string(result);
			if (temp.size() < 16)
			{
				/*If the result is not 16 digits, we add zero to the front to get more digits*/
				string n;
				int digits = 16 - temp.size();
				while(digits){
					string m = "0";
					m.append(n);
					n = m;
					digits--;
				}
				n.append(temp);
				temp = n;
			}
			temp.append(fullSum);
			fullSum = temp;
		}
	}

	size_t found = fullSum.find_first_not_of("0");
	fullSum = fullSum.substr(found, a.size()-found);
	
	cout << "Full sum: " << fullSum << endl;
	fullSum.resize(10);
	cout << "First 10 digits: " << fullSum << endl;

	time = clock() - time;
  	cout << "Program takes: " << time <<" miliseconds to be finished.";
	return 0;
}