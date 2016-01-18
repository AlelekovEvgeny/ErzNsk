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


public class UtilParseDbXml {

	public  void marshalingExample(Employees em) throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(em.getClass());
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		jaxbMarshaller.marshal(em, System.out);
		jaxbMarshaller.marshal(em, new File("c:/temp/employees.xml"));
	}
		
	public  void unMarshalingExample() throws JAXBException {
		JAXBContext jaxbContext = JAXBContext.newInstance(Employees.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		Employees emps = (Employees) jaxbUnmarshaller.unmarshal(new File("c:/temp/employees.xml"));

		for (Employee emp : emps.getEmployees()) {
			System.out.println(emp.getId());
			System.out.println(emp.getIncome());
		}
	}

	
	public void marshaling(ArrayList<ArrayList<String>> ls ) throws JAXBException
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
		
		marshalingExample(employees);
	}

}
