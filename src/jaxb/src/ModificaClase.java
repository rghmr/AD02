/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jaxb.src;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.math.BigDecimal.ONE;
import java.math.BigInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import jaxb.clase.Alumnos;
import jaxb.clase.Alumnos.Alumno;
import jaxb.clase.CursoType;
import jaxb.clase.DatosProf;

/**
 *
 * @author rodrigo
 */
public class ModificaClase {
    public static void main(String[] args) {
        try {

            // Crear una instancia de la clase JAXBContext para
            // poder manipular las clases generadas en el paquete jaxb.clase
            // La clase JAXBContext proporciona al cliente un punto de entrada a la API JAXB
            // Facilita una abstracción para manejar la información generada para
            // implementar las operaciones del JAXB binding framework como unmarshal y marshal
            // unmarshal: consiste en convertir datos XML en un árbol de objetos Java
            // marshal: consiste en convertir un árbol de objetos Java a datos XML
            JAXBContext jaxbContext = JAXBContext.newInstance("jaxb.clase");

            // Crear un objeto de tipo Unmarshaller para convertir datos XML en un árbol de objetos Java
            Unmarshaller u = jaxbContext.createUnmarshaller();

            // La clase JAXBElement representa a un elemento de un documento XML
            // en este caso a un elemento del documento albaran.xml
            JAXBElement jaxbElement = (JAXBElement) u.unmarshal(
                               new FileInputStream("/home/rodrigo/DAM2/AD/AD02/clase.xml"));

            // El método getValue() retorna el modelo de contenido (content model) y el valor de los atributos del elemento
            CursoType cursoType = (CursoType) jaxbElement.getValue();

            // Lista de alumnos que será la que marshalicemos más tarde
            Alumnos listaAlumnos = cursoType.getAlumnos();
            
            // Creamos dos variables locales para guardar datos de alumnos que
            // más tarde añadiremos a la lista para marshalizarlos
            Alumno alumno1, alumno2;
            
            // Vamos allá con el primer alumno
            alumno1 = new Alumno();
            alumno1.setNombreAlumno("Ruidoquedito");
            alumno1.setDireccion("San Periquín del Valle, 33");
            alumno1.setEdad(53);
            alumno1.setTelefono(BigInteger.valueOf(44444));
            alumno1.setComentario("este alumno es un poco tarambainas");

            // Vamos a meter los datos del segundo alumno
            alumno2 = new Alumno();
            alumno2.setNombreAlumno("Syd Barrett");
            alumno2.setDireccion("Todos los muertos, 15");
            alumno2.setEdad(70);
            alumno2.setTelefono(BigInteger.valueOf(33333));
            alumno2.setComentario("este alumno tiene problemas con las drogas");            
            
            // Añadimos los alumnos a la lista
            listaAlumnos.getAlumno().add(alumno1);
            listaAlumnos.getAlumno().add(alumno2);
            
            // Ya puestos, vamos a cambiar también los datos del profesor
            // Obtenemos una instancia de tipo cursoType para obtener un Objeto de tipo DatosProf
            DatosProf profesor = cursoType.getProfesor();

            // Establecemos los datos
            profesor.setNombre("Alberto Unapiedra");
            profesor.setAsignatura("Física");
            profesor.setCurso("Segundo");
            profesor.setEspecialidad("Ciencias");            
            profesor.setModalidad(ONE);
            profesor.setPais("Islas Barbudas");

            // Crear un objeto de tipo Marshaller para posteriormente convertir un
            // el árbol de objetos Java a datos XML
            Marshaller m = jaxbContext.createMarshaller();

            // El método setProperty(String nombrePropiedad, Object value) recibe en este
            // caso la propiedad "jaxb.formatted.output". Esta propiedad controla si al
            // realizar un marshal, debe formatear el resultado XML con saltos de linea
            // e indentaciones para que las personas podamos leerlo cómodamente. Por defecto
            // su valor es falso es decir el XML creado no está formateado
            // El argumento value en este caso tiene que ser concretamente de tipo Boolean
            // para indicar si queremos que el resultado XML esté formateado o no
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // El método marshall(Object elementoJAXB, OutputStream os) recibe un objeto
            // de tipo JAXBElement para que su contenido lo muestre en la salida estándar
            // debido a que este método está sobrecargo, si miramos la documentación de
            //la API podemos ver como podemos mostrar o escribir el resultado XML de
            //diferentes maneras
            m.marshal(jaxbElement, System.out);
  
        } catch (JAXBException je) {
            System.out.println(je.getCause()) ;
        } catch (FileNotFoundException ioe) {
            System.out.println(ioe.getMessage());
        }
    } // main()
    
}
