<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<title>Enter Word</title>

</head>
<body>
	<h1>Suggest words</h1>
	<form method="POST" action="suggestWord">
		<table>
			<tbody>
				<tr>
					<td>Enter Word:</td>
					<td><input type="text" name="word" ></input></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" value="Suggest Word"></td>
				</tr>
			</tbody>
		</table>
	</form>
</body>
</html>
