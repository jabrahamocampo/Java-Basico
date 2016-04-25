<jsp:useBean id="firmante" class="com.tema13.Usuario" scope="session" />
<jsp:useBean id="libro" class="com.tema13.LibroDeFirmas" scope="application"></jsp:useBean>
<html>
	<head>
	</head>
	<body>
		<%libro.anhadirFirmante(firmante);%>
		<jsp:forward page="/jsp/tema13/Libro.jsp" />
	</body>
</html>