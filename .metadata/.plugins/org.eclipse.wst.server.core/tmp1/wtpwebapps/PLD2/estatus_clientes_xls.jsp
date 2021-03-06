﻿﻿<%@ page contentType = "application/vnd.ms-excel; charset=Unicode" %>
<%@page import="datosRatos.XlsEstatusCliente"%>
<%@page import="entidad.EstatusClientes"%>
<%      response.setHeader("Content-Disposition", "attachment;filename=EstatusClientes.xls");
%>
<!DOCTYPE html>
<html>
<head >
    <title>Efectivale - PLD</title>
</head>
<body>
	<header id="header">
	</header>
	<h5 >Sistema de Prevención de Lavado de Dinero</h1>
			<table id="tblEstatusClientes" name="tblEstatusClientes">
				<tr class="info" >
					<th>
						Id Sales Force 
					</th>
                                        
					<th>
						Id Cliente
					</th>					
                                        
					<th>
						Nombre
					</th>
					<th>
						RFC
					</th>	
					<th>
						Tipo
					</th>	
					<th>
						Estatus
					</th>
					<th>
						Validó
					</th>
					<th>
						Fecha de validación
					</th>					
					<th>
						Fecha de bloqueo
					</th>
					<th>
						Fecha ult. mod.
					</th>					
					<th>
						Mensaje
					</th>	
                                        <th>
						Notas
					</th>	
					<th>Ejecutivo</th>
					
	
                                </tr>
                                
                                <%
                                    
                                    String where ="";
                                    String usuario = "";
                                    String IdPerfil = "";
                                    String Offset = "";
                                    
                                        try{
                                            where = (String)request.getAttribute("where");
                                            Offset = (String)request.getAttribute("offset");                                            
                                        } catch ( Exception es ){
                                            es.printStackTrace();
                                        }
                                        
                                        if (Offset==null) {
                                            Offset = "0";
                                        }                                        
                                        //Recuperando el usuario aitenticado
                                        try{
                                            HttpSession sesion = request.getSession();
                                            usuario = sesion.getAttribute("user").toString();
                                            IdPerfil = sesion.getAttribute("perfilId").toString();
                                            
                                        } catch ( Exception es ){
                                            es.printStackTrace();
                                        }
                                        
                                        
                                        //EstatusClientes[] c = new DatosEstatusCliente().getList(where, usuario, IdPerfil);
                                        EstatusClientes[] c =null;
                                        if(where!=null)
                                        {
                                            c=new XlsEstatusCliente().getList(where, usuario, IdPerfil);
                                        }
                                        if ( c != null){
                                            
                                            
                                           
                                            
                                            
                                            for (int i = 0; i < c.length; i++ ) 
                                            {
                                                
                                                String servlet = "";
                                                if ( c[i].getTipoPersona().trim().equals("F")){
                                                    servlet="EdicionPersonaFisica";
                                                } else if ( c[i].getTipoPersona().trim().equals("M")){
                                                    servlet="EdicionPersonaMoral";
                                                } else if ( c[i].getTipoPersona().trim().equals("X")){
                                                    servlet ="EdicionFideicomiso";
                                                } else if ( c[i].getTipoPersona().trim().equals("G")){
                                                    servlet = "EdicionGobierno";
                                                } else {
                                                    servlet = "tipo_persona.jsp";
                                                }
                                                
                                           
                                                
                                                out.println("<tr>");
                                                out.println("<td><a href=\"" + servlet + "?idCliente=" + c[i].getClienteId().trim() + "&esVerificacion=1&rfc=" + c[i].getRfc().trim().replace("&", "%26") + "\" style=\"text-decoration: underline; color:blue;\">" + c[i].getClienteId() + "</a>");
                                                out.println("</td>");
                                                
                                                out.println("<td>" + c[i].getIdClienteNumerico() );
                                                out.println("</td>");

                                                        //<td><a href="verificacion.html">A-12345</a>
                                                        //</td>					
                                                out.println("<td>" + c[i].getRazonSocial());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getRfc());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getTipoPersona());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getEstatus());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getUsuariovalido() );
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getFechaValido() );
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getFechaBloqueo() );
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getFechaModificacion() );
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getMensaje());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getNotas());
                                                out.println("</td>");
                                                out.println("<td>" + c[i].getEjecutivo());
                    							out.println("</td>");
                    							
                                                out.println("</tr>");

                                            } //fin del for
                                            out.println("</table> ");                    
                                        } else {
                                            out.println("</table> ");                                                                                
                                        }      
                                %>
	<footer>

</footer>	
        <script>
            //Tabla2Excel('tblEstatusClientes', 'EstatusClientes');       
            window.close();
        </script>
        
</body>
</html>