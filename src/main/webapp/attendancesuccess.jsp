<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Attendance Form</title>
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
    width: 35px;
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
<div class="container">
            <h2>Attendance Form</h2>
            <form action="AttendanceServlet" method="post">
                <table>
                    <tr>
                        <th>Student ID</th>
                        <th>Period 1</th>
                        <th>Period 2</th>
                        <th>Period 3</th>
                        <th>Period 4</th>
                        <th>Period 5</th>
                        <th>Attendance Date</th>
                    </tr>
                    <c:forEach var="attendanceRecord" items="${attendanceRecords}">
    <tr>
        <td><input type="text" name="studentId" value="${attendanceRecord.studentId}"/></td>
        <td><input type="text" name="period1" value="${attendanceRecord.period1}"/></td>
        <td><input type="text" name="period2" value="${attendanceRecord.period2}"/></td>
        <td><input type="text" name="period3" value="${attendanceRecord.period3}"/></td>
        <td><input type="text" name="period4" value="${attendanceRecord.period4}"/></td>
        <td><input type="text" name="period5" value="${attendanceRecord.period5}"/></td>
        <td><input type="date" name="attendanceDate" value="${attendanceRecord.attendanceDate}" required/></td>
    </tr>
</c:forEach>
                </table>
                <div class="back-button">
                    <button type="submit">Save</button>
                </div>
            </form>
        </div>
    </div>
</div>
<footer>
		<p>© 2024 Admin Dashboard. All rights reserved.</p>
	</footer>
</body>
</html>
