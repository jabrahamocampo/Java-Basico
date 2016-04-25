<!--  AdivinarNumeros.jsp -->
<%! int adivinar = (int)(java.lang.Math.random()*100)+1; %>
<%! int intentos = 0;%>

<%@ page isThreadSafe= "true" %>

<%!
 /* 
 * Este metodo genera un pequeño mensaje de ayuda
 */
 
 public String ayuda(int numero) {
     if (numero > adivinar) {
         return "El numero es menor";
     }
     return "El numero es  mayor";
	}

%>
<html>
	<head>
		<title>Adivinar Numero</title>
	</head>
	<body>
	El numero a adivinar es: <%= adivinar %>
		<%
			String parametro = request.getParameter("adivinar");
			int numero;
			if(parametro != null){
				numero = Integer.parseInt(parametro);
				intentos++;
			}else{
				numero = -1;
			}
			
			if(parametro != null && adivinar == numero){%>
			<p>¡Enhorabuena! lo has adivinado despues de <%= intentos %> intentos.</p>
			<%
				adivinar = (int)(java.lang.Math.random()*100)+1;
				intentos = 0;
			
				response.sendRedirect("AdivinarNumeros.jsp");
				/*RequestDispatcher requestDispatcher;
				requestDispatcher = request.getRequestDispatcher("AdivinarNumeros.jsp");
				requestDispatcher.forward(request, response);
				*/
			%>
			¿Quieres probar otra vez <a href="jsp/tema12/AdivinarNumeros.jsp"></a>?
			<%
			}else if (intentos == 0) {%> 
				<p>Adivina el numero entre 1 y 100:</p>
			<%} else { %>
			<p>No es ese. <%= ayuda(numero) %>.</p>
			   <p>Has hecho un total de <%= intentos%> intentos.</p>
        	<% }%>

        <form method=get>
            ¿Cual es el numero? <input type=text name="adivinar">
            <input type=submit value="Submit">
        </form>
	</body>
</html>