# Jokes appication

## About

This project is Jokes applicaiton for Plumery

## How to run

To run project can be used one of following approaches:

### Local build

To build locally following requirements exist

* `JVM 17`
* `Maven 3`

Build and run application by executing

```shell
mvn clean spring-boot:run -Dspring.profiles.active=local
```

### Skaffold build

To build locally following requirements exist

* `minikube`
* `kubectl`
* `skaffold`

Run skaffold:

```shell
skaffold dev
```

## Code quality

Maven build process instructed by following code quality criteria:

* `jdk-unsafe`: Signatures of `unsafe` methods that use default charset, default locale, or default timezone. For server
  applications it is very dangerous to call those methods, as the results will definitely not what the user wants.
* `jdk-deprecated`: This disallows all `deprecated` methods from the JDK
* `jdk-system-out`: On server-side applications or libraries used by other programs, printing to `System.out`
  or `System.err` is discouraged and should be avoided.
* `jdk-internal`: Lists all internal packages of the JDK as of `Security.getProperty("package.access")`. Calling those
  methods will always trigger security manager and is completely forbidden from Java 9.
* `jdk-non-portable`: Signatures of all non-portable (like `com.sun.management.HotSpotDiagnosticMXBean`) or internal
  runtime APIs (like `sun.misc.Unsafe`).
* `jdk-reflection`: Reflection usage to work around access flags fails with `SecurityManagers` and likely will not work
  anymore on runtime classes in Java 9
* `commons-io-unsafe-2.5`: If your application uses the `Apache Common-IO` library (version 2.5), this adds signatures
  of all methods that depend on default charset.

## Code style

Default configuration is provided in [Editor Config file](.editorconfig). Additional rules coming from PMD:

* AvoidUsingHardCodedIP
* CheckResultSet
* PrimitiveWrapperInstantiation
* UnusedFormalParameter
* UnusedLocalVariable
* UnusedPrivateField
* UnusedPrivateMethod
* ExtendsObject
* ForLoopShouldBeWhileLoop
* TooManyStaticImports
* UnnecessaryFullyQualifiedName
* UnnecessaryImport
* UnnecessaryModifier
* UnnecessaryReturn
* UselessParentheses
* UselessQualifiedThis
* CollapsibleIfStatements
* SimplifiedTernary
* UselessOverridingMethod
* AvoidBranchingStatementAsLastInLoop
* AvoidDecimalLiteralsInBigDecimalConstructor
* AvoidMultipleUnaryOperators
* AvoidUsingOctalValues
* BrokenNullCheck
* CheckSkipResult
* ClassCastExceptionWithToArray
* DontUseFloatTypeForLoopIndices
* EmptyCatchBlock
* EmptyFinallyBlock
* EmptyIfStmt
* EmptyInitializer
* EmptyStatementBlock
* EmptyStatementNotInLoop
* EmptySwitchStatements
* EmptySynchronizedBlock
* EmptyTryBlock
* EmptyWhileStmt
* JumbledIncrementer
* MisplacedNullCheck
* OverrideBothEqualsAndHashcode
* ReturnFromFinallyBlock
* UnconditionalIfStatement
* UnnecessaryConversionTemporary
* UnusedNullCheckInEquals
* UselessOperationOnImmutable
* AvoidThreadGroup
* DontCallThreadRun
* DoubleCheckedLocking
* BigIntegerInstantiation

For detailed information visist [PMD website](https://pmd.github.io/latest/pmd_rules_java.html)

## Test coverage

Provided unit test must satisfy following criteria:

* Number of non tested classes: 0
* Minimum percentage of covered lines: 80%
* Minimum percentage of walked branches: 80%
* Non-testable classes: `*Test*`, `*Configuration*`, `Domain` objects

## Mutation coverage

Unit tests are instrumented by a mutational engine with following constraints:

* Minimum percentage of covered mutations: 80%

## Code style

For code style use [this styles](.editorconfig)
