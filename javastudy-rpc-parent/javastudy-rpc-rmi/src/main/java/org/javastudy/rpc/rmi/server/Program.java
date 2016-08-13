package org.javastudy.rpc.rmi.server;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import org.javastudy.rpc.rmi.impl.PersonServiceImpl;
import org.javastudy.rpc.rmi.service.PersonService;

// http://www.cnblogs.com/leslies2/archive/2011/05/20/2051844.html
public class Program {

	public static void main(String[] args) {
		try {
			PersonService personService = new PersonServiceImpl();
			// 注册通讯端口
			LocateRegistry.createRegistry(6600);
			// 注册通讯路径
			Naming.rebind("rmi://127.0.0.1:6600/PersonService", personService);
			System.out.println("Service Start!");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}