# Jenkins Survival Guide for SDET Students
*Run your Java + Cucumber + ExtentReports test suite like a pro — even if you’ve never touched CI/CD before.*

---

## Table of Contents
1. [What & Why](#what--why)
2. [High-level Jenkins Anatomy](#high-level-jenkins-anatomy)
3. [Accessing the Class Jenkins Server](#accessing-the-class-jenkins-server)
4. [Creating or Cloning a Job](#creating-or-cloning-a-job)
5. [The Jenkinsfile — Hands-On](#the-jenkinsfile--hands-on)
6. [Connecting to GitHub](#connecting-to-github)
7. [Running the Cucumber Tests](#running-the-cucumber-tests)
8. [Interpreting Build Results](#interpreting-build-results)
9. [Common Pitfalls & How to Avoid Them](#common-pitfalls--how-to-avoid-them)
10. [When to Involve DevOps](#when-to-involve-devops)
11. [Best-Practices Checklist](#best-practices-checklist)

---

## What & Why
### What is **CI/CD**?
* **Continuous Integration (CI)** – every code change is merged, built, and tested automatically.
* **Continuous Delivery (CD)** – the build is automatically packaged and ready to deploy at the click of a button.
* **Continuous Deployment** – the build is pushed straight to production without human approval.

> **Tip:** CI catches bugs early; CD gets features to users faster. Together they form the backbone of modern software delivery.

### Where Jenkins Fits
Jenkins is an **automation server** that orchestrates your CI/CD pipeline.  
Think of it as a programmable robot that:

1. Pulls code from GitHub
2. Compiles and tests it
3. Publishes reports and artifacts
4. Optionally deploys to downstream environments

---

## High-level Jenkins Anatomy
| Piece | What it does | Why you care |
|-------|--------------|--------------|
| **Controller** | The main Jenkins instance & UI | Where you create jobs and view results |
| **Agent** | A machine that actually runs builds | Provides CPU/RAM + tools (JDK, Maven, browsers) |
| **Job / Project** | A single automation task | Runs your tests or deploys code |
| **Plugin** | Adds new features (e.g., GitHub, HTML reports) | Powers almost everything in Jenkins |
| **Workspace** | Checkout folder on an agent | Contains your source code & build output |
| **Credential** | Securely-stored secret (PAT, SSH key) | Keeps passwords out of scripts |

> **Gotcha:** Plugins can break after Jenkins upgrades. Ask DevOps before installing new ones.

---

## Accessing the Class Jenkins Server
### 1. Log In
1. Open `http://<jenkins-url>:8080/`.
2. Username/password provided by your instructor.
3. First login → **People ▸ your-user ▸ Configure** to change password.

### 2. Find Your Workspace
* Freestyle & Pipeline jobs: `Build ▸ Workspace`.
* Multibranch:  
  `OrangeHRMB22 ▸ Branch ▸ Build #n ▸ Workspace`.

### 3. Permissions
| Action | Student | Instructor/DevOps |
|--------|---------|-------------------|
| View jobs & logs | ✅ | ✅ |
| Create/edit own jobs | ✅ | ✅ |
| Install plugins, manage agents | ❌ | ✅ |
| Delete other users’ jobs | ❌ | ✅ |

> **Warning:** Deleting a shared job deletes **everyone’s** history—double-check before you click!

---

## Creating or Cloning a Job
### Job Types
| Type | When to choose it |
|------|------------------|
| **Freestyle** | Quick experiments, GUI-driven, easy to copy |
| **Pipeline (Jenkinsfile)** | Repeatable, version-controlled, recommended |
| **Multibranch Pipeline** | One job per Git branch, perfect for team forks |

> **Tip:** Start with **Multibranch** if your repo already has a `Jenkinsfile`. Otherwise create a basic Freestyle job, verify the command, then upgrade to Pipeline.

### New Agent vs. Shared Agent
Ask DevOps for a **new agent** when:
* Your tests need a browser/version not installed.
* You require admin-level packages (e.g., Docker-in-Docker).
* The shared agent’s queue is a bottleneck.

Otherwise, reuse the shared Linux agent to save time.

---

## The Jenkinsfile — Hands-On
### Declarative Syntax Primer
```groovy
pipeline {
  agent any            // where to run
  stages {
    stage('Build')   { steps { sh 'mvn -B clean test' } }
    stage('Report')  { steps { publishHTML(... ) } }
  }
}
```

# Maven Build Lifecycle

- A **lifecycle** in Maven is fixed sequence of build **phases**.
- When you invoke `mvn <phase>`, maven executes **that phase and every phase before
it**. 

* `mvn package`
  * validate -> compile -> test -> package <- stops here

* **2. The Three Standard Lifecycles**
---
  | Lifecycle | Why you’d call it | Key phases (in order) |
  |-----------|------------------|-----------------------|
  | **default** (build) | Compile, test, package, etc. | `validate` → `compile` → `test` → `package` → `verify` → `install` → `deploy` |
  | **clean** | Wipe generated files | `pre-clean` → `clean` → `post-clean` |
  | **site** | Generate project docs | `pre-site` → `site` → `post-site` → `site-deploy` |
---

* **3. Default Phases & What They Do**
---
| Phase | What happens (plugins invoked) | Why you care |
|-------|--------------------------------|--------------|
| `validate` | Checks `pom.xml` ⇢ project structure | Early fail if the project is mis-configured |
| `compile` | **maven-compiler-plugin** runs `javac` | Source code compiled to `target/classes` |
| `test` | **maven-surefire-plugin** runs unit tests | Creates `TEST-*.xml` + Surefire reports |
| `package` | **maven-jar/war-plugin** zips artifacts | `target/your-app.jar` or `.war` produced |
| `verify` | Optional checks (e.g., **failsafe**) | Integration tests, code-quality gates |
| `install` | Copies artifact to local `~/.m2` repo | Lets other local projects `dependency:copy` |
| `deploy` | Publishes to remote artifact repo | CI/CD pushes to Nexus, Artifactory, etc. |
---

## 4  How Maven **Finds & Runs** Your Tests

### The Surefire Plugin (unit tests)
*Auto-bound* to the **`test`** phase.  
By default it runs every Java class that matches:
* **Test.java *Tests.java *TestCase.java or.**
We could also change the runner class name as we want in the maven sure fire 
plugin section.
```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.2.5</version>
  <configuration>
    <includes>
      <include>**/*Runner.java</include>   <!-- Cucumber runners -->
    </includes>
  </configuration>
</plugin>
```
# Common Maven Commands 
| Command                     | What It Runs                   | Typical Use                                   |
|-----------------------------|--------------------------------|-----------------------------------------------|
| `mvn clean`                 | **clean** lifecycle only       | Delete `target/` before a fresh build         |
| `mvn test`                  | Up to **test** phase           | Fast feedback, skips packaging                |
| `mvn package`               | Up to **package** phase        | Produce JAR/WAR for CI pipelines              |
| `mvn verify`                | Up to **verify** phase         | Run integration tests (Failsafe)              |
| `mvn install`               | Up to **install** phase        | Add artifact to local `~/.m2` repository      |
| `mvn deploy`                | Full build + upload            | CI server pushes artifact to remote repo      |
| `mvn clean verify`          | `clean` then full test suite   | Typical Jenkins command for complete testing  |
| `mvn -DskipTests package`   | Build but **skip tests**       | Hot-fix builds (avoid in CI for main branch)  |


# Jenkins file
- It is written in groovy language and tells Jenkins how to build, test, and 
publish your project.
- Benefits:
  - Your pipeline is also version controlled `Jenkinsfile` is included in your
  project repository.
  - One push = code + build recipe in a single commit.
  - No more clicking around the Jenkins UI to update steps.
  - Easy to review and rollback.
---
## 2  Prerequisites (one-time DevOps setup)

| Tool / Config | Who sets it up | Where |
|---------------|---------------|-------|
| JDK 11/17/21           | DevOps | **Manage Jenkins ▸ Global Tool Config** |
| Maven 3.9              | DevOps | same screen |
| Chrome + ChromeDriver  | DevOps | installed on the Linux agent |
| GitHub PAT `github-pat`| DevOps/You | **Credentials ▸ Global** (kind = Secret text) |
| Multibranch job        | DevOps/You | **New Item ▸ Multibranch Pipeline** |
---








