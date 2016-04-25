<%! int ultimo=0;%>

<%!
public String hora(){
	return (new java.util.Date()).toString();	
}
%>

<html>
	<head>
		<title>Simple KSP</title>
	</head>
	<body>
		<h1>Una tabla chida:</h1>
		<table border="1">
			<%
				for(int i=ultimo; i < ultimo + 10; i++){ %>
					<tr>
						<td>Numero</td>
						<td><%= i %></td>
					</tr>
				<% }
				ultimo += 10;
				%>
			
		</table>
		<p>La hota actual es:<%= hora() %></p>
	</body>
</html>