FROM maven:3.8.4-openjdk-17

RUN mkdir /server
WORKDIR /server

# Copy over the app from the host filesystem
COPY . /server

# Start the main process.
CMD ["mvn spring-boot:run"]
