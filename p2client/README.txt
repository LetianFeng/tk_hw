== Build ==
build.xml is provided in the root folder, use ant to build the project.

== Execution ==
Executables are built into dist folder.
Running ant build will automatically start SOAP client and REST client.

== Running Server ==
1 command line argument (named 'host' which is the address that client will be connected to) is needed for the ant build.
Example, ant -Dhost='192.168.0.100'

When no command line argument, client will connect to localhost by default.
