/*
 * title	HASHIT
 * author	James Zhang, Francesca Bueti, Alex Vallorosi
 * date		12FEB2017
 * pledge	I pledge my honor that I have abided by the Stevens Honor System
 *
 */
#include "hashit.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void clear_table(hash_set *set) {
	int i;
	// Loop through the table and write NULL over all values
	for(i = 0; i < TABLE_SIZE; ++i) {
		free(set->keys[i]);
		set->keys[i] = NULL;
	}
}

int hash(char *key) {
	long sum = 0;
	long i = 1;
	if (*key == '\0') sum = 1;
	while(*key != '\0') {
		sum += (*key) * i;
		++i;
		++key;
	}
	return (19 * sum) % 101;
}

int search_table(hash_set *set, char *key) {
	// Hash the key
	int h = hash(key);
	int j, loc;
	for(j = 0; j < 20; ++j) {
		// Find location of key in table
		loc = (h + 23 * j + j * j) % 101;
		// If the key is not in the table, return 0
		if(set->keys[loc] == NULL) return 0;
		// Else if it exists, return 1
		if(strcmp(set->keys[loc], key) == 0) {
			return 1;
		}
	}
	return 1;
}

int insert_key(hash_set *set, char *key) {
	// Hash the key
	int h = hash(key);
	int j, loc;
	// Search the table, and if key exists, don't insert
	if (search_table(set, key) == 1) return 0;
	for(j = 0; j < 20; ++j) {
		// Find location to insert key into table
		loc = (h + 23 * j + j * j) % 101;
		if(set->keys[loc] == NULL) {
			// Allocate some memory for the key
			set->keys[loc] = malloc(100);
			// Copy the key into the table
			strcpy(set->keys[loc], key);
			// Increase the size of the table
			(set->num_keys)++;
			break;
		}
	}
	return 1;
}

int delete_key(hash_set *set, char *key) {
	// Hash the key
	int h = hash(key);
	int j, loc;
	for(j = 0; j < 20; ++j) {
		// Get the location of the key in the table
		loc = (h + 23 * j + j * j) % 101;
		// If the key location is null, do nothing & return
		if(set->keys[loc] == NULL) return 0;
		// Otherwise, go ahead and delete the key
		if(strcmp(set->keys[loc], key) == 0) {
			set->keys[loc] = NULL;
			(set->num_keys)--;
		}
	}
	return 1;
}

void display_keys(hash_set *set) {
	int i;
	// Display the size of the table
	printf("%d\n", set->num_keys);
	// Loop over each key
	for(i = 0; i < TABLE_SIZE; ++i) {
		// Display the key if it is not null
		if(set->keys[i] != NULL) {
			printf("%d:%s\n", i, set->keys[i]);
		}
	}
}

int main() {
	int t;
	scanf("%d", &t);

	while(t--) {
		// create a new hash_set
		hash_set hash_table;

		int i, j;
		// zero out the table with NULL values
		for(i = 0; i < 101; ++i) {
			hash_table.keys[i] = NULL;
		}
		hash_table.num_keys = 0;

		int n;
		scanf("%d", &n);

		char string[50];
		for(i = 0; i < n; ++i) {
			scanf("%s", string);
			if(string[0] == 'A') {
				// Add the a new key to the table
				insert_key(&hash_table, string + 4);
			} else {
				// Delete the key from the table
				delete_key(&hash_table, string + 4);
			}
		}

		// Print out the table
		display_keys(&hash_table);
		clear_table(&hash_table);
	}
	return 0;
}
