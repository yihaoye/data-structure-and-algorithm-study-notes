# Simple Bank

Models:  
* Bank - OneToMany relationship with bank account and store its total balance
* Account - bank account, which represent customer information (balance, customer name) in a particular bank
* Customer - real customer, who can execute create bank account, withdraw, deposit operations

## Unit Tests
To run the tests, just simply run the run.sh bash script in the same folder.  

The test includes a basic usage case:   
1. Customer deposit $30 to her new account, then account balance and bank total balance remain $30
2. Customer withdraw $20, then account balance and bank total balance remain $10
3. Customer try to withdraw $11, then exception throw out and say "not enough balance"
  
Another test is needed to be implemented for test multiple customer operation still works well.  
