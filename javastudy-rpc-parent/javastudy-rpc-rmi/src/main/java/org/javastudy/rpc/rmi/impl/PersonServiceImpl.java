package org.javastudy.rpc.rmi.impl;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.List;

import org.javastudy.rpc.rmi.model.PersonEntity;
import org.javastudy.rpc.rmi.service.PersonService;

//此为远程对象的实现类，须继承UnicastRemoteObject
public class PersonServiceImpl extends UnicastRemoteObject implements PersonService {

	public PersonServiceImpl() throws RemoteException {
		super();
	}

	public List<PersonEntity> GetList() throws RemoteException {
		// TODO Auto-generated method stub
		System.out.println("Get Person Start!");
		List<PersonEntity> personList = new LinkedList<PersonEntity>();

		PersonEntity person1 = new PersonEntity();
		person1.setAge(20);
		person1.setId(0);
		person1.setName("Leslie");
		personList.add(person1);

		PersonEntity person2 = new PersonEntity();
		person2.setAge(25);
		person2.setId(1);
		person2.setName("Rose");
		personList.add(person2);
		
		PersonEntity person3 = new PersonEntity();
		person3.setAge(30);
		person3.setId(2);
		person3.setName("henry");
		personList.add(person3);

		return personList;
	}
}