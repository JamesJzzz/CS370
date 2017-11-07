// @author: Fran Bueti, James Zhang, Alex Vallorosi
// @pledge: I pledge my honor that I have abided by the Stevens Honor System
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

#define ALPHABET_LENGTH    26
#define OPERATION_BUF_SIZE  5
#define NAME_BUF_SIZE      22
#define MAX_BUF_SIZE       27

typedef struct node{
    int num_children;
    struct node *children[ALPHABET_LENGTH];
} trie_node;

// create new trie node
struct node *getNode(void){
	int i;
    struct node *node = NULL;
    node = (struct node *)malloc(sizeof(struct node));
    if (node){
        for (i = 0; i < ALPHABET_LENGTH; i++)
            node->children[i] = NULL;
    }
    return node;
}
 
// add(name) where name is a string denoting a contact name
void add(struct node *root, const char *name){
    int index, i;
    struct node *start = root;
    // start with the root
    for (i = 0; i < strlen(name); i++){
        index = (int)name[i] - (int)'a';
        if (!start->children[index]){
            start->children[index] = getNode();
        }
        start = start->children[index];
	start->num_children++;
    }
}

//find(partial) returns a count of number of string that contain partial
int find(trie_node *node, char *partial){
	while(*partial != '\n'&& node)
		node = node->children[*partial++ - 'a']; // recurse over the root until we reach the bottom
	if(node != NULL)
		return node->num_children; // always return the number of a nodes children
	else
		return 0; // base case
}

char* trim(char* a){
	if (a[0] == 'a') {
		a += 4; // trim add// was saving a newline so had to strip the last character
	} else {
		a += 5; // trim find
	}
	return a;
}

int main(int argc, char const *argv[]){
	int t,temp;
	scanf("%d\n", &t);
	
	// Create a new node and buff
	struct node *contacts = getNode();
	char* buff = malloc(MAX_BUF_SIZE);

	while (t != 0) { // Loop until amount of commands left is zero
		char* s = buff;
		fgets(s, MAX_BUF_SIZE, stdin);
		t--;

		// If command is add, add to the contacts node
		if (s[0] == 'a') {
			s = trim(s);
			if (s != NULL) add(contacts, s);
		} else if (s[0] == 'f') { // else if it's find, then find the string
			s = trim(s);
			if (s != NULL) temp = find(contacts, s);
			printf("%d\n", temp);
		}
	}
	free(buff);
  	free(contacts);
  	return 0;
}

