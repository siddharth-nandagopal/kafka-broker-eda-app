
# Getting Started

1. Make sure to start [kafka cluster](https://github.com/siddharth-nandagopal/kafka-kraft-cluster)
2. Start [user-tracking-producer](https://github.com/siddharth-nandagopal/kafka-broker-eda-app/tree/development/user-tracking-producer)
3. Make sure to start [user-tracking-consumer](https://github.com/siddharth-nandagopal/kafka-broker-eda-app/tree/development/user-tracking-consumer)

## Setup Local Registry:
```
$ docker run -d -p 5000:5000 --restart=always --name registry registry:2
```

## How to start
```
./mvnw spring-boot:run
```


# Troubleshoot guide/Frequent issues

## Issue:
```
Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.13.0:testCompile (default-testCompile) on project user-tracking-producer: Fatal error compiling: error: release version 22 not supported
```
### Solution:
```
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<configuration>
				<release>22</release>
				<source>22</source>
				<target>22</target>
			</configuration>
		</plugin>
	</plugins>
</build>
```