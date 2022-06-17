select*from avcliente
select*from avpersonafisica
select*from avpersonamoral
select*from avfideicomiso
select*from avgobierno
select*from avdomicilioext
select*from varusuarios
select*from varusuariotransitorio
select*from avtipoidentificacion

select*from avcliente INNER JOIN avpersonafisica on avcliente.cliente_id=avpersonafisica.cliente_id
select*from avcliente INNER JOIN avpersonamoral on  avcliente.cliente_id=avpersonamoral.cliente_id
select*from avcliente LEFT JOIN avpersonafisica ON avpersonafisica.cliente_id=avpersonafisica.cliente_id
select*from avcliente LEFT JOIN avpersonamoral ON avpersonamoral.cliente_id=avpersonamoral.cliente_id


*****caso del martes 14/06/2022***************
select case when tipopersona='F' then 1 when tipopersona='M' then 2 when tipopersona='X' then 4 when tipopersona='G' then 7 end
as CVE_TIPO_PERSONA, COALESCE(nombre) as NOMBRE_PERSONA,COALESCE(apellidopaterno) as APELLIDO_PATERNO, COALESCE(apellidomaterno)as APELLIDO_MATERNO,
COALESCE(avpersonamoral.razonsocial) as DS_RAZON_SOCIAL,case when paisnacim is null then 0 when paisnacim='MX' then 1 else 2 end as CVE_NACIONALIDAD,	
avdomicilioext.calle as DS_CALLE,avdomicilioext.numexterior as NO_EXTERIOR,numinterior as NO_INTERIOR,codpostal as CODIGO_POSTAL,telefono as NUM_TEL_CASA, email as EMAIL,
COALESCE(avpersonafisica.fechanacimiento) as FECHA_NACIMIENTO,COALESCE(avpersonamoral.fechaconstitucion) as FECHA_CONSTITUCION,COALESCE(avpersonafisica.curp) as curp,COALESCE(avpersonafisica.rfc,avpersonamoral.rfc) as RFC_CLIENTE,
COALESCE(avpersonafisica.actividad_id) as CVE_ACTIVIDAD_ECONOMICA,COALESCE(avpersonafisica.identifica_id,avpersonamoral.rlidentifica_id) as DS_IDENTIFICACION
FROM avcliente
LEFT JOIN avpersonafisica ON avpersonafisica.cliente_id=avcliente.cliente_id
LEFT JOIN avpersonamoral ON avpersonamoral.cliente_id=avcliente.cliente_id
LEFT JOIN avfideicomiso ON avfideicomiso.cliente_id=avcliente.cliente_id
LEFT JOIN avgobierno ON avgobierno.cliente_id=avcliente.cliente_id
LEFT JOIN avdomicilioext ON avdomicilioext.cliente_id=avcliente.cliente_id


