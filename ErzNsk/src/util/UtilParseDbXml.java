package util;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import dao.impl.Employee;
import dao.impl.Employees;
import dao.impl.Secondlist;
import help.Const;


public class UtilParseDbXml {

	public  void marshalingExample(String name,Employees em) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(em.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(em, new File(Const.OUTPUTDONE+name+".xml"));
	}
		
	public  ArrayList<ArrayList<String>> unMarshalingExample(String name) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Employees emps = (Employees) jaxbUnmarshaller.unmarshal(new File(Const.OUTPUTDONE+name+".xml"));

		ArrayList<ArrayList<String>> colBig = new ArrayList<ArrayList<String>>();
		
		for (Employee emp : emps.getEmployees()) {
			ArrayList<Secondlist> sl = emp.getLs();
			
			colBig.add(sl.get(0).getSecond());
		}
		
		return colBig;
	}

	
	public void marshaling(String name,ArrayList<ArrayList<String>> ls ) throws JAXBException
	{
		Employees employees = new Employees();
		employees.setEmployees(new ArrayList<Employee>());
		
		Employee emp = new Employee();
		Secondlist second = new Secondlist();
		ArrayList<Secondlist> secondAL = new ArrayList<Secondlist>();
		
		for(ArrayList<String> sm : ls)
		{
			emp = new Employee();
			second = new Secondlist();
			second.setSecond(sm);

			secondAL = new ArrayList<Secondlist>();
			secondAL.add(second);

			emp.setLs(secondAL);
			employees.getEmployees().add(emp);

		}
		
		marshalingExample(name,employees);
	}

}
