# Simple Bank

Models:  
* Bank - OneToMany relationship with bank account and store its total balance
* Account - bank account, which represent customer information (balance, customer name) in a particular bank
* Customer - real customer, who can execute create bank account, withdraw, deposit operations

## Unit Tests
To run the code and tests, just simply type `sh run.sh` command in the same directory.  

The test includes a basic usage case:   
1. Customer Alice deposit $30 to her new account, then account balance and bank total balance remain $30
2. Customer Alice withdraw $20, then account balance and bank total balance remain $10
3. Customer Alice try to withdraw $11, then exception throw out and say "not enough balance"
  
Another test is needed to be implemented for test multiple customer operation still works well.  
