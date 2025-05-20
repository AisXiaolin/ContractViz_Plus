## Here are the steps to prepare the data 
Given cryptokitty transaction as the example
1. for the rpc call use "trace_transaction" as the method
2. get the colorful txt result of the trace using foundry tool
3. use the uploaded "substitute_types.py" to substitute the function call method with the actual function names
4. attach_money_flows.py is attaching the money flow result generate by money_flow_analyzer.py
5. to_trace_event.py is converting the result to the trace event format
6. to_trace_event_with_timestamps.py is inserting the timestamps
   
