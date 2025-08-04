# 🧩 How to Create a New View Based on ETC (ContractViz+ Model)

This guide explains how to create a **new view** in Eclipse or Theia Trace Viewer using the existing **ContractViz+ model** (`se.kth.contractvizplus`), either by creating a new plugin or reusing an existing one.

---

## 🔨 Steps to Create a New View

### 1. Create a Plugin (or Use an Existing One)

You can either:

- Create a **new Eclipse plugin** (e.g., `org.example.mynewview`)
- Or **add the view** inside an existing plugin (e.g., `org.example.statediagram`)

> ✅ If the view is conceptually independent, it's better to use a separate plugin for better modularity.

---

### 2. Create the Java Class for the View

Create a new class that extends `TmfViewPart` (if using Eclipse RCP) or use a custom Theia-compatible structure if you're targeting a web view.

#### Example (Eclipse RCP View):

```java
public class MyNewEtcView extends TmfViewPart {

    public static final String VIEW_ID = "org.example.mynewview.views.mynewetcview";

    public MyNewEtcView() {
        super("My ETC View");
    }

    @Override
    public void createPartControl(Composite parent) {
        // Initialize your UI components here
    }

    @Override
    public void setFocus() {
        // Optional: behavior when the view gains focus
    }
}

---

### 3. Register the View in plugin.xml

Add the new view to your plugin's `plugin.xml`:

```xml
<extension
     point="org.eclipse.ui.views">
  <view
        id="org.example.mynewview.views.mynewetcview"
        name="My ETC View"
        class="org.example.mynewview.MyNewEtcView"
        category="org.eclipse.tracecompass.views.category">
  </view>
</extension>
```

⚠️ **Important:** Ensure the `id` matches the `VIEW_ID` constant in your Java class.

### 4. Use the Existing ETC Model

Leverage the analysis logic from the `se.kth.contractvizplus` plugin (model only, no UI dependency):

```java
ContractvizPlusAnalysis analysis = new ContractvizPlusAnalysis();
analysis.execute(trace); // Run the analysis on a trace

List<EtcResult> results = analysis.getResults(); // Use results for your view
```

You can display these results in any form: a chart, table, graph, or custom layout.

### 5. Run and Test

1. **For Eclipse RCP:**
   - Launch the application via "Launch Eclipse Application"
   - Open your view via `Window > Show View > Other...`
   - Search for "My ETC View"

2. **For Theia:**
   - Ensure the view is registered and declared in the frontend module
   - Build and test in the Theia environment

## ✅ Development Summary

| Step | Description |
|------|-------------|
| **Create a plugin** | Either a new one or reuse an existing plugin |
| **Create the view class** | Extend `TmfViewPart`, define a unique `VIEW_ID` |
| **Modify plugin.xml** | Register the view using the same id |
| **Use ETC model** | Import `se.kth.contractvizplus`, no UI dependency |
| **Run & test** | Open the view in Eclipse or Theia |

### Best Practices

- **Separation of concerns:** Use only the model/analysis logic, avoid UI dependencies
- **Unique identifiers:** Ensure VIEW_ID is unique across all plugins
- **Error handling:** Implement proper error handling for trace analysis
- **Performance:** Consider caching analysis results for large traces

---

## Contact

For questions about this setup, please refer to:
- GitHub Issues in your fork
- Theia Trace Extension documentation
- Eclipse Trace Compass community

---