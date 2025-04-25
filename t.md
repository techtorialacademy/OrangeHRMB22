# 📌 Guidelines for Testing Admin Page Functionality in OrangeHRM

## 🔹 1. Test Plan & Structure
When automating the **Admin User Management Page** of **OrangeHRM**, we focus on key functionalities:

- **Add a new user**
- **Edit an existing user**
- **Delete a user**
- **Search for users**
- **Enable/Disable user accounts**

Each functionality should be tested with **valid and invalid inputs**.

---

## 🔹 2. Feature File Structure (Cucumber)
Your feature file should follow a **logical structure**:

### **🔹 Background Section**
- **Purpose**: Set up the test preconditions, like logging in as an admin.
- **Example**:
  ```gherkin
  Background:
    Given I am logged in as an admin
    And I navigate to the Admin User Management page
  ```

---

### **🔹 Scenario 1: Adding a New User**
- **Steps to Cover**:
    - Click the **Add** button.
    - Fill in the required user details.
    - Click **Save** and verify the user is added.
- **Validations**:
    - Success message is displayed.
    - User appears in the list.
    - Username should be unique.

---

### **🔹 Scenario 2: Editing an Existing User**
- **Steps to Cover**:
    - Search for an existing user.
    - Click **Edit** and modify details (e.g., change status).
    - Click **Save** and verify changes.
- **Validations**:
    - Success message is displayed.
    - Updated details are correctly saved.
    - Ensure validation messages appear if fields are incorrect.

---

### **🔹 Scenario 3: Deleting a User**
- **Steps to Cover**:
    - Search for a user.
    - Select the user checkbox.
    - Click **Delete**, confirm the deletion.
    - Verify the user is removed from the list.
- **Validations**:
    - Success message is displayed.
    - The user no longer appears in the list.

---

## 🔹 3. Sample Feature File

### **Feature: Admin Page - User Management**
📍 **Location:** `src/test/resources/features/admin.feature`

```gherkin
Feature: Admin Page - User Management

  Background:
    Given I am logged in as an admin
    And I navigate to the Admin User Management page

  Scenario: Add a new system user
    When I click on the "Add" button
    And I enter user details
    And I click the "Save" button
    Then I should see a success message
    And the new user should be listed
```
