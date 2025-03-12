# Hudi and the Blowfish

Hudi and the Blowfish is a custom Apache Hudi extension that integrates Blowfish encryption into your Hudi data pipeline.
It encrypts a specified field (e.g., "sensitiveField") before writing records to S3.

## Features

- Custom Payload Extension: Encrypts a specified field in Hudi records using Blowfish encryption.
- Easy Integration: Can be included in your Hudi ingestion pipeline via Spark's --jars option.

## Getting Started

Prerequisites:
- Java 17 or higher
- Maven

Build:

Clone the repository and build the project using Maven:

  git clone https://github.com/yourusername/hudi-and-the-blowfish.git
  cd hudi-and-the-blowfish
  mvn clean package

This will produce a jar file (e.g., hudi-blowfish-0.1.0.jar) in the target directory.

Usage:

Include the generated jar in your Hudi ingestion pipeline. For example, using Spark:

  spark-submit --jars /path/to/hudi-blowfish-0.1.0.jar \
    --class your.main.Class your-spark-job.jar

Configure your Hudi job to use the custom payload class by setting the following property:

  hoodie.datasource.write.payload.class=com.wallentx.hudi.blowfish.YourCustomPayloadClass

## Customization

- Encryption Key: The encryption key is hardcoded for demonstration purposes. For production, integrate with a secure key management solution.
- Field Encryption: Currently, only the "sensitiveField" is encrypted. You can modify the payload logic to encrypt additional fields or apply different encryption strategies as needed.

## License

This project is licensed under the MIT License.
