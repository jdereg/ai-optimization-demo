# AI-Assisted Iterative Optimization Loops

A live demo project showing how AI coding agents (Claude Code) can run iterative optimization loops against a real codebase — measuring results, making changes, and looping until a goal is met.

**[View the Presentation](https://jdereg.github.io/ai-optimization-demo/presentation.html)**

## What's Inside

A deliberately inefficient Java sales data pipeline (50,000 records) with 10 planted performance problems and minimal test coverage (~10%). Two demos run against this same codebase:

### Demo 1: Code Coverage Loop
Feed Claude Code a single prompt and watch it measure JaCoCo coverage, identify gaps, write JUnit 5 tests, re-measure, and loop until line coverage reaches 92%+.

### Demo 2: Performance Optimization Loop
Feed Claude Code a single prompt and watch it benchmark the pipeline, analyze the timing breakdown, apply the highest-impact optimization, commit if it helps (>=2% improvement), and loop until 3 consecutive failures signal diminishing returns. Starting time ~6.5s, expected final time <1s.

## The Pattern

Both demos follow the same universal pattern:

```
measurable_goal + tool_access + termination_condition = AI optimization loop
```

## Running the Demos

```bash
cd java-app

# Build and verify
mvn clean test jacoco:report

# Run the benchmark
mvn -q exec:java -Dexec.mainClass="com.demo.optimizer.App"

# Reset to starting state after a demo run
git checkout main
git reset --hard demo-start
```

## Planted Inefficiencies

| # | Class | Problem | Expected Fix |
|---|-------|---------|-------------|
| 1 | Sorter | Bubble sort O(n^2) | Collections.sort() |
| 2 | App/DataLoader | Re-reads file per region (6x) | Load once, filter in memory |
| 3 | DataCleaner | Pattern.compile() in loop | Static precompiled Pattern |
| 4 | DataCleaner | String += char-by-char | StringBuilder |
| 5 | DataEnricher | New DateTimeFormatter per call | Static cached instance |
| 6 | Aggregator | Linear search in ArrayList | HashMap lookup |
| 7 | StatisticsCalculator | Redundant copies + autoboxing | double[] + single sort |
| 8 | Aggregator | Synchronized collections (single-threaded) | Regular collections |
| 9 | ReportGenerator | String += in loop | StringBuilder |
| 10 | DataEnricher | Sequential per-record processing | Parallel streams |

## Built With

- Java 17, Maven, JUnit 5, JaCoCo
- [Claude Code](https://claude.ai/code) — Anthropic's AI coding agent
