# Image Service

Image Service is a service designed to handle image-related operations, such as retrieving, optimizing, and managing images from various sources.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Testing](#testing)

## Overview

The Image Service is built to simplify image-related tasks in your application. It provides functionalities like image retrieval, optimization, and storage management.

## Features

- **Image Retrieval:** Retrieve images based on predefined types and references.
- **Image Optimization:** Optimize images based on predefined configurations.
- **Image Storage Management:** Save, flush, and manage images in the storage system.
- **Image Download:** Download images from external sources and handle source image not found scenarios.

## Prerequisites

Ensure you have the following prerequisites installed before using the Image Service:

- Java 21
- Maven
- Spring Boot 3

## Installation

Build and run the project using Maven:

```bash
cd image-service
mvn clean install
java -jar target/image-service-0.0.1-SNAPSHOT.jar
```

## Testing

Run tests to ensure the correctness of the Image Service. Use Maven for running tests:

```bash
mvn test
```
There are some mocked images under resources folder.
Existing predefined image types now are as follows:
- thumbnail
- large
To test the application on your localhost, you can use the following requests:
- http://localhost:8080/image/show/large/dept-blazer?reference=abcdefghijk.jpg
- http://localhost:8080/image/show/thumbnail/bag?reference=abc.jpg
- http://localhost:8080/image/show/thumbnail/shop?reference=abcdefghi.jpg