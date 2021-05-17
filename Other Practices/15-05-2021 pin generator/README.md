# Pin Generator

## Pin Requirements:
* Pin should be 4 digit format, range: `[0000, 9999]`.
* Increase by 1 or Decrease by 1 within any two adjacent digits is not allowed.
* Any adjacent digits equals each other is not allowed.
  
(which means any two adjacent digits' difference's absolute value should not be 0 or 1.)
  
## Pin List Requirements:  
* Pin Generator able to output a pin list contains 0-1000 pins.
* Every pin in the generated pin list should be unique.

## Source Code
* [Pin Generator Algorithm](./src/main/java/com/exmaple/pin/utils/PinGenerator.java)
* [Unit Tests](./src/test/java/com/exmaple/pin/utils/PinGeneratorTest.java)

## Run Tests
Run `gradle clean test` in this path.
