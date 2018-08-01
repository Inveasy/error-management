# Error Management
[![Build Status](https://travis-ci.org/Inveasy/error-management.svg?branch=master)](https://travis-ci.org/Inveasy/error-management) [![Quality gate status](https://sonarcloud.io/api/project_badges/measure?project=io.inveasy%3Aerror-management&metric=alert_status)](https://sonarcloud.io/dashboard?id=io.inveasy%3Aerror-management) [ ![Download](https://api.bintray.com/packages/inveasy/maven/error-management/images/download.svg) ](https://bintray.com/inveasy/maven/error-management/_latestVersion)

## What is it ?
This project provides annotations and a class to runtime generate (build time generation is planned) hexadecimal error codes, composed of prefixes and suffixes.

Generated codes will be of the form "[project prefix][class prefix][method prefix(optional)][suffix]"

## How To
First, include the maven dependency in your build :

```xml
<dependency>
  <groupId>io.inveasy</groupId>
  <artifactId>error-management</artifactId>
  <version>1.0.0</version>
</dependency>

<repositories>
  <repository>
    <id>bintray-inveasy-maven</id>
    <name>inveasy-maven</name>
    <url>https://dl.bintray.com/inveasy/maven</url>
  </repository>
</repositories>
```

Then, on your main class, add the project annotation with the prefix of your current project :
```java
@ProjectErrorPrefix("00") // Set here the prefix of this project
public class Application
{
	public static void main(String[] args)
	{
		// ...
	}
}
```

For each class you will be generating error codes, add the class annotation :
```java
@ClassErrorPrefix("00") // Set here the prefix of this class
public class SomeClass
{
	// ...
}
```

Optionally, you can add a method prefix by adding the method annotation to methods containing error code generation :
```java
@ClassErrorPrefix("00") // Set here the prefix of this class
public class SomeClass
{
	@MethodErrorPrefix("00") // Set here the prefix of this method
	public void someMethod()
	{
		// ...
	}
}
```

Finally, request a new code where you need it :
```java
@ClassErrorPrefix("00") // Set here the prefix of this class
public class SomeClass
{
	@MethodErrorPrefix("00") // Set here the prefix of this method
	public void someMethod()
	{
		String errorCode = ErrorManager.code("00"); // Set here the suffix of the code
	}
}
```

Generated code will, in this example, be "00000000".