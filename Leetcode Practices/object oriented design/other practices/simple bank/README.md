# Simple Bank

Entities:
* Bank - OneToMany relationship with bank account and store its total balance
* Account - bank account, which represent customer information (bank code, balance, account number) in a particular bank
* Customer - real customer, who can execute create bank account, withdraw, deposit operations

Repositories:
* BankRepository - Dao for bank entity
* AccountRepository - Dao for account entity

Services:
* BankService - which handle customer operation request and update data in bank object.

Exceptions:
* DataAccessException - customized DataAccessException to mock operation failure on dao layer
* OperationException - customer operation failure exception

## Test instructions
`gradle clean test`

or could be run by IDE (e.g. IntelliJ)

The test includes a basic usage case: 
1. Customer deposit $30 to her new account, then account balance and bank total balance remain $30
2. Customer withdraw $20, then account balance and bank total balance remain $10
3. Customer try to withdraw $11, then exception throw out and say "not enough balance"

Also a second test is implemented for test multiple customer operation still works well. 
