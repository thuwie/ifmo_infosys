FROM maven:3.6.0-jdk-8
COPY . /user/src/api
WORKDIR /user/src/api
RUN mvn package

CMD ["cat"]
