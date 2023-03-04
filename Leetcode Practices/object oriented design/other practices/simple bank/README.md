# Simple Bank

Project structure:  
```
src/
    main/
        java/
    test/
        java/
```
  
Written in Java. The source code include all the classes and methods, the main structure looks like following:  
```
./model/
    Bank - OneToMany relationship with bank account and store its total balance
    Account - bank account, which represent customer information (balance, customer name) in a particular bank
    Customer - real customer, who can execute create bank account, withdraw, deposit operations
./utils/
    BankCode
Solution(Main)
```
For engineering/production way, should split the `model` part into 3 package: `dao`, `service`, `model` and import them, exception should use customize exception class instead of simple RuntimeException.  

Tests include the following test cases:  
```
TestSingleAccountOperation - include edge cases like withdraw negative amount
TestMultipleAccountsOperations - include deposit/withdraw non-integer amount
TestInvalidAccountOperations - non-exist account operations check
```

## Unit Tests
To run the code and tests, just simply type `sh run.sh` command in the same directory.  

The first test includes a basic usage case:   
1. Customer Alice deposit $30 to her new account, then account balance and bank total balance remain $30
2. Customer Alice withdraw $20, then account balance and bank total balance remain $10
3. Customer Alice try to withdraw $11, then exception throw out and say "Account balance insufficient to withdraw"
  
The second test include multiple customers operations cases:
* Customer Ana and Bob create Account and deposit/withdraw non-integer amount, each balance should be calculate separately and the bank total amount should be correct

## Design Decisions and Trade-Offs
* The Account withdraw operation will check Account balance but not bank total balance since if Account balance is always not negative then bank total balance will not be negative.
* Customer may have multiple Account in different Bank, so relationship between Bank and Account is OneToMany, and relationship between Customer and Account is OneToMany as well. To get all Bank Account for a particular Customer, we could applied the comment part customerId (customer unique ID for example social ID or email address etc) and Bank's customersAccounts to retrieve them.

