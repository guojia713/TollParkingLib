# TollParkingLib

This is a Java API to simulate a toll parking

## Description

A toll parking contains multiple parking slots of different types :
- the standard parking slots for sedan cars (gasoline-powered)
- parking slots with 20kw power supply for electric cars
- parking slots with 50kw power supply for electric cars
20kw electric cars cannot use 50kw power supplies and vice-versa.
Every Parking is free to implement is own pricing policy :
- Some only bills their customer for each hour spent in the parking (nb hours * hour price)
- Some other bill a fixed amount + each hour spent in the parking (fixed amount + nb hours *
hour price)
In the future, there will be other pricing policies
Cars of all types come in and out randomly, the API must :
- Send them to the right parking slot of refuse them if there is no slot (of the right type) left.
- Mark the parking slot as Free when the car leaves it
- Bill the customer when the car leaves.

## Requirements
JAVA 8 or plus

## Installation
For Maven user, add this dependency in your pom.xml
```xml
<dependency>
  <groupId>com.tollparking.api</groupId>
  <artifactId>tollparkinglib</artifactId>
  <version>1.0</version>
</dependency>
```

For Gradle users :
Add this dependency to your project's build file:
```groovy
implementation "com.tollparking.ap:tollparkinglib:1.0"
```
## Documentation

## Usage 

A usage sample, check out the test 'tollParkingTest()' in TollParkingTest.java
The parking is initialized with default values with 10 cars come and go.

The parking entries equals 5 means that I create 5 threads for this test.

Run this end to end test using maven by this command :
```
mvn -Dtest=TollParkingTest#tollParkingTest test
```

## Features

The parking is initialized by the constructor of class Parking. 

Default values are : 
- Sedan car : 2 parking slots
- Electric car with 20kw : 2 parking slots
- Electric car with 50Kw : 2 parking slots
- Pricing policy = nb hours * hour price (0.5 dollars)

Call this constructor to initialize the parking :
```java
public Parking(int nbSedanCar, int nbElectricCar_20kw, int nbElectricCar_50kw, PricingPolicy pricingPolicy)
```

To add new pricing policy, you can implement the interface
```java
PricingPolicy.class
```

Call this method to check in one car in the toll parking : 
```java
public Boolean carCheckIn(Parking parking, Car car)
```

Call this method to check out the car from the toll parking :
```java 
public Boolean carCheckOut(Parking parking, Car car)
```
## Build the project
```shell script
# Compile and package the project
$ mvn clean install

# Run the tests
$ mvn test
```