<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Students List</title>
    <style>
    
    *{
margin:0;
padding:0;
box-shadow: 0;
}
.main-container {
    
    margin: auto;
    border: 1px solid black;
    display: flex;
    flex-direction: column;
    min-height: 100vh; 
}


.header {
    background-color: #f0f0f0;
    display: flex;
    justify-content: end;
    padding: 5px 10px;
    align-items: center;
}

.user-info {
    padding: 2px 30px;
    font-size: 17px;
    color: initial;
}

.image {
    width: 60px;
    height: 60px;
    border-radius: 30px;
    background-color: #919499;
}

.image img {
    max-width: 100%;
    border-radius: 30px;
}

.container {
    flex-grow: 1; 
    background-color: #b0afac;
    text-align: center;
    color: white;
    padding: 10px 10px;
}
.table-header{

text-align: center;
}

.table-container {
            overflow-x: auto;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin: auto;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: center; 
        }
        th {
            background-color: #007bff;
            color: #fff;
        }
     	   tr:nth-child(even) {
            background-color: #f2f2f2;
        } 
        .back-button {
            margin-top: 20px;
            display: flex;
            justify-content: center; 
        }
        button {
            padding: 10px 20px;
            border: none;
            background-color: #007bff;
            color: #fff;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
         button:hover {
            background-color: #0056b3;
        } 
        
        input {
          background: none;
    border: none;
    font-family: math;
    font-size: 15;
    width: 60px;
        }
        
    footer {
        width: 100%;
        text-align: center;
        background-color: #d6d5d2;
        padding: 10px 0;
        border-top: 1px solid #ccc;
        position: fixed;
        bottom: 0;
    }
</style>
</head>
<body>
    <div class="main-container">
        <div class="container">
            <div class="header">
                <div class="user-info">
                    <p>Username: ${username}</p>
                    <p>Role: ${role}</p>
                </div>
                <div class="image">
                    <a href="ProfileServlet?username=${username}"><img src="Profile.png" alt="Profile Image"></a>
                </div>
            </div>
            <div class="content">
              <h2>Marks</h2>
                <table>
                    <tr>
                        <th>Roll Number</th>
                        <th>Name</th>
                        <th>English</th>
                        <th>CS</th>
                        <th>Maths</th>
                        <th>Physics</th>
                        <th>Chemistry</th>
                        <th>Total</th>
                    </tr>
                    <tr>
                        <td>${studentList[0].rollNumber}</td>
                        <td>${studentList[0].name}</td>
                        <td>${studentList[0].english}</td>
                        <td>${studentList[0].cs}</td>
                        <td>${studentList[0].maths}</td>
                        <td>${studentList[0].physics}</td>
                        <td>${studentList[0].chemistry}</td>
                        <td>${studentList[0].english + studentList[0].cs + studentList[0].maths + studentList[0].physics + studentList[0].chemistry}</td>
                    </tr>
                </table>
            </div>
    </div>
    <footer>
            <p>© 2024 Admin Dashboard. All rights reserved.</p>
        </footer>
    </div>
</body>
 <script>
        function confirmDelete() {
            return confirm('Are you sure you want to delete this student?');
        }
    </script>
</html>