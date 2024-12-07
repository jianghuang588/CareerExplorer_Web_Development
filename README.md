# Application Walkthrough Guide 📖

---

## 📂 **Instructions:**
1. **Start the H2 Database**:
   - Run the command: `java -cp h2*.jar org.h2.tools.Server -tcp -pg`
2. **Setup the Database**:
   - Compile and execute `workerDB.java` to create the database.
3. **Start the Web Server**:
   - Compile and execute `WebServer.java`.
4. **Access the Application**:
   - Open your browser and navigate to: `http://localhost:40013/login.html`
   - `login.html` is the **main page**.

---

## 🔑 **User Information:**
### Regular Users:
- **Usernames**: `michael`, `taylor`, `julia`, `nora`, `oliver`, `william`
- **Password**: `password`

### Admin User:
- **Username**: `admin`
- **Password**: `password`

---

## ⚙️ **Primary Key for Deleting Accounts:**
Use the following primary keys to delete accounts:
1. `1`: michael  
2. `2`: taylor  
3. `3`: julia  
4. `4`: nora  
5. `5`: oliver  
6. `6`: william  
7. `7`: admin  

---

## 🌟 **App Walkthrough:**
- **Navigation Flow:** Home Page → Career Page → Update User → Admin Update Account → Admin Delete Account

---

### 📝 **Screencasts (GIFs)**

<details>
<summary>🏠 **Home Page For Regular User**</summary>

![homePage](https://github.com/user-attachments/assets/0d473f4d-d2c3-46ff-a88e-450b123827e4)

> **Description**: Shows the homepage for regular users. *(GIF)*

</details>

<details>
<summary>💼 **Best Paying Career with Location Page**</summary>

![page](https://github.com/user-attachments/assets/7c591ff4-2760-45a5-ad67-c09b2f32318e)

> **Description**: Displays the best-paying careers with locations. *(GIF)*

</details>

<details>
<summary>🔄 **Update User Information Page**</summary>

![part1](https://github.com/user-attachments/assets/0ac78a36-a5c5-46c6-94c0-9a0f422953c0)

> **Description**: Enables users to update their account information. *(GIF)*

</details>

<details>
<summary>🛠️ **Admin User Update Account Page**</summary>

![update](https://github.com/user-attachments/assets/ee93a10a-0cd8-497e-9e5c-75a60960688d)

> **Description**: Admin interface to update user accounts. *(GIF)*

</details>

<details>
<summary>❌ **Admin User Delete Account Page**</summary>

![delete](https://github.com/user-attachments/assets/ca6fc766-de97-4734-a83d-11c7890f1678)

> **Description**: Admin interface to delete user accounts. *(GIF)*

</details>

---

## 📝 **Additional Tips:**
- Ensure all users have the correct credentials.
- For admins, ensure primary key deletion corresponds accurately to the table.
