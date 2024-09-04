
# Getting Started




# Troubleshoot/Frequent issues

Issue:
Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.13.0:testCompile (default-testCompile) on project user-tracking-producer: Fatal error compiling: error: release version 22 not supported
Solution:
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