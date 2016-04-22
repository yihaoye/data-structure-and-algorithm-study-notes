//*****************************************************************
//  HashTable.h
//  HashTable
//
//  Created by Karlina Beringer on June 18, 2014.
//
//  This header file contains the Hash Table class declaration.
//  Hash Table array elements consist of Linked List objects.
//*****************************************************************

#ifndef HashTable_h
#define HashTable_h

#include "LinkedList.h"

//*****************************************************************
// Hash Table objects store a fixed number of Linked Lists.
//*****************************************************************
class HashTable
{
private:
    
    // Array is a reference to an array of Linked Lists.
    LinkedList * array;
    
    // Length is the size of the Hash Table array.
    int length;
    
    // Returns an array location for a given item key.
    int hash( string itemKey );
    
public:
    
    // Constructs the empty Hash Table object.
    // Array length is set to 13 by default.
    HashTable( int tableLength = 13 );
    
    // Adds an item to the Hash Table.
    void insertItem( Item * newItem );
    
    // Deletes an Item by key from the Hash Table.
    // Returns true if the operation is successful.
    bool removeItem( string itemKey );
    
    // Returns an item from the Hash Table by key.
    // If the item isn't found, a null pointer is returned.
    Item * getItemByKey( string itemKey );
    
    // Display the contents of the Hash Table to console window.
    void printTable();
    
    // Prints a histogram illustrating the Item distribution.
    void printHistogram();
    
    // Returns the number of locations in the Hash Table.
    int getLength();
    
    // Returns the number of Items in the Hash Table.
    int getNumberOfItems();
    
    // De-allocates all memory used for the Hash Table.
    ~HashTable();
};

#endif

//*****************************************************************
// End of File
//*****************************************************************