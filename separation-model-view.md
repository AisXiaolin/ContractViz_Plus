# 🔄 View / Model Separation: ContractViz+ & StateDiagram (Theia Trace Viewer)

## 🔧 Initial Problem

Originally, the ContractViz visualization plugin combined both:

- **Model**: trace data processing and analysis logic  
- **View**: UI components built using Theia Trace Viewer

This design caused several issues:

- Impossible to reuse **just the model** without importing the entire UI plugin  
- Unnecessary bloat on headless servers (analysis-only environments)  
- Poor separation of concerns, leading to a non-modular architecture

---

## ✂️ Solution: Split into Two Plugins

The solution was to **cleanly separate the analysis logic and the visualization** into two independent plugins:

| Plugin                     | Role                              | Main Dependencies                      |
|----------------------------|-----------------------------------|----------------------------------------|
| `se.kth.contractvizplus`   | Contains the **model** and analysis logic | `tracecompass.analysis.*` only     |
| `org.example.statediagram` | Contains the **TTV view**         | `theia-trace-extension`, React, etc.   |

---

## ✅ Result

- The `se.kth.contractvizplus` plugin can now be used **independently** from the view  
- It can be integrated into **other visualizations**, such as `StateDiagram`  
- The modular architecture prevents UI code from bloating server environments

---

## 🔁 Reuse Example

```ts
import { ContractvizPlusAnalysis } from 'se.kth.contractvizplus';

const results = await ContractvizPlusAnalysis.run(trace);
renderDiagram(results);

---

Voici la traduction complète en Markdown :

```markdown
## 🔍 Alignment with Trace Compass Standards

This architecture follows the best practices of the Trace Compass project, which consistently separates **business logic** (core) from **UI presentation** (ui).

### Examples of architecture in Trace Compass:

- `tmf.core` / `tmf.ui`  
  > Trace Model Framework: analysis core + user interface

- `ctf.core` / `ctf.ui`  
  > Common Trace Format: trace reading + visualization

- `stateanalysis.core` / `stateanalysis.ui`  
  > State extraction from events + display as state diagrams

---

Similarly, our project separates:

- `se.kth.contractvizplus` → **core** module  
- `org.example.statediagram` → **ui** module

This promotes reusability, testability, and better maintainability of the code.
```
