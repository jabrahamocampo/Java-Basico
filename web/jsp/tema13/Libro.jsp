<%@page import="com.tema13.*"%>
<%@page import="java.util.List" %>

<jsp:useBean id="firmante" class="com.tema13.Usuario" scope="session"></jsp:useBean>
<jsp:useBean id="libro" class="com.tema13.LibroDeFirmas" scope="application"></jsp:useBean>

<html>
<head>
</head>
<body>
	<%
		if(firmante.getNombre() != null){ %>
			<h1>Hola<jsp:getProperty name="firmante" property="nombre" />!</h1>
	<%  }else{ %>
			<h1>Hola Wey!</h1>
	<%	} %>
	
	<img style="width: 358px;" alt="a" src="gatitosalvado.jpg">
	
	<p>Esta es toda la gente que ha salvado al gatito: </p>
	
	<ul>
		<% for(Usuario firma : libro.getFirmantes()){
			out.print("<li>"+firma.getNombre()+"("+firma.getEmail()+ ")"+firma.getEdad()+"</li>");
			}
			session.invalidate();
		%>	
	</ul>
</body>
</html>
			
			