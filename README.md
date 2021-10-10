# Notes

The best backend for keeping notes

### Requirements for the application to run <br>

- Postgres <br>
- Kafka <br>

The application is written in monorepo architecture so you can open the root **pom** and you can see all the
microservices as single module per microservice <br>

The application is not completed yet <br>

### Steps to do next

- Add support for **Parquet** file formats so the job will write parquet file
- Right now good testing is written only in **Notes** ms. Complete tests for all MS-es
- Integrate swagger
- Polish code a bit
- Create docker compose
- Create CI/CD flow
- Deploy MS to AWS <br>
    - Use S3 with file MS <br>
    - Use Lambdas instead of job

### API documentation

In the future there will be swagger link but for now there is postman collection attached in the root of project