// Zhiyuan (James) Zhang, Francesca Bueti, Alexander Vallorosi
// I pledge my honor that I have abided by the Stevens Honors System.

#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

int main(){   
int i, j, k, num, key, val, position, sum=0;
char ch;

std::vector<int> cipher;
std::vector<int> key1s;
std::vector<int> key2s;
std::vector<int> key3s;

// read in file + parse ints to vector
fstream file("cipher.txt", fstream::in);
while (file >> num >> ch) {
	cipher.push_back(num);
}
file >> num;		//includes last char
cipher.push_back(num);

// finds possible letters for each 
for(i=0; i<3; i++){
	key = 97; // starts at 'a'
	while(key <= 122){ // ends at 'z'
		//cout << "STARTING OVER WITH KEY " << (char) key << endl;
		position = i;
		while(position <= cipher.size()){
			val = cipher[position] ^ key;
			//cout << cipher[position] << ":" << val << ":" << (char) val << " " << endl;
			// if the xor produced a character we don't normally see in english			
			if(val<32 || (35<=val && val<=38) || val==42 || val==43 || (60<=val && val<=62) || val==64 || (91<=val && val<=96) || (123<=val && val<=127)){
				//cout << "NOT ENGLISH " << endl;
				position = cipher.size() + 1;	//break while loop	
			}
			else if(cipher.size()-position < 3){	//if it got through the entire message without weird characters, store that letter
				//cout << (char) key;
				if(i==0) key1s.push_back(key);
				else if(i==1)  key2s.push_back(key);
				else  key3s.push_back(key);
			}
			position+=3;		
		}
		key++;
	}
}

// for every possible combo of letters that haven't already failed (in this case, only GOD), print the message, key, and find the sum
cout << "Decrypted Message: " << endl;
for(i=0; i<key1s.size(); i++){
	for(j=0; j<key1s.size(); j++){
		for(k=0; k<key1s.size(); k++){
			for(position=0; position <= cipher.size(); position+=3){
				cout << (char) (cipher[position] ^ key1s[i]);
				if (cipher.size()-position >= 3){
					cout << (char) (cipher[position+1] ^ key2s[j]);
					cout << (char) (cipher[position+2] ^ key3s[k]);
				}
				if (cipher.size()-position >= 3) sum += (cipher[position] ^ key1s[i]) + (cipher[position+1] ^ key2s[j]) + (cipher[position+2] ^ key3s[k]);
				else sum += (cipher[position] ^ key1s[i]);
			}
		}
	}
}

cout << endl << "Sum: " << sum << endl;
cout << "Key: " << (char) key1s[0] << (char) key2s[0] << (char) key3s[0] << endl;
}
