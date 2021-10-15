[![Java CI with Maven](https://github.com/Zavarov/Arithmetic-Expressions/actions/workflows/maven.yml/badge.svg)](https://github.com/Zavarov/JRA/actions/workflows/maven.yml)

# Arithmetic Expressions

A project for evaluation mathematical expressions via MontiCore. It is MontiCore derivative which extends the Expression
grammar with mathematical constants and functions. 

### Getting started

The project provides consists of two classes:
- ArithmeticExpressionValueCalculator, which derives a numerical value from an arithmetic expression.
- ArithmeticExpressionPrettyPrinter, which transforms an arithmetic expression into a human-readable string.

Arithmetic expressions are created via the built-in MontiCore expressions.

### Installing

In order to install this project, simply execute the Maven command:

```
mvn clean install
```

## Runtime Dependencies

* [MontiCore Grammar 6.7.0](https://github.com/MontiCore/monticore/tree/6.7.0),
   MontiCore 3-Level License
   
## Built With

* [Maven](https://maven.apache.org/) - Dependency management and build tool.

## Authors

* **Zavarov**

## License

This project is licensed under the GPLv3 License - see the [LICENSE](LICENSE) file for details.