*******consulta numero 1 de 16/06/2022***********
select case when tipopersona='F' then 1 when tipopersona='M' then 2 when tipopersona='X' then 4 when tipopersona='G' then 7 end
as CVE_TIPO_PERSONA, COALESCE(nombre) as NOMBRE_PERSONA,COALESCE(apellidopaterno) as APELLIDO_PATERNO, COALESCE(apellidomaterno)as APELLIDO_MATERNO,
COALESCE(avpersonamoral.razonsocial) as DS_RAZON_SOCIAL,case when paisnacim is null then 0 when paisnacim='MX' then 1 else 2 end as CVE_NACIONALIDAD,	
avdomicilioext.calle as DS_CALLE,avdomicilioext.numexterior as NO_EXTERIOR,numinterior as NO_INTERIOR,codpostal as CODIGO_POSTAL,telefono as NUM_TEL_CASA, email as EMAIL,
COALESCE(avpersonafisica.fechanacimiento) as FECHA_NACIMIENTO,COALESCE(avpersonamoral.fechaconstitucion) as FECHA_CONSTITUCION,COALESCE(avpersonafisica.curp) as curp,COALESCE(avpersonafisica.rfc,avpersonamoral.rfc) as RFC_CLIENTE,
COALESCE(avpersonafisica.actividad_id) as CVE_ACTIVIDAD_ECONOMICA,COALESCE(avpersonafisica.identifica_id,avpersonamoral.rlidentifica_id) as DS_IDENTIFICACION,
COALESCE(case when avpersonamoral.rlautoridademiteid is null then 0 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL ELECTORAL' then 1 when avpersonamoral.rlautoridademiteid='SECRETARIA DE RELACIONES EXTERIORES' then 2 
when avpersonamoral.rlautoridademiteid='SECRETARIA DE EDUCACION PUBLICA' then 3 when avpersonamoral.rlautoridademiteid='INSTITUTO MEXICANO DEL SEGURO SOCIAL' THEN 4 
when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL DE LAS PERSONAS ADULTAS MAYORES' then 5  end) as CVE_EMISOR_IDENTIFICACION
FROM avcliente
LEFT JOIN avpersonafisica ON avpersonafisica.cliente_id=avcliente.cliente_id
LEFT JOIN avpersonamoral ON avpersonamoral.cliente_id=avcliente.cliente_id
LEFT JOIN avfideicomiso ON avfideicomiso.cliente_id=avcliente.cliente_id
LEFT JOIN avgobierno ON avgobierno.cliente_id=avcliente.cliente_id
LEFT JOIN avdomicilioext ON avdomicilioext.cliente_id=avcliente.cliente_id


***consulta numero 2 de: 16/06/2022***
select case when tipopersona='F' then 1 when tipopersona='M' then 2 when tipopersona='X' then 4 when tipopersona='G' then 7 end
as CVE_TIPO_PERSONA, COALESCE(nombre) as NOMBRE_PERSONA,COALESCE(apellidopaterno) as APELLIDO_PATERNO, COALESCE(apellidomaterno)as APELLIDO_MATERNO,
COALESCE(avpersonamoral.razonsocial) as DS_RAZON_SOCIAL,case when paisnacim is null then 0 when paisnacim='MX' then 1 else 2 end as CVE_NACIONALIDAD,	
avdomicilioext.calle as DS_CALLE,avdomicilioext.numexterior as NO_EXTERIOR,numinterior as NO_INTERIOR,codpostal as CODIGO_POSTAL,telefono as NUM_TEL_CASA, email as EMAIL,
COALESCE(avpersonafisica.fechanacimiento) as FECHA_NACIMIENTO,COALESCE(avpersonamoral.fechaconstitucion) as FECHA_CONSTITUCION,COALESCE(avpersonafisica.curp) as curp,COALESCE(avpersonafisica.rfc,avpersonamoral.rfc) as RFC_CLIENTE,
COALESCE(avpersonafisica.actividad_id) as CVE_ACTIVIDAD_ECONOMICA,COALESCE(avpersonafisica.identifica_id,avpersonamoral.rlidentifica_id) as DS_IDENTIFICACION,
case when avpersonamoral.rlautoridademiteid is null then 0 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL ELECTORAL' then 1 when avpersonamoral.rlautoridademiteid='SECRETARIA DE RELACI0NES EXTERIORES' then 2
when avpersonamoral.rlautoridademiteid='SECRETARIA DE EDUCACION PUBLICA' then 3 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL DE LAS PERSONA ADULTAS MAYORES' then 4 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL DE LAS PERSONAS ADULTAS MAYORES' then 5 end
as CVE_EMISOR_IDENTIFICACION_MORAL
FROM avcliente
LEFT JOIN avpersonafisica ON avpersonafisica.cliente_id=avcliente.cliente_id
LEFT JOIN avpersonamoral ON avpersonamoral.cliente_id=avcliente.cliente_id
LEFT JOIN avfideicomiso ON avfideicomiso.cliente_id=avcliente.cliente_id
LEFT JOIN avgobierno ON avgobierno.cliente_id=avcliente.cliente_id
LEFT JOIN avdomicilioext ON avdomicilioext.cliente_id=avcliente.cliente_id

***consulta 17/06/2022
select case when tipopersona='F' then 1 when tipopersona='M' then 2 when tipopersona='X' then 4 when tipopersona='G' then 7 end
as CVE_TIPO_PERSONA, COALESCE(nombre) as NOMBRE_PERSONA,COALESCE(apellidopaterno) as APELLIDO_PATERNO, COALESCE(apellidomaterno)as APELLIDO_MATERNO,
COALESCE(avpersonamoral.razonsocial) as DS_RAZON_SOCIAL,case when paisnacim is null then 0 when paisnacim='MX' then 1 else 2 end as CVE_NACIONALIDAD,	
avdomicilioext.calle as DS_CALLE,avdomicilioext.numexterior as NO_EXTERIOR,numinterior as NO_INTERIOR,codpostal as CODIGO_POSTAL,telefono as NUM_TEL_CASA, email as EMAIL,
COALESCE(avpersonafisica.fechanacimiento) as FECHA_NACIMIENTO,COALESCE(avpersonamoral.fechaconstitucion) as FECHA_CONSTITUCION,COALESCE(avpersonafisica.curp) as curp,COALESCE(avpersonafisica.rfc,avpersonamoral.rfc) as RFC_CLIENTE,
COALESCE(avpersonafisica.actividad_id) as CVE_ACTIVIDAD_ECONOMICA,COALESCE(avpersonafisica.identifica_id,avpersonamoral.rlidentifica_id) as DS_IDENTIFICACION,
case when avpersonamoral.rlautoridademiteid is null then 0 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL ELECTORAL' then 1 when avpersonamoral.rlautoridademiteid='SECRETARIA DE RELACI0NES EXTERIORES' then 2
when avpersonamoral.rlautoridademiteid='SECRETARIA DE EDUCACION PUBLICA' then 3 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL DE LAS PERSONA ADULTAS MAYORES' then 4 when avpersonamoral.rlautoridademiteid='INSTITUTO NACIONAL DE LAS PERSONAS ADULTAS MAYORES' then 5 end
as CVE_EMISOR_IDENTIFICACION_MORAL
FROM avcliente
LEFT JOIN avpersonafisica ON avpersonafisica.cliente_id=avcliente.cliente_id
LEFT JOIN avpersonamoral ON avpersonamoral.cliente_id=avcliente.cliente_id
LEFT JOIN avfideicomiso ON avfideicomiso.cliente_id=avcliente.cliente_id
LEFT JOIN avgobierno ON avgobierno.cliente_id=avcliente.cliente_id
LEFT JOIN avdomicilioext ON avdomicilioext.cliente_id=avcliente.cliente_id