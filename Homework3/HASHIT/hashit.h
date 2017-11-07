/*
 * title	HASHIT
 * author	James Zhang, Francesca Bueti, Alex Vallorosi
 * date		12FEB2017
 * pledge	I pledge my honor that I have abided by the Stevens Honor System
 *
 */

#define TABLE_SIZE 101

typedef struct {
    char* keys[TABLE_SIZE];
    int num_keys;
} hash_set;

void clear_table(hash_set *set);

int hash(char *key);

int insert_key(hash_set *set, char *key);

int delete_key(hash_set *set, char *key);

void display_keys(hash_set *set);
