## Here are the steps to prepare the data 
Given cryptokitty transaction as the example
1. for the rpc call use "trace_transaction" as the method
2. get the colorful txt result of the trace using foundry tool
3. use the uploaded "substitute_types.py" to substitute the function call method with the actual function names
4. attach_money_flows.py is attaching the money flow result generate by money_flow_analyzer.py
5. to_trace_event.py is converting the result to the trace event format
6. to_trace_event_with_timestamps.py is inserting the timestamps
   
# ContractViz Plus - Build and Run Instructions

This project allows you to build and run ContractViz Plus, a smart contract analysis environment.

## Prerequisites

Before you begin, make sure you have the following installed on your system:

* Java JDK 21
* Maven 3.9+
* Git
* A Linux system(x86\_64)
* GTK libraries required for SWT applications

## Build

To build the project without running unit tests, use the following command:

```bash
mvn clean install -Dmaven.test.skip=true -DskipTests
```

This command:

* Cleans previous build files
* Compiles the project
* Skips tests to speed up the process

## Run

After a successful build, you can run Trace Compass using the following command:

```bash
./rcp/org.eclipse.tracecompass.rcp.product/target/products/org.eclipse.tracecompass.rcp/linux/gtk/x86_64/trace-compass/tracecompass
```

 🔧 Once Trace Compass is launched, make sure to enable the **Trace Event Parser** plugin by going to **Tools > Add-ons** and checking **Trace Event Parser**.

### Notes:

* Ensure the file is executable. If not, you can make it executable with:

  ```bash
  chmod +x ./rcp/org.eclipse.tracecompass.rcp.product/target/products/org.eclipse.tracecompass.rcp/linux/gtk/x86_64/trace-compass/tracecompass
  ```

* The path may vary slightly depending on the project version or Maven configuration.

## Troubleshooting

* If the build fails, check that all dependencies are properly resolved (common issues: `zest`, `jdt.annotation`, etc.).
* To force Maven to update dependencies:

  ```bash
  mvn clean install -U -Dmaven.test.skip=true -DskipTests
  ```

## License

This project is licensed under the [Eclipse Public License v2.0](https://www.eclipse.org/legal/epl-2.0/).
