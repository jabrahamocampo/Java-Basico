package com.tema10;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="JDBCServlet", urlPatterns={"/JDBCServlet"})

public class JDBCServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//JDBC Driver name and DB URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/emp";
	
	//Database credentials
	static final String USER = "root";
	static final String PASS = "";
	
    private Statement statment = null;
    private Connection conexion = null;
	
	@Override
	public void init(ServletConfig config){
		try{
			//STEP 2: REGISTER JDBC DRIVER
			Class.forName("com.mysql.jdbc.Driver");
			
			//STEP 3:OPEN A CONNECTION
			System.out.println("Connecting to Database");
			conexion = DriverManager.getConnection(DB_URL,USER,PASS);
			
			//STEP 4: EXECUTE A QUERY
			System.out.println("Creating statement...");
			statment = conexion.createStatement();
		}catch(ClassNotFoundException ex){
			Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE,"No se pudo cargar el driver de la base de datos", ex);
		}catch(SQLException ex){
			Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE,"No se pudo obtener la conexion a la base de datos", ex);
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		try{
			out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Listar Personas</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista de las personas:</h1>");
            out.println("<ul>");
            String sql = null;
            ResultSet resultSet = null;
            sql = "Select * from Employees";
            try{
            	synchronized(statment){
            		resultSet = statment.executeQuery(sql);
            	}
            	while(resultSet.next()){
            		out.println("<li>"+resultSet.getString("first")
            				+ " edad:" + resultSet.getInt("age")+"</li>");
            	}
            }catch(SQLException ex){
            	gestionarErrorEnConsultaSQL(ex, request, response);
            }
            finally{
            	if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE,
                                "No se pudo cerrar el Resulset", ex);
                    }
                }
            }
            out.println("</ul>");
            out.println("</body>");
            out.println("</html>");
		}finally{
			out.close();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException{
		String first = request.getParameter("nombre");
		int age;
		try{
			age = Integer.parseInt(request.getParameter("edad"));
		}catch(NumberFormatException e){
			age = -1;
		}
		
		ServletContext contexto = request.getServletContext();
		String query = null;
		
		try{
			query = "insert into employees (id,age,first) values("+45+","+age+",'"+first+"')";
				     
			synchronized(statment){
				statment.executeUpdate(query);
			}
		}catch(SQLException ex){
			gestionarErrorEnConsultaSQL(ex, request, response);
		}
		RequestDispatcher paginaAltas = 
				contexto.getRequestDispatcher("/jsp/tema10/crearpersona.html");
		paginaAltas.forward(request, response);
	}
	
	private void gestionarErrorEnConsultaSQL(SQLException ex, HttpServletRequest request, 
            HttpServletResponse response) throws IOException, ServletException {
    ServletContext contexto  = request.getServletContext();
    Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE, "No se pudo ejecutar la consulta contra la base de datos", ex);
    RequestDispatcher paginaError = contexto.getRequestDispatcher("/jsp/tema10/crearpersona.html");
    paginaError.forward(request, response);
}

	@Override
	public void destroy (){
	    try {
	        statment.close();
	    } catch (SQLException ex) {
	        Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE,
	                "No se pudo cerrar el objeto Statement", ex);
	    }
	    finally {
	        try {
	            conexion.close();
	        } catch (SQLException ex) {
	            Logger.getLogger(JDBCServlet.class.getName()).log(Level.SEVERE,
	                    "No se pudo cerrar el objeto Conexion", ex);
	        }
	    }
	}
}
